/*
 * This file was last modified at 2020.09.06 12:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import su.svn.daybook.domain.model.db.db.Role;

import java.util.UUID;

public interface RoleDao extends ReactiveCrudRepository<Role, UUID> {
}
