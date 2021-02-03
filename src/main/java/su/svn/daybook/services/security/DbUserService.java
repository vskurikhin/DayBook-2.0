/*
 * This file was last modified at 2021.02.03 21:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DbUserService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.security.RoleDao;
import su.svn.daybook.domain.dao.db.security.SessionDao;
import su.svn.daybook.domain.dao.db.security.UserNameDao;
import su.svn.daybook.domain.model.db.security.Role;
import su.svn.daybook.domain.model.db.security.Session;
import su.svn.daybook.domain.model.db.security.UserName;
import su.svn.daybook.domain.security.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
public class DbUserService implements UserService {

    private final long expirationTime;

    private final UserNameDao userNameDao;

    private final SessionDao sessionDao;

    private final RoleDao roleDao;

    public DbUserService(
            @Value("${springbootwebfluxjjwt.jjwt.expiration}") String expirationTime,
            UserNameDao userNameDao,
            SessionDao sessionDao,
            RoleDao roleDao) {
        this.expirationTime = Long.parseLong(expirationTime);
        this.userNameDao = userNameDao;
        this.sessionDao = sessionDao;
        this.roleDao = roleDao;
    }

    public Mono<User> findByUsername(final String username) {
            return roleDao.fluxByUserName(username)
                    .collectList()
                    .log()
                    .flatMap((roles -> fetchByUserName(username, roles)));
    }

    @Override
    @Transactional
    public Mono<Session> newSessionForUserName(String username) {
        log.trace("newSessionForUserName({})", username);
        UUID sessionId = UUID.randomUUID();
        log.trace("newSessionForUserName({}) -> {}", username, sessionId);
        final Session session = Session.builder()
                .id(username)
                .createTime(LocalDateTime.now())
                .sessionId(sessionId)
                .enabled(true)
                .visible(true)
                .flags(0)
                .endTime(LocalDateTime.now().plus(expirationTime, ChronoUnit.SECONDS))
                .updateTime(LocalDateTime.now())
                .build();
        return sessionDao.insert(session).flatMap(i -> i > 0 ? Mono.just(session) : Mono.empty());
    }

    @Override
    @Transactional
    public Mono<Session> updateSessionForUserName(final String username) {
        log.trace("updateSessionForUserName({})", username);
        return sessionDao.findById(username).flatMap(session -> {
            UUID sessionId = UUID.randomUUID();
            log.trace("updateSessionForUserName({}) -> {}", username, sessionId);
            session.setSessionId(sessionId);
            session.setEndTime(LocalDateTime.now().plus(expirationTime, ChronoUnit.SECONDS));
            session.setUpdateTime(LocalDateTime.now());

            return sessionDao.save(session);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<User> findBySessionId(UUID sessionId) {
        return sessionDao.monoBySessionId(sessionId).flatMap(session -> findByUsername(session.getId()));
    }

    @Transactional(readOnly = true)
    public Mono<User> fetchByUserName(String username, final List<Role> roles) {
        return userNameDao.fetchByUserName(username).map(userName -> buildUser(userName, roles));
    }

    public User buildUser(UserName userName, List<Role> roles) {
        return User.builder()
                .username(userName.getUserName())
                .password(userName.getPassword())
                .enabled(userName.getEnabled())
                .roles(collect(roles))
                .build();
    }

    private List<su.svn.daybook.domain.security.Role> collect(List<Role> roles) {
        return roles.stream()
                .map(DbUserService.this::convert)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private su.svn.daybook.domain.security.Role convert(Role role) {
        return su.svn.daybook.domain.security.Role.valueOfName(role.getRoleName());
    }
}
