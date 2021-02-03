/*
 * This file was last modified at 2021.02.03 21:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.Session;

import java.util.UUID;

public interface SessionDao extends ReactiveCrudRepository<Session, String>, SessionCustomizedDao {

    @Query("SELECT * FROM security.session WHERE user_name = $1 AND end_time < (now() - '08:00:10'::interval) AND enabled")
    Mono<Session> monoByUserName(String userName);

    @Query("SELECT * FROM security.session WHERE session_id = $1 AND end_time < (now() - '08:00:10'::interval) AND enabled")
    Mono<Session> monoBySessionId(UUID sessionId);
}
