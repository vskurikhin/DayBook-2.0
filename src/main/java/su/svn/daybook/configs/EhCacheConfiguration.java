/*
 * This file was last modified at 2020.11.15 22:00 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EhCacheConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import su.svn.daybook.domain.model.DBLongEntry;
import su.svn.daybook.domain.model.DBStringEntry;
import su.svn.daybook.domain.model.DBUuidEntry;

import java.time.Duration;
import java.util.UUID;

@Configuration
@EnableCaching
public class EhCacheConfiguration {

    @Value("${application.cache.long-cache.entries}")
    private int longCacheEntries;

    @Value("${application.cache.long-cache.ttl-ms}")
    private int longCacheTtl;

    @Value("${application.cache.string-cache.entries}")
    private int stringCacheEntries;

    @Value("${application.cache.string-cache.ttl-ms}")
    private int stringCacheTtl;

    @Value("${application.cache.uuid-cache.entries}")
    private int uuidCacheEntries;

    @Value("${application.cache.uuid-cache.ttl-ms}")
    private int uuidCacheTtl;

    @Bean("longCache")
    public Cache<Long, DBLongEntry> longCache() {
        ResourcePoolsBuilder rpBuilder = ResourcePoolsBuilder.heap(longCacheEntries);
        CacheConfigurationBuilder<Long, DBLongEntry> configurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Long.class, DBLongEntry.class, rpBuilder)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(longCacheTtl)));
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", configurationBuilder)
                .build();
        cacheManager.init();

        return cacheManager.createCache("longCache", configurationBuilder);
    }

    @Bean("stringCache")
    public Cache<String, DBStringEntry> stringCache() {
        ResourcePoolsBuilder rpBuilder = ResourcePoolsBuilder.heap(stringCacheEntries);
        CacheConfigurationBuilder<String, DBStringEntry> configurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(String.class, DBStringEntry.class, rpBuilder)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(stringCacheTtl)));
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", configurationBuilder)
                .build();
        cacheManager.init();

        return cacheManager.createCache("stringCache", configurationBuilder);
    }

    @Bean("uuidCache")
    public Cache<UUID, DBUuidEntry> uuidCache() {
        ResourcePoolsBuilder rpBuilder = ResourcePoolsBuilder.heap(stringCacheEntries);
        CacheConfigurationBuilder<UUID, DBUuidEntry> configurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(UUID.class, DBUuidEntry.class, rpBuilder)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(stringCacheTtl)));
        CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", configurationBuilder)
                .build();
        cacheManager.init();

        return cacheManager.createCache("uuidCache", configurationBuilder);
    }
}
