/*
 * This file was last modified at 2021.02.03 21:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.Session;
import su.svn.daybook.domain.security.User;

import java.util.UUID;

public interface UserService {

    Mono<User> findByUsername(String username);

    Mono<Session> newSessionForUserName(String username);

    Mono<Session> updateSessionForUserName(String username);

    Mono<User> findBySessionId(UUID sessionId);
}
