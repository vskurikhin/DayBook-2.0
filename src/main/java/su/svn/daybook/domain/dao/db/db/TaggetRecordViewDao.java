/*
 * This file was last modified at 2021.03.07 23:13 by Victor N. Skurikhin.
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

    @Query("SELECT * FROM db.tagget_record_view WHERE id = :id")
    Mono<TaggetRecordView> monoById(UUID id);

    @Query("SELECT * FROM db.tagget_record_view WHERE id = :id")
    Mono<TaggetRecordView> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.tagget_record_view")
    Flux<TaggetRecordView> fluxAll();

    @Query("SELECT * FROM db.tagget_record_view WHERE id IN (:ids)")
    Flux<TaggetRecordView> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.tagget_record_view")
    Mono<Long> monoCount();
}
