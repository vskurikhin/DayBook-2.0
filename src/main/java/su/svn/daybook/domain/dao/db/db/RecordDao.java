/*
 * This file was last modified at 2020.11.10 19:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Record;

import java.util.UUID;

public interface RecordDao extends ReactiveCrudRepository<Record, UUID>, RecordCustomizedDao {

    @Query("SELECT * FROM db.record WHERE record_id = :id AND enabled")
    Mono<Record> monoById(UUID id);

    @Query("SELECT * FROM db.record WHERE record_id = :id AND enabled")
    Mono<Record> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.record WHERE enabled")
    Flux<Record> fluxAll();

    @Query("SELECT * FROM db.record WHERE record_id IN (:ids) AND enabled")
    Flux<Record> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.record WHERE enabled")
    Mono<Long> monoCount();
}
