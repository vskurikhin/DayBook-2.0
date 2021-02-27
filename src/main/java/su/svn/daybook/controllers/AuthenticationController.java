/*
 * This file was last modified at 2021.02.27 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AuthenticationController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.Session;
import su.svn.daybook.domain.security.AuthRequest;
import su.svn.daybook.domain.security.AuthResponse;
import su.svn.daybook.services.security.JWTUtil;
import su.svn.daybook.services.security.PBKDF2Encoder;
import su.svn.daybook.services.security.UserService;

import java.util.UUID;
import java.util.function.Consumer;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication Controller", description = "JWT authentication APIs for the DayBook project.")
public class AuthenticationController {

    private final JWTUtil jwtUtil;

    private final PBKDF2Encoder passwordEncoder;

    private final UserService userService;

    public AuthenticationController(JWTUtil jwtUtil, PBKDF2Encoder passwordEncoder, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Operation(summary = "login", security = @SecurityRequirement(name = "bearerAuth"))
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest, ServerWebExchange exchange) {
        log.info("login({})", authRequest);
        return userService.findByUsername(authRequest.getUsername()).flatMap((userDetails) -> {
            if (passwordEncoder.encode(authRequest.getPassword()).equals(userDetails.getPassword())) {
                final String userName = userDetails.getUsername();
                return wrapSession(userName).doOnNext(responseEntity -> addCookie(exchange));
            } else {
                return Mono.just(unauthorized());
            }
        }).defaultIfEmpty(unauthorized());
    }

    private void addCookie(ServerWebExchange exchange) {
        String token = jwtUtil.generateToken(UUID.randomUUID());
        ResponseCookie cookie = ResponseCookie.from("rt", token)
                .maxAge(604800)
                .httpOnly(true)
                .build();
        exchange.getResponse().addCookie(cookie);
    }

    private ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    private Mono<ResponseEntity<?>> wrapSession(final String username) {
        log.trace("wrapSession({})", username);
        return generateSession(username).map(session -> ok(username, session.getSessionId()));
    }

    public ResponseEntity<?> ok(String username, UUID sessionId) {
        log.info("ok({}, {})", username, sessionId);
        return ResponseEntity.ok(new AuthResponse(username, jwtUtil.generateToken(sessionId)));
    }

    private Mono<Session> generateSession(String username) {
        log.trace("generateSession({})", username);
        return userService.updateSessionForUserName(username)
                .switchIfEmpty(userService.newSessionForUserName(username));
    }
}
