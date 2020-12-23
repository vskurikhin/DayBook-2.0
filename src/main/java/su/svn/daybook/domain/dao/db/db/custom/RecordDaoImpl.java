/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
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
import reactor.core.publisher.Signal;
import su.svn.daybook.domain.dao.db.db.RecordCustomizedDao;
import su.svn.daybook.domain.model.DBUuidEntry;
import su.svn.daybook.domain.model.db.db.Record;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.function.Consumer;

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
    public Mono<Integer> transactionalInsert(Record entry) {
        Iterable<Record> iterable = new LinkedList<>() {{ add(entry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction1(connection, iterable));
    }

    @Override
    public Mono<Integer> transactionalInsertAll(Iterable<Record> entries) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction1(connection, entries));
    }

    private Publisher<? extends Record> createInsertAllTransaction2(Connection connection, Iterable<Record> entries) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements2(connection, entries))
                .delayUntil(r -> connection.commitTransaction())
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction ", e))
                .doFinally((st) -> connection.close());
    }

    private Flux<Record> executeInsertStatements2(Connection connection, Iterable<Record> entries) {

        if (entries != null) {
            Iterator<Record> iterator = entries.iterator();
            if (iterator.hasNext()) {
                Statement statement = insertStatement(connection, iterator.next());
                while (iterator.hasNext()) {
                    statementBinding(statement.add(), iterator.next());
                }
                return Flux.from(statement.returnGeneratedValues("record_id").execute())
                        .flatMap(result -> result.map((row, rowMetadata) -> Record.builder()
                                .id(row.get(1, UUID.class))
                                .build()));
            }
        }
        return Flux.empty();
    }

    private Mono<Integer> createInsertAllTransaction1(Connection connection, Iterable<Record> entries) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements1(connection, entries))
                .reduce(Integer::sum)
                .delayUntil(r -> connection.commitTransaction())
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction1 ", e))
                .doFinally((st) -> connection.close());
    }


    private Flux<Integer> executeInsertStatements1(Connection connection, Iterable<Record> entries) {

        if (entries != null) {
            Iterator<Record> iterator = entries.iterator();
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

    private <T extends DBUuidEntry> Publisher<?> getResult(Signal<? extends Result> signal, T record) {
        return getResult(signal.get(), record);
    }

    private <T extends DBUuidEntry> Publisher<?> getResult(Result result, T t) {
        return Mono.from(result.getRowsUpdated()).doOnSuccess(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                log.warn("integer: {}", integer);
            }
        }).then(Mono.from(result.map((row, rowMetadata) -> {
            log.warn("row: {}", row);
            log.warn("rowMetadata: {}", rowMetadata);
            t.setId(row.get(1, UUID.class));
            return t;
        })));
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
