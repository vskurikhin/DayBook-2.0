/*
 * This file was last modified at 2021.02.24 19:08 by Victor N. Skurikhin.
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.NewsEntryDto;
import su.svn.daybook.domain.model.db.db.AllRecordView;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

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
        String userName = getName();

        if (userName != null) {

            record.setUserName(userName);
            newsEntry.setUserName(userName);

            return recordDao.insert(record)
                    .doOnSuccess(i -> log.debug("insert {} count of record: {}", i, record))
                    .doOnError(e -> log.error("insertNewsEntry ", e))
                    .flatMap(i -> doOnInsert(i, newsEntry))
                    .doOnSuccess(i -> log.debug("insert {} count of newsEntry: {}", i, newsEntry))
                    .doOnError(e -> log.error("insertNewsEntry ", e));
        }
        return Mono.empty();
    }

    private String getName() {
        if (isNotValidContextAuthentication()) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    boolean isNotValidContextAuthentication() {
        return null == SecurityContextHolder.getContext().getAuthentication();
    }

    private Mono<NewsEntry> doOnInsert(Integer i, final NewsEntry newsEntry) {
        return i != null && i == 1
                ? newsEntryDao.insert(newsEntry).map(integer -> newsEntry)
                : Mono.error(new RuntimeException(" doOnInsert catch i: " + i));
    }

    @Transactional(readOnly = true)
    public Mono<NewsEntryDto> getNewsEntry(UUID id) {
        return newsEntryDao.monoById(id)
                .flatMap(newsEntry -> findRecordConvertToNewsEntry(newsEntry, id));
    }

    private Mono<NewsEntryDto> findRecordConvertToNewsEntry(NewsEntry newsEntry, UUID id) {
        return recordDao.monoById(id)
                .map(record -> buildNewsEntryDto(newsEntry, record));
    }

    private NewsEntryDto buildNewsEntryDto(NewsEntry newsEntry, Record record) {
        return NewsEntryDto.builder()
                .id(record.getId().toString())
                .newsGroupId(newsEntry.getNewsGroupId().toString())
                .title(newsEntry.getTitle())
                .content(newsEntry.getContent())
                .createTime(newsEntry.getCreateTime())
                .updateTime(newsEntry.getUpdateTime())
                .enabled(newsEntry.getEnabled())
                .visible(newsEntry.getVisible())
                .flags(newsEntry.getFlags())
                .build();
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

    @Transactional
    public  Mono<NewsEntry> insertNewsEntryNew(NewsEntryDto dto) {

        NewsEntry newsEntry = NewsEntry.builder()
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .content(dto.getContent())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();

        Record record = Record.builder()
                .position(Integer.MAX_VALUE / 2)
                .type(NewsEntry.class.getSimpleName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();

        return insertNewsEntry(record, newsEntry);
    }

    @Transactional
    public  Mono<NewsEntry> updateNewsEntryNew(NewsEntryDto dto) {

        NewsEntry newsEntry = NewsEntry.builder()
                .id(UUID.fromString(dto.getId()))
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .content(dto.getContent())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();

        Record record = Record.builder()
                .id(UUID.fromString(dto.getId()))
                .position(Integer.MAX_VALUE / 2)
                .type(NewsEntry.class.getSimpleName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();

        return recordDao.save(record).flatMap(new Function<Record, Mono<NewsEntry>>() {
            @Override
            public Mono<NewsEntry> apply(Record record) {
                return newsEntryDao.save(newsEntry);
            }
        });
    }
}
