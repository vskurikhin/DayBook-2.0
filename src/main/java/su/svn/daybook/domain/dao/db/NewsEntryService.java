/*
 * This file was last modified at 2020.11.10 19:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryService.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import su.svn.daybook.domain.dao.db.db.NewsEntryDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

import java.util.UUID;

@Slf4j
@Service
public class NewsEntryService {

    public static final String INSERT_RECORD_2 = "INSERT INTO db.record " +
            " (record_id, position, type, user_name, create_time, update_time, enabled, visible, flags) " +
            " VALUES (:id, :position, :type, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    public static final String INSERT_NEWS_ENTRY_2 = "INSERT INTO db.news_entry " +
            " (news_entry_id, news_group_id, user_name, title, content, create_time, update_time, enabled, visible, " +
            "  flags) VALUES (:id, :newsGroupId, :userName, :title, :content, :createTime, :updateTime, :enabled, " +
            "  :visible, :flags)";

    private final DatabaseClient client;

    private final RecordDao recordDao;

    private final NewsEntryDao newsEntryDao;

    public NewsEntryService(DatabaseClient client, RecordDao recordDao, NewsEntryDao newsEntryDao) {
        this.client = client;
        this.recordDao = recordDao;
        this.newsEntryDao = newsEntryDao;
    }

    @Transactional
    public Mono<Void> insertNewsEntry(Record record, NewsEntry newsEntry) {
        UUID uuid = UUID.randomUUID();
        record.setId(uuid);
        newsEntry.setId(uuid);
        return client.execute(INSERT_RECORD_2)
                .bind("id", record.getId())
                .bind("position", record.getPosition())
                .bind("type", record.getType())
                .bind("userName", record.getUserName())
                .bind("createTime", record.getCreateTime())
                .bind("updateTime", record.getUpdateTime())
                .bind("enabled", record.getEnabled())
                .bind("visible", record.getVisible())
                .bind("flags", record.getFlags())
                .fetch().rowsUpdated()
                .then(client.execute(INSERT_NEWS_ENTRY_2)
                        .bind("id", newsEntry.getId())
                        .bind("newsGroupId", newsEntry.getNewsGroupId())
                        .bind("userName", newsEntry.getUserName())
                        .bind("title", newsEntry.getTitle())
                        .bind("content", newsEntry.getContent())
                        .bind("createTime", newsEntry.getCreateTime())
                        .bind("updateTime", newsEntry.getUpdateTime())
                        .bind("enabled", newsEntry.getEnabled())
                        .bind("visible", newsEntry.getVisible())
                        .bind("flags", newsEntry.getFlags())
                        .fetch().rowsUpdated())
                .then();
    }

    @Transactional
    public Mono<Tuple2<Record, NewsEntry>> insertNewsEntry2(Record record, NewsEntry newsEntry) {
        record.setId(UUID.randomUUID());
        recordDao.save(record);
        newsEntry.setId(record.getId());
        return recordDao.insert(record)
                .doOnSuccess(r -> log.info("record: {}", r))
                .zipWith(newsEntryDao.insert(newsEntry));
    }
}
