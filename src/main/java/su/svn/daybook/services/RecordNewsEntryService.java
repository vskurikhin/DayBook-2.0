/*
 * This file was last modified at 2021.03.06 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordNewsEntryService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.AllRecordViewDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.model.PageAllRecordViewImpl;
import su.svn.daybook.domain.model.PageAllRecordViewKey;
import su.svn.daybook.domain.model.db.db.AllRecordView;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class RecordNewsEntryService {

    public static final Sort.Order ORDER_DESC_BY_UPDATE_TIME = Sort.Order.desc("updateTime");

    public static final Sort.Order ORDER_ASC_BY_POSITION = Sort.Order.by("position");

    public static final Sort DEFAULT_SORT = Sort.by(ORDER_DESC_BY_UPDATE_TIME, ORDER_ASC_BY_POSITION);

    private final AllRecordViewDao allRecordViewDao;

    private final RecordDao recordDao;


    private final Cache<PageAllRecordViewKey, PageAllRecordViewImpl> pageAllRecordViewCache;

    private final ExecutorService executor;

    public RecordNewsEntryService(
            AllRecordViewDao allRecordViewDao,
            RecordDao recordDao,
            @Qualifier("pageAllRecordViewCache")
            Cache<PageAllRecordViewKey, PageAllRecordViewImpl> pageAllRecordViewCache) {
        this.allRecordViewDao = allRecordViewDao;
        this.recordDao = recordDao;
        this.pageAllRecordViewCache = pageAllRecordViewCache;
        this.executor = Executors.newSingleThreadExecutor();
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

        PageAllRecordViewKey key = new PageAllRecordViewKey(pageable, totalCount, size);
        PageAllRecordViewImpl value = pageAllRecordViewCache.get(key);

        if (value != null) {
            log.info("Cache hit key: {}", key);
            cacheAllRecordViewToPage(key);

            return Flux.just(value);
        }
        return allRecordViewDao.findAllByEnabledIsTrue(pageable)
                .buffer(size)
                .doOnNext(allRecordViews -> putCacheAllRecordViewToPage(key, allRecordViews))
                .map(list -> newPage(list, pageable, totalCount));
    }

    private Page<AllRecordView> newPage(List<AllRecordView> list, Pageable pageable, long totalCount) {
        return new PageImpl<>(list, pageable, totalCount);
    }

    private void cacheAllRecordViewToPage(PageAllRecordViewKey key) {
        executor.submit(() -> runUpdateCacheAllRecordViewToPage(key));
    }

    private void runUpdateCacheAllRecordViewToPage(PageAllRecordViewKey key) {
        updateCacheAllRecordViewToPage(key).subscribe();
    }

    @Nonnull
    private Flux<List<AllRecordView>> updateCacheAllRecordViewToPage(PageAllRecordViewKey key) {
        return allRecordViewDao.findAllByEnabledIsTrue(key.getPageable())
                .buffer(key.getSize())
                .doOnNext(allRecordViews -> putCacheAllRecordViewToPage(key, allRecordViews));
    }

    private void putCacheAllRecordViewToPage(PageAllRecordViewKey key, List<AllRecordView> allRecordViews) {
        PageAllRecordViewImpl value = new PageAllRecordViewImpl(allRecordViews, key.getPageable());
        pageAllRecordViewCache.put(key, value);
    }
}
