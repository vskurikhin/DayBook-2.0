/*
 * This file was last modified at 2021.01.19 19:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Link;

import java.util.UUID;

public interface LinkDao extends ReactiveCrudRepository<Link, UUID> {

    @Query("SELECT * FROM db.link WHERE id = :id AND enabled")
    Mono<Link> monoById(UUID id);

    @Query("SELECT * FROM db.link WHERE id = :id AND enabled")
    Mono<Link> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.link WHERE enabled")
    Flux<Link> fluxAll();

    @Query("SELECT * FROM db.link WHERE id IN (:ids) AND enabled")
    Flux<Link> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.link WHERE enabled")
    Mono<Long> monoCount();
}
