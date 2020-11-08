/*
 * This file was last modified at 2020.09.22 16:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsGroup;

import java.util.UUID;

public interface NewsGroupDao extends ReactiveCrudRepository<NewsGroup, UUID> {

    @Query("SELECT * FROM db.news_group WHERE news_group_id = :id AND enabled")
    Mono<NewsGroup> monoById(UUID id);

    @Query("SELECT * FROM db.news_group WHERE news_group_id = :id AND enabled")
    Mono<NewsGroup> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.news_group WHERE enabled")
    Flux<NewsGroup> fluxAll();

    @Query("SELECT * FROM db.news_group WHERE news_group_id IN (:ids) AND enabled")
    Flux<NewsGroup> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.news_group WHERE enabled")
    Mono<Long> monoCount();
}
