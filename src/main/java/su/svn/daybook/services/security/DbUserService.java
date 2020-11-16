/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DbUserService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.security.RoleDao;
import su.svn.daybook.domain.dao.db.security.UserNameDao;
import su.svn.daybook.domain.model.db.security.Role;
import su.svn.daybook.domain.model.db.security.UserName;
import su.svn.daybook.domain.security.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
public class DbUserService implements UserService {

    private final UserNameDao userNameDao;

    private final RoleDao roleDao;

    public DbUserService(UserNameDao userNameDao, RoleDao roleDao) {
        this.userNameDao = userNameDao;
        this.roleDao = roleDao;
    }

    public Mono<User> findByUsername(final String username) {
            return roleDao.fluxByUserName(username)
                    .collectList()
                    .log()
                    .flatMap((roles -> fetchByUserName(username, roles)));
    }

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
