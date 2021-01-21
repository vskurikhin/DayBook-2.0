/*
 * This file was last modified at 2021.01.19 21:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Article;

import java.util.UUID;

public interface ArticleDao extends ReactiveCrudRepository<Article, UUID> {

    @Query("SELECT * FROM db.article WHERE id = :id AND enabled")
    Mono<Article> monoById(UUID id);

    @Query("SELECT * FROM db.article WHERE id = :id AND enabled")
    Mono<Article> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.article WHERE enabled")
    Flux<Article> fluxAll();

    @Query("SELECT * FROM db.article WHERE id IN (:ids) AND enabled")
    Flux<Article> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.article WHERE enabled")
    Mono<Long> monoCount();
}
