/*
 * This file was last modified at 2021.01.23 13:09 by Victor N. Skurikhin.
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
import su.svn.daybook.domain.model.RecordDto;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
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
                .flatMap(connection -> createInsertAllTransaction(connection, iterable));
    }

    @Override
    public Mono<Integer> transactionalInsertAll(Iterable<Record> entries) {
        return Mono.from(connectionFactory.create())
                .flatMap(connection -> createInsertAllTransaction(connection, entries));
    }

    public static final String SELECT_RECORDS = "SELECT " +
            " r.id r_id, r.position r_position, r.type r_type, r.user_name r_user_name, " +
            " r.create_time r_create_time, r.update_time r_update_time, r.enabled r_enabled, r.visible r_visible, " +
            " r.flags r_flags, ne.id ne_news_entry_id, ne.news_group_id ne_news_group_id, " +
            " ne.user_name ne_user_name, ne.title ne_title, ne.content ne_content, ne.create_time ne_create_time, " +
            " ne.update_time ne_update_time, ne.enabled ne_enabled, ne.visible ne_visible, ne.flags ne_flags " +
            " FROM db.record r LEFT JOIN db.news_entry ne ON r.id = ne.news_entry_id;";

    @Override
    public Flux<RecordDto<?>> selectRecords() {
        return client.execute(SELECT_RECORDS).fetch().all().map(this::getRecord);
    }

    private RecordDto<?> getRecord(Map<String, Object> map) {
        String type = map.getOrDefault("r_type", "null").toString();
        switch (type) {
            case "NewsEntry": return getRecordNewsEntry(map);
            default: return getRecordEmpty(map);
        }
    }

    private RecordDto<?> getRecordNewsEntry(Map<String, Object> map) {
        RecordDto<?> recordDto = getRecordEmpty(map);
        NewsEntry.Builder newsEntryBuilder = NewsEntry.builder();
        newsEntryBuilder.id(UUID.fromString(map.get("ne_news_entry_id").toString()));
        Object neNewsGroupId = map.get("ne_news_group_id");
        if (neNewsGroupId != null) {
            newsEntryBuilder.newsGroupId(UUID.fromString(neNewsGroupId.toString()));
        }
        Object neUserName = map.get("ne_user_name");
        if (neUserName != null) {
            newsEntryBuilder.userName(neUserName.toString());
        }
        newsEntryBuilder.title(map.get("ne_title").toString());
        Object neContent = map.get("ne_content");
        if (neContent != null) {
            newsEntryBuilder.content(neContent.toString());
        }
        Object neCreateTime = map.get("ne_create_time");
        if (neCreateTime instanceof LocalDateTime) {
            newsEntryBuilder.createTime((LocalDateTime) neCreateTime);
        }
        Object neUpdateTime = map.get("ne_update_time");
        if (neUpdateTime instanceof LocalDateTime) {
            newsEntryBuilder.updateTime((LocalDateTime) neUpdateTime);
        }
        Object neEnabled = map.get("ne_enabled");
        newsEntryBuilder.enabled(neEnabled != null ? (Boolean) neEnabled : false);
        Object neVisible = map.get("ne_visible");
        newsEntryBuilder.visible(neVisible != null ? (Boolean) neVisible : false);
        Object neFlags = map.get("ne_flags");
        if (neFlags instanceof Integer) {
            newsEntryBuilder.flags((Integer) neFlags);
        }

        return new RecordDto<>(recordDto.getRecord(), newsEntryBuilder.build());
    }

    private RecordDto<?> getRecordEmpty(Map<String, Object> map) {
        Record.Builder recordBuilder = Record.builder();
        recordBuilder.id(UUID.fromString(map.get("r_id").toString()));
        Object rPosition = map.get("r_position");
        if (rPosition instanceof Integer) {
            recordBuilder.position((Integer) rPosition);
        }
        Object rUserName = map.get("r_user_name");
        if (rUserName != null) {
            recordBuilder.userName(rUserName.toString());
        }
        Object rType = map.get("r_type");
        if (rType != null) {
            recordBuilder.type(rType.toString());
        }
        Object rCreateTime = map.get("r_create_time");
        if (rCreateTime instanceof LocalDateTime) {
            recordBuilder.createTime((LocalDateTime) rCreateTime);
        }
        Object neUpdateTime = map.get("r_update_time");
        if (neUpdateTime instanceof LocalDateTime) {
            recordBuilder.updateTime((LocalDateTime) neUpdateTime);
        }
        Object neEnabled = map.get("r_enabled");
        recordBuilder.enabled(neEnabled != null ? (Boolean) neEnabled : false);
        Object neVisible = map.get("r_visible");
        recordBuilder.visible(neVisible != null ? (Boolean) neVisible : false);
        Object neFlags = map.get("r_flags");
        if (neFlags instanceof Integer) {
            recordBuilder.flags((Integer) neFlags);
        }

        return new RecordDto<>(recordBuilder.build());
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
