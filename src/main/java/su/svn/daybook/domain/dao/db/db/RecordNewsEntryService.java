/*
 * This file was last modified at 2021.01.12 21:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordNewsEntryService.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.NewsEntryRecordDto;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

import java.util.UUID;

@Slf4j
@Service
public class RecordNewsEntryService {

    private final RecordDao recordDao;

    private final NewsEntryDao newsEntryDao;

    public RecordNewsEntryService(RecordDao recordDao, NewsEntryDao newsEntryDao) {
        this.recordDao = recordDao;
        this.newsEntryDao = newsEntryDao;
    }

    @Transactional
    public Mono<NewsEntry> insertNewsEntry(Record record, NewsEntry newsEntry) {
        record.setId(UUID.randomUUID());
        newsEntry.setId(record.getId());
        return recordDao.insert(record)
                .doOnSuccess(i -> log.debug("insert {} count of record: {}", i, record))
                .doOnError(e -> log.error("insertNewsEntry ", e))
                .flatMap(i -> doOnInsert(i, newsEntry))
                .doOnSuccess(i -> log.debug("insert {} count of newsEntry: {}", i, newsEntry))
                .doOnError(e -> log.error("insertNewsEntry ", e));
    }

    private Mono<NewsEntry> doOnInsert(Integer i, final NewsEntry newsEntry) {
        return i != null && i == 1
                ? newsEntryDao.insert(newsEntry).map(integer -> newsEntry)
                : Mono.error(new RuntimeException(" doOnInsert catch i: " + i));
    }

    public Mono<NewsEntryRecordDto> getNewsEntryRecord(UUID id) {
        return newsEntryDao.monoById(id)
                .flatMap(newsEntry -> findRecordConvertToNewsEntryRecord(newsEntry, id));
    }

    private Mono<NewsEntryRecordDto> findRecordConvertToNewsEntryRecord(NewsEntry newsEntry, UUID id) {
        return recordDao.monoById(id)
                .map(record -> new NewsEntryRecordDto(record, newsEntry));
    }
}
