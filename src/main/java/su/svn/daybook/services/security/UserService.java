/*
 * This file was last modified at 2020.08.27 08:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserService.java
 * $Id$
 */

package su.svn.daybook.services.security;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.security.Role;
import su.svn.daybook.domain.security.User;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private Map<String, User> data;

    @PostConstruct
    public void init(){
        data = new HashMap<>();

        //username:passwowrd -> user:user
        data.put("user", new User("user", "cBrlgyL2GI2GINuLUUwgojITuIufFycpLG4490dhGtY=", true, Collections.singletonList(Role.ROLE_USER)));

        //username:passwowrd -> admin:admin
        data.put("admin", new User("admin", "dQNjUIMorJb8Ubj2+wVGYp6eAeYkdekqAcnYp+aRq5w=", true, Collections.singletonList(Role.ROLE_ADMIN)));
    }

    public Mono<User> findByUsername(String username) {
        if (data.containsKey(username)) {
            return Mono.just(data.get(username));
        } else {
            return Mono.empty();
        }
    }
}
