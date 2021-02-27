/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Tagget;

import java.util.UUID;

public interface TaggetDao extends ReactiveCrudRepository<Tagget, UUID>, TaggetCustomizedDao {

    @Query("SELECT * FROM db.tagget WHERE id = :id AND enabled")
    Mono<Tagget> monoById(UUID id);

    @Query("SELECT * FROM db.tagget WHERE id = :id AND enabled")
    Mono<Tagget> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.tagget WHERE enabled")
    Flux<Tagget> fluxAll();

    @Query("SELECT * FROM db.tagget WHERE id IN (:ids) AND enabled")
    Flux<Tagget> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.tagget WHERE enabled")
    Mono<Long> monoCount();
}
