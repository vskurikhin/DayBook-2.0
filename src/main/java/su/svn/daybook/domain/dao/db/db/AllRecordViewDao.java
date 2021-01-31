/*
 * This file was last modified at 2021.01.31 20:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordViewDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.AllRecordView;

import java.util.UUID;

public interface AllRecordViewDao extends ReactiveSortingRepository<AllRecordView, UUID> {

    @Query("SELECT * FROM db.all_record_view WHERE record_id = :id AND record_enabled")
    Mono<AllRecordView> monoById(UUID id);

    @Query("SELECT * FROM db.all_record_view WHERE record_id = :id AND record_enabled")
    Mono<AllRecordView> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.all_record_view WHERE record_enabled")
    Flux<AllRecordView> fluxAll();

    Flux<AllRecordView> findAllByEnabledIsTrue(final Pageable pageable);

    Flux<AllRecordView> findAllByEnabledIsTrueOrderByUpdateTimeDescPositionAsc(final Pageable pageable);

    @Query("SELECT * FROM db.all_record_view WHERE record_id IN (:ids) AND record_enabled")
    Flux<AllRecordView> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.all_record_view WHERE record_enabled")
    Mono<Long> monoCount();
}