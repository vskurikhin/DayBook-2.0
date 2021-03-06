/*
 * This file was last modified at 2021.03.06 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EhCacheConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import org.ehcache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.svn.daybook.domain.model.PageAllRecordViewImpl;
import su.svn.daybook.domain.model.PageAllRecordViewKey;
import su.svn.daybook.utils.CacheConstructor;

@Configuration
@EnableCaching
public class EhCacheConfiguration {

    public static final String PAGE_ALL_RECORD_VIEW_CACHE = "pageAllRecordViewCache";

    @Value("${application.cache.page-all-record-view.entries}")
    private int tagLabelCacheEntries;

    @Value("${application.cache.page-all-record-view.ttl-ms}")
    private int tagLabelCacheTtl;

    @Bean(PAGE_ALL_RECORD_VIEW_CACHE)
    public Cache<PageAllRecordViewKey, PageAllRecordViewImpl> pageAllRecordViewCache() {
        return CacheConstructor.<PageAllRecordViewKey, PageAllRecordViewImpl>builder()
                .cacheEntries(tagLabelCacheEntries)
                .ttlExpirationInMs(tagLabelCacheTtl)
                .keyClass(PageAllRecordViewKey.class)
                .valueClass(PageAllRecordViewImpl.class)
                .build().create(PAGE_ALL_RECORD_VIEW_CACHE);
    }
}
