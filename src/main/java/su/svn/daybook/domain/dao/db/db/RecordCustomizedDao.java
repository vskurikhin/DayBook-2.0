/*
 * This file was last modified at 2020.11.10 19:59 by Victor N. Skurikhin.
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

    Mono<Record> insert(Record entry);

    Flux<Record> insertAll(Iterable<Record> entry);

    Mono<Record> transactionalInsert(Record entry);

    Flux<Record> transactionalInsertAll(Iterable<Record> entries);
}
