/*
 * This file was last modified at 2021.01.23 16:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetRecordViewDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.TaggetRecordView;

import java.util.UUID;

public interface TaggetRecordViewDao extends ReactiveCrudRepository<TaggetRecordView, UUID> {

    @Query("SELECT * FROM db.tagget_record_view WHERE record_id = :id AND record_enabled")
    Mono<TaggetRecordView> monoById(UUID id);

    @Query("SELECT * FROM db.tagget_record_view WHERE record_id = :id AND record_enabled")
    Mono<TaggetRecordView> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.tagget_record_view WHERE record_enabled")
    Flux<TaggetRecordView> fluxAll();

    @Query("SELECT * FROM db.tagget_record_view WHERE record_id IN (:ids) AND record_enabled")
    Flux<TaggetRecordView> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.tagget_record_view WHERE record_enabled")
    Mono<Long> monoCount();
}
