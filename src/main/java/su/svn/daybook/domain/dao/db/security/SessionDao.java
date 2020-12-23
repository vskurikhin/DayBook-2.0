/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.daybook.domain.model.db.security.Session;

public interface SessionDao extends ReactiveCrudRepository<Session, String>, SessionCustomizedDao {

    @Query("SELECT * FROM security.role WHERE user_name = $1 AND end_time < (now() - '08:00:10'::interval) AND enabled")
    Flux<Session> fluxByUserName(String userName);
}
