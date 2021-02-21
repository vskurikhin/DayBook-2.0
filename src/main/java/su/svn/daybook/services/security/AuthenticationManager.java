/*
 * This file was last modified at 2021.02.21 20:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AuthenticationManager.java
 * $Id$
 */

package su.svn.daybook.services.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.security.RoleDao;
import su.svn.daybook.domain.dao.db.security.SessionDao;
import su.svn.daybook.domain.model.db.security.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Slf4j
@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final JWTUtil jwtUtil;

    private final SessionDao sessionDao;

    private final RoleDao roleDao;

    public AuthenticationManager(JWTUtil jwtUtil, SessionDao sessionDao, RoleDao roleDao) {
        this.jwtUtil = jwtUtil;
        this.sessionDao = sessionDao;
        this.roleDao = roleDao;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.error("authenticate({})", authentication);

        String authToken = authentication.getCredentials().toString();
        log.error("authenticate({}): authToken={}", authentication, authToken);

        try {
            String username = jwtUtil.getUsernameFromToken(authToken);
            if ( ! jwtUtil.validateToken(authToken)) {
                return Mono.empty();
            }
            Claims claims = jwtUtil.getAllClaimsFromToken(authToken);
            List<String> rolesMap = claims.get("role", List.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            for (String rolemap : rolesMap) {
                authorities.add(new SimpleGrantedAuthority(rolemap));
            }
            log.error("authenticate({}): rolesMap={}", authentication, rolesMap);
            return Mono.just(new UsernamePasswordAuthenticationToken(username, null, authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
