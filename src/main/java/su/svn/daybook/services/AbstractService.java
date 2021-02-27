/*
 * This file was last modified at 2021.02.27 11:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.DBUserOwnedEntry;
import su.svn.daybook.domain.model.DBUuidEntry;

import java.util.function.Function;

@Slf4j
public abstract class AbstractService<E extends DBUuidEntry> {

    @Transactional
    public Mono<E> insert(Function<E, Mono<Integer>> daoInsertMethod, E entry) {
        log.trace("insert({}, {})", daoInsertMethod, entry);

        return daoInsertMethod.apply(entry)
                .doOnSuccess(i -> log.debug("insert {} count of entry: {}", i, entry))
                .doOnError(e -> log.error("insert ", e))
                .flatMap(i -> doOnInsert(i, entry));
    }

    private Mono<E> doOnInsert(Integer i, final E entry) {
        return i != null && i == 1
                ? Mono.just(entry)
                : Mono.error(new RuntimeException(" doOnInsert catch i: " + i));
    }

    protected void setUserName(SecurityContext context, DBUserOwnedEntry entry) {
        String userName = SecurityContextUtil.getName(context);
        log.trace("insert => userName: {}", userName);
        if (userName != null) {
            entry.setUserName(userName);
        }
    }
}
