/*
 * This file was last modified at 2021.03.06 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CacheConstructor.java
 * $Id$
 */

package su.svn.daybook.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

public class CacheConstructor<K, V> {

    private final CacheManager cacheManager;

    private final CacheConfigurationBuilder<K, V> configurationBuilder;

    private CacheConstructor(int cacheEntries, long ttlExpirationInMs, Class<K> keyClass, Class<V> valueClass) {
        ResourcePoolsBuilder rpBuilder = ResourcePoolsBuilder.heap(cacheEntries);
        configurationBuilder = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(keyClass, valueClass, rpBuilder)
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofMillis(ttlExpirationInMs)));
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("preConfigured", configurationBuilder)
                .build();
        cacheManager.init();
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder<>();
    }

    public Cache<K, V> create(String name) {
        return cacheManager.createCache(name, configurationBuilder);
    }

    public static class Builder<K, V> {
        private int cacheEntries;

        private long ttlExpirationInMs;

        private Class<K> keyClass;

        private Class<V> valueClass;

        Builder() {
        }

        public Builder<K, V> cacheEntries(int cacheEntries) {
            this.cacheEntries = cacheEntries;
            return this;
        }

        public Builder<K, V> ttlExpirationInMs(long ttlExpirationInMs) {
            this.ttlExpirationInMs = ttlExpirationInMs;
            return this;
        }

        public Builder<K, V> keyClass(Class<K> keyClass) {
            this.keyClass = keyClass;
            return this;
        }

        public Builder<K, V> valueClass(Class<V> valueClass) {
            this.valueClass = valueClass;
            return this;
        }

        public CacheConstructor<K, V> build() {
            return new CacheConstructor<>(cacheEntries, ttlExpirationInMs, keyClass, valueClass);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "cacheEntries=" + cacheEntries +
                    ", ttlExpirationInMs=" + ttlExpirationInMs +
                    ", keyClass=" + keyClass +
                    ", valueClass=" + valueClass +
                    '}';
        }
    }
}
