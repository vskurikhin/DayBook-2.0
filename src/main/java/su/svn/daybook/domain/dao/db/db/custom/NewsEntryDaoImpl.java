/*
 * This file was last modified at 2020.11.08 23:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db.custom;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsEntryCustomizedDao;
import su.svn.daybook.domain.model.db.db.NewsEntry;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class NewsEntryDaoImpl implements NewsEntryCustomizedDao {

    private final ConnectionFactory connectionFactory;

    private final AtomicLong count = new AtomicLong(0);

    public NewsEntryDaoImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public static final String INSERT = "INSERT INTO db.news_entry " +
            " (news_entry_id, news_group_id, user_name, title, content, create_time, update_time, enabled, visible, " +
            "  flags) " +
            " VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)";

    @Override
    public Mono<NewsEntry> insert(NewsEntry newsEntry) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> insert(connection, newsEntry));
    }

    private Mono<? extends NewsEntry> insert(Connection connection, NewsEntry newsEntry) {
        return executeInsert(connection, newsEntry)
                .doOnSuccess(n -> incrementAndGetPrint())
                .doOnError(e -> log.error("insert ", e))
                .doFinally((st) -> connection.close())
                .flatMap(result -> Mono.from(extractResult(result)));
    }

    @Override
    public Flux<NewsEntry> insertAll(Iterable<NewsEntry> entries) {
        return Mono.from(connectionFactory.create())
                .flatMapMany(connection -> insertAll(connection, entries));
    }

    private Publisher<? extends NewsEntry> insertAll(Connection connection, Iterable<NewsEntry> entries) {
        return Flux.from(executeInsertStatements(connection, entries))
                .doOnError(e -> log.error("createInsertAllTransaction ", e))
                .doFinally((st) -> connection.close())
                .flatMap(this::extractResult);
    }

    @Override
    public Mono<NewsEntry> transactionalInsert(NewsEntry newsEntry) {
        Iterable<NewsEntry> iterable = new LinkedList<>() {{ add(newsEntry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(createInsertAllTransaction(connection, iterable)));
    }

    private void incrementAndGetPrint() {
        long c = count.incrementAndGet();
        log.debug("insert count: {}", c);
    }

    private Mono<? extends Result> executeInsert(Connection connection, NewsEntry newsEntry) {
        return Mono.from(executeInsertStatement(connection, newsEntry));
    }

    @Override
    public Flux<NewsEntry> transactionalInsertAll(Iterable<NewsEntry> entries) {
        return Mono.from(connectionFactory.create())
                .flatMapMany(connection -> createInsertAllTransaction(connection, entries));
    }

    private Publisher<? extends NewsEntry> createInsertAllTransaction(Connection connection, Iterable<NewsEntry> entries) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements(connection, entries))
                .collectList()
                .delayUntil(r -> connection.commitTransaction())
                .flatMapMany(Flux::fromIterable)
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction ", e))
                .doFinally((st) -> connection.close())
                .flatMap(this::extractResult);
    }

    private Publisher<? extends NewsEntry> extractResult(Result result) {
        return result.map((row, rowMetadata) -> NewsEntry.builder()
                .id(row.get("news_entry_id", UUID.class))
                .newsGroupId(row.get("news_group_id", UUID.class))
                .userName(row.get("user_name", String.class))
                .title(row.get("title", String.class))
                .content(row.get("content", String.class))
                .createTime(row.get("create_time", LocalDateTime.class))
                .updateTime(row.get("update_time", LocalDateTime.class))
                .enabled(row.get("enabled", Boolean.class))
                .visible(row.get("visible", Boolean.class))
                .flags(row.get("flags", Integer.class))
                .build());
    }

    private Publisher<? extends Result> executeInsertStatements(Connection connection, Iterable<NewsEntry> entries) {

        if (entries != null) {
            Iterator<NewsEntry> iterator = entries.iterator();
            if (iterator.hasNext()) {
                Statement statement = insertStatement(connection, iterator.next());
                while (iterator.hasNext()) {
                    statementBinding(statement.add(), iterator.next());
                }
                return statement.execute();
            }
        }
        return Flux.empty();
    }

    private Publisher<? extends Result> executeInsertStatement(Connection connection, NewsEntry newsEntry) {
        return insertStatement(connection, newsEntry).execute();
    }

    private Statement insertStatement(Connection connection, NewsEntry newsEntry) {
        return statementBinding(connection.createStatement(INSERT), newsEntry);
    }

    private Statement statementBinding(Statement statement, NewsEntry newsEntry) {
        return statement
                .bind("$1", newsEntry.getId())
                .bind("$2", newsEntry.getNewsGroupId())
                .bind("$3", newsEntry.getUserName())
                .bind("$4", newsEntry.getTitle())
                .bind("$5", newsEntry.getContent())
                .bind("$6", newsEntry.getCreateTime())
                .bind("$7", newsEntry.getUpdateTime())
                .bind("$8", newsEntry.getEnabled())
                .bind("$9", newsEntry.getVisible())
                .bind("$10", newsEntry.getFlags());
    }

    public static int size(Iterable<?> data) {

        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object i : data) {
            counter++;
        }
        return counter;
    }
}
