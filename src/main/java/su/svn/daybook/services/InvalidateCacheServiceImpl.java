/*
 * This file was last modified at 2021.03.07 12:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * InvalidateCacheServiceImpl.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.stereotype.Service;
import su.svn.daybook.configs.EhCacheConfiguration;
import su.svn.daybook.domain.model.PageAllRecordViewImpl;
import su.svn.daybook.domain.model.PageAllRecordViewKey;

@Slf4j
@Service
public class InvalidateCacheServiceImpl implements InvalidateCacheService {

    private final Cache<PageAllRecordViewKey, PageAllRecordViewImpl> pageAllRecordViewCache;

    public InvalidateCacheServiceImpl(Cache<PageAllRecordViewKey, PageAllRecordViewImpl> pageAllRecordViewCache) {
        this.pageAllRecordViewCache = pageAllRecordViewCache;
    }

    @Override
    public void invalidate(String name) {
        //noinspection SwitchStatementWithTooFewBranches
        switch (name) {
            case EhCacheConfiguration.PAGE_ALL_RECORD_VIEW_CACHE:
                pageAllRecordViewCache.clear();
                break;
        }
    }
}
