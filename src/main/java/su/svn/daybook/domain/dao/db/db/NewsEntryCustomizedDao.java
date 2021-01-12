/*
 * This file was last modified at 2021.01.12 21:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsEntry;

public interface NewsEntryCustomizedDao {

    Mono<Integer> insert(NewsEntry newsEntry);

    Mono<Integer> transactionalInsert(NewsEntry newsEntry);

    Mono<Integer> transactionalInsertAll(Iterable<NewsEntry> entries);
}
