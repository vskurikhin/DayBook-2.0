/*
 * This file was last modified at 2021.02.22 14:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionRolesViewDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.SessionRolesView;

import java.util.UUID;

public interface SessionRolesViewDao extends ReactiveCrudRepository<SessionRolesView, String> {

    @Query("SELECT * FROM security.session_roles_view WHERE user_name = $1")
    Mono<SessionRolesView> monoByUserName(String userName);

    @Query("SELECT * FROM security.session_roles_view WHERE session_id = $1")
    Mono<SessionRolesView> monoBySessionId(UUID sessionId);
}
