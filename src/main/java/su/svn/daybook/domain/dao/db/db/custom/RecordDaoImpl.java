/*
 * This file was last modified at 2020.11.10 22:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoImpl.java
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
import su.svn.daybook.domain.dao.db.db.RecordCustomizedDao;
import su.svn.daybook.domain.model.db.db.Record;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
public class RecordDaoImpl implements RecordCustomizedDao {

    private final ConnectionFactory connectionFactory;

    private final DatabaseClient client;

    public RecordDaoImpl(ConnectionFactory connectionFactory, DatabaseClient client) {
        this.connectionFactory = connectionFactory;
        this.client = client;
    }

    public static final String INSERT = "INSERT INTO db.record " +
            " (record_id, position, type, user_name, create_time, update_time, enabled, visible, flags) " +
            " VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9)";

    private Statement statementBinding(Statement statement, Record entry) {
        return statement
                .bind("$1", entry.getId())
                .bind("$2", entry.getPosition())
                .bind("$3", entry.getType())
                .bind("$4", entry.getUserName())
                .bind("$5", entry.getCreateTime())
                .bind("$6", entry.getUpdateTime())
                .bind("$7", entry.getEnabled())
                .bind("$8", entry.getVisible())
                .bind("$9", entry.getFlags());
    }

    private Publisher<? extends Record> extractResult(Result result) {
        return result.map((row, rowMetadata) -> {
            Integer position = row.get("position", Integer.class);
            return Record.builder()
                    .id(row.get("record_id", UUID.class))
                    .position(position != null ? position : 0)
                    .type(row.get("type", String.class))
                    .userName(row.get("user_name", String.class))
                    .createTime(row.get("create_time", LocalDateTime.class))
                    .updateTime(row.get("update_time", LocalDateTime.class))
                    .enabled(row.get("enabled", Boolean.class))
                    .visible(row.get("visible", Boolean.class))
                    .flags(row.get("flags", Integer.class))
                    .build();
        });
    }

    public static final String INSERT_RECORD = "INSERT INTO db.record " +
            " (record_id, position, type, user_name, create_time, update_time, enabled, visible, flags) " +
            " VALUES (:id, :position, :type, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(Record entry) {
        return client.execute(INSERT_RECORD)
                .bind("id", entry.getId())
                .bind("position", entry.getPosition())
                .bind("type", entry.getType())
                .bind("userName", entry.getUserName())
                .bind("createTime", entry.getCreateTime())
                .bind("updateTime", entry.getUpdateTime())
                .bind("enabled", entry.getEnabled())
                .bind("visible", entry.getVisible())
                .bind("flags", entry.getFlags())
                .fetch().rowsUpdated();
    }

    @Override
    public Mono<Record> transactionalInsert(Record newsEntry) {
        Iterable<Record> iterable = new LinkedList<>() {{ add(newsEntry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(createInsertAllTransaction(connection, iterable)));
    }

    @Override
    public Flux<Record> transactionalInsertAll(Iterable<Record> entries) {
        return Mono.from(connectionFactory.create())
                .flatMapMany(connection -> createInsertAllTransaction(connection, entries));
    }

    private Publisher<? extends Record> createInsertAllTransaction(Connection connection, Iterable<Record> entries) {
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

    private Publisher<? extends Result> executeInsertStatements(Connection connection, Iterable<Record> entries) {

        if (entries != null) {
            Iterator<Record> iterator = entries.iterator();
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

    private Statement insertStatement(Connection connection, Record entry) {
        return statementBinding(connection.createStatement(INSERT), entry);
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
