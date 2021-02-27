/*
 * This file was last modified at 2021.02.27 22:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Tagget;

import java.util.Set;
import java.util.UUID;

public interface TaggetCustomizedDao {

    Mono<Integer> insert(Tagget entry);

    Mono<Integer> insertFromSelect(UUID recordId, String userName, Set<String> ids);

    Mono<Integer> deleteNotInIds(UUID recordId, String userName, Set<String> ids);
}
