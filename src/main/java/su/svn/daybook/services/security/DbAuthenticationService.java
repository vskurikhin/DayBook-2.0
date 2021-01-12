/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DbAuthenticationService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.security.AuthRequest;
import su.svn.daybook.domain.security.AuthResponse;
import su.svn.daybook.domain.security.User;

import javax.validation.constraints.NotNull;

@Slf4j
@Service
public class DbAuthenticationService {

    private final JWTUtil jwtUtil;

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userService;

    public DbAuthenticationService(JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername()).flatMap((userDetails) -> {
            if (passwordEncoder.encode(authRequest.getPassword()).equals(userDetails.getPassword())) {
                return getAuthResponseResponseEntity(authRequest, userDetails);
            } else {
                return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @NotNull
    private Mono<ResponseEntity<?>> getAuthResponseResponseEntity(AuthRequest authRequest, User userDetails) {
        AuthResponse authResponse = new AuthResponse(authRequest.getUsername(), jwtUtil.generateToken(userDetails));
        return Mono.just(ResponseEntity.ok(authResponse));
    }

}
