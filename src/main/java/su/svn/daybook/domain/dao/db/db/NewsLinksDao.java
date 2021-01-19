/*
 * This file was last modified at 2021.01.19 19:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsLinks;

import java.util.UUID;

public interface NewsLinksDao extends ReactiveCrudRepository<NewsLinks, UUID> {

    @Query("SELECT * FROM db.news_links WHERE id = :id AND enabled")
    Mono<NewsLinks> monoById(UUID id);

    @Query("SELECT * FROM db.news_links WHERE id = :id AND enabled")
    Mono<NewsLinks> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.news_links WHERE enabled")
    Flux<NewsLinks> fluxAll();

    @Query("SELECT * FROM db.news_links WHERE id IN (:ids) AND enabled")
    Flux<NewsLinks> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.news_links WHERE enabled")
    Mono<Long> monoCount();
}
