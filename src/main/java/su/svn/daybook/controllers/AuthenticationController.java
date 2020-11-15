/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AuthenticationController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.security.AuthRequest;
import su.svn.daybook.domain.security.AuthResponse;
import su.svn.daybook.services.security.JWTUtil;
import su.svn.daybook.services.security.PBKDF2Encoder;
import su.svn.daybook.services.security.UserService;

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
    public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest authRequest) {
        return userService.findByUsername(authRequest.getUsername()).map((userDetails) -> {
            if (passwordEncoder.encode(authRequest.getPassword()).equals(userDetails.getPassword())) {
                return ResponseEntity.ok(new AuthResponse(authRequest.getUsername(), jwtUtil.generateToken(userDetails)));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
