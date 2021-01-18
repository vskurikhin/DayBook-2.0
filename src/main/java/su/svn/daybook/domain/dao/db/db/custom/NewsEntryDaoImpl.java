/*
 * This file was last modified at 2021.01.18 14:04 by Victor N. Skurikhin.
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
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsEntryCustomizedDao;
import su.svn.daybook.domain.model.db.db.NewsEntry;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
public class NewsEntryDaoImpl implements NewsEntryCustomizedDao {

    private final ConnectionFactory connectionFactory;

    private final DatabaseClient client;

    public NewsEntryDaoImpl(ConnectionFactory connectionFactory, DatabaseClient client) {
        this.connectionFactory = connectionFactory;
        this.client = client;
    }

    public static final String INSERT = "INSERT INTO db.news_entry " +
            " (id, news_group_id, user_name, title, content, create_time, update_time, enabled, visible, flags) " +
            " VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10)";

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

    private Publisher<? extends NewsEntry> extractResult(Result result) {
        return result.map((row, rowMetadata) -> NewsEntry.builder()
                .id(row.get("id", UUID.class))
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

    public static final String INSERT_NEWS_ENTRY = "INSERT INTO db.news_entry " +
            " (id, news_group_id, user_name, title, content, create_time, update_time, enabled, visible, flags) " +
            " VALUES" +
            " (:id, :newsGroupId, :userName, :title, :content, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(NewsEntry entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_NEWS_ENTRY)
                .bind("id", entry.getId())
                .bind("userName", entry.getUserName())
                .bind("title", entry.getTitle())
                .bind("createTime", entry.getCreateTime())
                .bind("enabled", entry.getEnabled())
                .bind("visible", entry.getVisible())
                .bind("flags", entry.getFlags());

        if (entry.getNewsGroupId() != null)
            execSpec = execSpec.bind("newsGroupId", entry.getNewsGroupId());
        else
            execSpec = execSpec.bindNull("newsGroupId", UUID.class);

        if (entry.getContent() != null)
            execSpec = execSpec.bind("content", entry.getContent());
        else
            execSpec = execSpec.bindNull("content", String.class);

        if (entry.getUpdateTime() != null)
            execSpec = execSpec.bind("updateTime", entry.getUpdateTime());
        else
            execSpec = execSpec.bind("updateTime", LocalDateTime.now());

        return execSpec.fetch().rowsUpdated();
    }

    @Override
    public Mono<Integer> transactionalInsert(NewsEntry newsEntry) {
        Iterable<NewsEntry> iterable = new LinkedList<>() {{ add(newsEntry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(createInsertAllTransaction1(connection, iterable)));
    }

    @Override
    public Mono<Integer> transactionalInsertAll(Iterable<NewsEntry> entries) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction1(connection, entries));
    }

    private Mono<Integer> createInsertAllTransaction1(Connection connection, Iterable<NewsEntry> entries) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements1(connection, entries))
                .reduce(Integer::sum)
                .delayUntil(r -> connection.commitTransaction())
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction1 ", e))
                .doFinally((st) -> connection.close());
    }

    private Flux<Integer> executeInsertStatements1(Connection connection, Iterable<NewsEntry> entries) {

        if (entries != null) {
            Iterator<NewsEntry> iterator = entries.iterator();
            if (iterator.hasNext()) {
                Statement statement = insertStatement(connection, iterator.next());
                while (iterator.hasNext()) {
                    statementBinding(statement.add(), iterator.next());
                }
                return Flux.from(statement.execute()).flatMap(Result::getRowsUpdated);
            }
        }
        return Flux.empty();
    }

    private Statement insertStatement(Connection connection, NewsEntry newsEntry) {
        return statementBinding(connection.createStatement(INSERT), newsEntry);
    }
}
