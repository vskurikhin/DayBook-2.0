/*
 * This file was last modified at 2021.01.18 14:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import io.micrometer.core.lang.NonNull;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.NewsEntry;

import java.util.UUID;

public interface NewsEntryDao extends ReactiveCrudRepository<NewsEntry, UUID>, NewsEntryCustomizedDao {

    @Query("SELECT * FROM db.news_entry WHERE id = :id AND enabled")
    Mono<NewsEntry> monoById(UUID id);

    @Query("SELECT * FROM db.news_entry WHERE id = :id AND enabled")
    Mono<NewsEntry> monoById(Publisher<UUID> id);

    @Query("SELECT * FROM db.news_entry WHERE enabled")
    Flux<NewsEntry> fluxAll();

    @Query("SELECT * FROM db.news_entry WHERE id IN (:ids) AND enabled")
    Flux<NewsEntry> fluxAllById(Iterable<UUID> ids);

    @NonNull
    @Query("SELECT COUNT(*) FROM db.news_entry WHERE enabled")
    Mono<Long> monoCount();
}
