/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
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
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.RecordCustomizedDao;
import su.svn.daybook.domain.model.db.db.Record;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

@Slf4j
public class RecordDaoImpl implements RecordCustomizedDao {

    private final ConnectionFactory connectionFactory;

    private final DatabaseClient client;

    public RecordDaoImpl(ConnectionFactory connectionFactory, DatabaseClient client) {
        this.connectionFactory = connectionFactory;
        this.client = client;
    }

    public static final String INSERT = "INSERT INTO db.record " +
            " (id, position, type, user_name, create_time, update_time, enabled, visible, flags) " +
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

    public static final String INSERT_RECORD = "INSERT INTO db.record " +
            " (id, position, type, user_name, create_time, update_time, enabled, visible, flags) " +
            " VALUES (:id, :position, :type, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(Record entry) {

        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_RECORD)
                .bind("id", entry.getId())
                .bind("position", entry.getPosition())
                .bind("type", entry.getType())
                .bind("userName", entry.getUserName());

        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

        return execSpec.fetch().rowsUpdated();
    }

    @Override
    public Mono<Integer> transactionalInsert(Record entry) {
        Iterable<Record> iterable = new LinkedList<>() {{ add(entry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction(connection, iterable));
    }

    @Override
    public Mono<Integer> transactionalInsertAll(Iterable<Record> entries) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction(connection, entries));
    }

    private Mono<Integer> createInsertAllTransaction(Connection connection, Iterable<Record> entries) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements(connection, entries))
                .reduce(Integer::sum)
                .delayUntil(r -> connection.commitTransaction())
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction1 ", e))
                .doFinally((st) -> connection.close());
    }

    private Flux<Integer> executeInsertStatements(Connection connection, Iterable<Record> entries) {

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
