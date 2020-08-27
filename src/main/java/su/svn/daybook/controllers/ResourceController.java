/*
 * This file was last modified at 2020.08.27 10:10 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ResourceController.java
 * $Id$
 */

package su.svn.daybook.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.Message;

@RestController
public class ResourceController {
    @RequestMapping(value = "/resource/user", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public Mono<ResponseEntity<?>> user() {
        return Mono.just(ResponseEntity.ok(new Message("Content for user")));
    }

    @RequestMapping(value = "/resource/admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> admin() {
        return Mono.just(ResponseEntity.ok(new Message("Content for admin")));
    }

    @RequestMapping(value = "/resource/user-or-admin", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Mono<ResponseEntity<?>> userOrAdmin() {
        return Mono.just(ResponseEntity.ok(new Message("Content for user or admin")));
    }
}
