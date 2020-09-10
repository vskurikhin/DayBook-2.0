/*
 * This file was last modified at 2020.09.08 19:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserNameDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.UserName;

import java.util.UUID;

public interface UserNameDao extends ReactiveCrudRepository<UserName, UUID> {

    @Query("SELECT * FROM security.user_name WHERE user_name = $1")
    Mono<UserName> fetchByUserName(String userName);
}
