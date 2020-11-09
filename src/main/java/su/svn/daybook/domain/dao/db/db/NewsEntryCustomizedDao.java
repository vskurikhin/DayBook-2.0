/*
 * This file was last modified at 2020.11.08 23:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsEntry;

public interface NewsEntryCustomizedDao {

    Mono<NewsEntry> insert(NewsEntry newsEntry);

    Flux<NewsEntry> insertAll(Iterable<NewsEntry> entries);

    Mono<NewsEntry> transactionalInsert(NewsEntry newsEntry);

    Flux<NewsEntry> transactionalInsertAll(Iterable<NewsEntry> entries);
}
