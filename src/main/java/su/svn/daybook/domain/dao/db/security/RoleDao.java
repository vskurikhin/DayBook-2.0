/*
 * This file was last modified at 2020.09.14 19:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import su.svn.daybook.domain.model.db.security.Role;

import java.util.UUID;

public interface RoleDao extends ReactiveCrudRepository<Role, UUID> {

    @Query("SELECT * FROM security.role WHERE user_name = $1 AND enabled")
    Flux<Role> fluxByUserName(String userName);
}
