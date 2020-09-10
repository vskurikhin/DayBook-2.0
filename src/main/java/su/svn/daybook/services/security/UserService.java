/*
 * This file was last modified at 2020.09.08 19:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.security.User;

public interface UserService {

    Mono<User> findByUsername(String username);
}
