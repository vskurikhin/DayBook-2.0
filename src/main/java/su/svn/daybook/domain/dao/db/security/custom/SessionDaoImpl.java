/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security.custom;

import io.r2dbc.spi.Connection;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Result;
import io.r2dbc.spi.Statement;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.security.SessionCustomizedDao;
import su.svn.daybook.domain.model.db.security.Session;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

@Slf4j
public class SessionDaoImpl implements SessionCustomizedDao {

    private final ConnectionFactory connectionFactory;

    private final DatabaseClient client;

    public SessionDaoImpl(ConnectionFactory connectionFactory, DatabaseClient client) {
        this.connectionFactory = connectionFactory;
        this.client = client;
    }

    public static final String INSERT = "INSERT INTO security.session " +
            " (user_name, session_id, create_time, end_time, update_time, enabled, visible, flags) " +
            " VALUES ($1, $2, $3, $4, $5, $6, $7, $8)";

    private Statement statementBinding(Statement statement, Session session) {
        return statement
                .bind("$1", session.getId())
                .bind("$2", session.getSessionId())
                .bind("$3", session.getCreateTime())
                .bind("$4", session.getEndTime())
                .bind("$5", session.getUpdateTime())
                .bind("$6", session.getEnabled())
                .bind("$7", session.getVisible())
                .bind("$8", session.getFlags());
    }

    private Publisher<? extends Session> extractResult(Result result) {
        return result.map((row, rowMetadata) -> Session.builder()
                .id(row.get("user_name", String.class))
                .sessionId(row.get("session_id", UUID.class))
                .createTime(row.get("create_time", LocalDateTime.class))
                .endTime(row.get("end_time", LocalDateTime.class))
                .updateTime(row.get("update_time", LocalDateTime.class))
                .enabled(row.get("enabled", Boolean.class))
                .visible(row.get("visible", Boolean.class))
                .flags(row.get("flags", Integer.class))
                .build());
    }

    public static final String INSERT_SESSION = "INSERT INTO security.session " +
            " (user_name, session_id, create_time, end_time, update_time, enabled, visible, flags) " +
            " VALUES" +
            " (:userName, :sessionId, :createTime, :endTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(Session entry) {
        return client.execute(INSERT_SESSION)
                .bind("userName", entry.getId())
                .bind("sessionId", entry.getSessionId())
                .bind("createTime", entry.getCreateTime())
                .bind("endTime", entry.getEndTime())
                .bind("updateTime", entry.getUpdateTime())
                .bind("enabled", entry.getEnabled())
                .bind("visible", entry.getVisible())
                .bind("flags", entry.getFlags())
                .fetch().rowsUpdated();
    }

    @Override
    public Mono<Session> transactionalInsert(Session entry) {
        Iterable<Session> iterable = new LinkedList<>() {{ add(entry); }};
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> Mono.from(createInsertAllTransaction(connection, iterable)));
    }

    @Override
    public Flux<Session> transactionalInsertAll(Iterable<Session> entries) {
        return Mono.from(connectionFactory.create())
                .flatMapMany(connection -> createInsertAllTransaction(connection, entries));
    }

    private Publisher<? extends Session> createInsertAllTransaction(Connection connection, Iterable<Session> iterable) {
        return Mono.from(connection.beginTransaction())
                .thenMany(executeInsertStatements(connection, iterable))
                .collectList()
                .flatMapMany(Flux::fromIterable)
                .delayUntil(r -> connection.commitTransaction())
                .onErrorResume(t -> Mono.just(connection.rollbackTransaction()).then(Mono.error(t)))
                .doOnError(e -> log.error("createInsertAllTransaction ", e))
                .doFinally((st) -> connection.close())
                .flatMap(this::extractResult);
    }

    private Publisher<? extends Result> executeInsertStatements(Connection connection, Iterable<Session> iterable) {

        if (iterable != null) {
            Iterator<Session> iterator = iterable.iterator();
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

    private Statement insertStatement(Connection connection, Session entry) {
        return statementBinding(connection.createStatement(INSERT), entry);
    }
}
