/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
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
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsEntryCustomizedDao;
import su.svn.daybook.domain.model.db.db.NewsEntry;

import java.util.Iterator;
import java.util.LinkedList;

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

    public static final String INSERT_NEWS_ENTRY = "INSERT INTO db.news_entry " +
            " (id, news_group_id, user_name, title, content, create_time, update_time, enabled, visible, flags) " +
            " VALUES" +
            " (:id, :newsGroupId, :userName, :title, :content, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(NewsEntry entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_NEWS_ENTRY)
                .bind("id", entry.getId())
                .bind("userName", entry.getUserName())
                .bind("title", entry.getTitle());

        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setUuid(execSpec, entry::getNewsGroupId, "newsGroupId");
        execSpec = GenericExecuteSpec.setString(execSpec, entry::getContent, "content");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

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
