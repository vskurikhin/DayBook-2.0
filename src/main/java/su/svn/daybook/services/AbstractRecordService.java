/*
 * This file was last modified at 2021.02.27 11:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractRecordService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.model.DBUuidEntry;
import su.svn.daybook.domain.model.db.db.Record;

@Slf4j
public abstract class AbstractRecordService<E extends DBUuidEntry> extends AbstractService<E> {

    protected abstract RecordDao getRecordDao();

    protected abstract Mono<Integer> insertEntry(E entry);

    @Transactional
    public Mono<E> insert(Record record, final E entry) {
        log.trace("insert({}, {})", record, entry);

        return getRecordDao().insert(record)
                .doOnSuccess(i -> log.debug("insert {} count of record: {}", i, record))
                .doOnError(e -> log.error("insertNewsEntry ", e))
                .flatMap(i -> doOnInsert(i, entry))
                .doOnSuccess(i -> log.debug("insert {} count of newsEntry: {}", i, entry))
                .doOnError(e -> log.error("insertNewsEntry ", e));
    }

    private Mono<E> doOnInsert(Integer i1, final E entry) {
        return i1 != null && i1 == 1
                ? insertEntry(entry).flatMap(i2 -> i2 == 1 ? Mono.just(entry) : Mono.empty())
                : Mono.error(new RuntimeException(" doOnInsert catch i: " + i1));
    }
}
