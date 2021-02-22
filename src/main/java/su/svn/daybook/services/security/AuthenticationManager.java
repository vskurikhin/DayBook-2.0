/*
 * This file was last modified at 2021.02.22 14:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AuthenticationManager.java
 * $Id$
 */

package su.svn.daybook.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.security.SessionRolesViewDao;
import su.svn.daybook.domain.model.db.security.SessionRolesView;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    private final SessionRolesViewDao sessionRolesViewDao;

    public AuthenticationManager(JWTUtil jwtUtil, SessionRolesViewDao sessionRolesViewDao) {
        this.jwtUtil = jwtUtil;
        this.sessionRolesViewDao = sessionRolesViewDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.trace("authenticate({})", authentication);

        String authToken = authentication.getCredentials().toString();
        log.trace("authenticate({}): authToken={}", authentication, authToken);

        try {
            if ( ! jwtUtil.validateToken(authToken)) {
                log.error("authenticate({}): invalid token={}", authentication, authToken);
                return Mono.empty();
            }
            UUID sessionId = jwtUtil.getSessionIdFromToken(authToken);

            return getAuthentication(sessionId);
        } catch (Exception e) {
            return Mono.empty();
        }
    }

    private Mono<Authentication> getAuthentication(UUID sessionId) {
        return sessionRolesViewDao.monoBySessionId(sessionId).map(this::createAuthentication);
    }

    private Authentication createAuthentication(SessionRolesView srv) {

        Authentication auth = getUsernamePasswordAuthenticationToken(srv);
        SecurityContextHolder.getContext().setAuthentication(auth);
        log.info("createAuthentication: userName={}, roles={}", auth.getPrincipal(), auth.getAuthorities());

        return auth;
    }

    private Authentication getUsernamePasswordAuthenticationToken(SessionRolesView srv) {

        String userName = srv.getId();
        log.trace("getUsernamePasswordAuthenticationToken({}): userName={}", srv, userName);
        Set<String> roles = Set.of(srv.getRoles());
        log.trace("getUsernamePasswordAuthenticationToken({}): roles={}", srv, roles);
        Set<GrantedAuthority> credentials = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(srv.getId(), null, credentials);
    }
}
