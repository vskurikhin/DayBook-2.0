/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Record;

public interface RecordCustomizedDao {

    Mono<Integer> insert(Record entry);

    Mono<Integer> transactionalInsert(Record entry);

    Mono<Integer> transactionalInsertAll(Iterable<Record> entries);
}
