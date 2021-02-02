/*
 * This file was last modified at 2021.02.02 19:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordNewsEntryService.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.NewsEntryRecordDto;
import su.svn.daybook.domain.model.db.db.AllRecordView;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class RecordNewsEntryService {

    public static final Sort.Order ORDER_DESC_BY_UPDATE_TIME = Sort.Order.desc("updateTime");

    public static final Sort.Order ORDER_ASC_BY_POSITION = Sort.Order.by("position");

    public static final Sort DEFAULT_SORT = Sort.by(ORDER_DESC_BY_UPDATE_TIME, ORDER_ASC_BY_POSITION);

    private final AllRecordViewDao allRecordViewDao;

    private final RecordDao recordDao;

    private final NewsEntryDao newsEntryDao;

    public RecordNewsEntryService(AllRecordViewDao allRecordViewDao, RecordDao recordDao, NewsEntryDao newsEntryDao) {
        this.allRecordViewDao = allRecordViewDao;
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

    @Transactional(readOnly = true)
    public Mono<NewsEntryRecordDto> getNewsEntryRecord(UUID id) {
        return newsEntryDao.monoById(id)
                .flatMap(newsEntry -> findRecordConvertToNewsEntryRecord(newsEntry, id));
    }

    private Mono<NewsEntryRecordDto> findRecordConvertToNewsEntryRecord(NewsEntry newsEntry, UUID id) {
        return recordDao.monoById(id)
                .map(record -> new NewsEntryRecordDto(record, newsEntry));
    }

    @Transactional(readOnly = true)
    public Mono<Page<AllRecordView>> getRecords(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return Mono.from(findAllRecordViewToPage(pageable, size));
    }

    @Nonnull
    private Flux<Page<AllRecordView>> findAllRecordViewToPage(final Pageable pageable, int size) {
        return recordDao.monoCount()
                .map(mayBeNull -> mayBeNull != null ? mayBeNull : pageable.getPageSize())
                .flatMapMany(totalCount -> findAllRecordViewToPage(pageable, totalCount, size));
    }

    private Flux<Page<AllRecordView>> findAllRecordViewToPage(final Pageable pageable, long totalCount, int size) {
        return allRecordViewDao.findAllByEnabledIsTrue(pageable)
                .buffer(size)
                .map(list -> newPage(list, pageable, totalCount));
    }

    private Page<AllRecordView> newPage(List<AllRecordView> list, Pageable pageable, long totalCount) {
        return new PageImpl<>(list, pageable, totalCount);
    }
}
