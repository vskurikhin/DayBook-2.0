/*
 * This file was last modified at 2020.12.23 09:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.security;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.security.Session;

public interface SessionCustomizedDao {
    Mono<Integer> insert(Session entry);

    Mono<Session> transactionalInsert(Session entry);

    Flux<Session> transactionalInsertAll(Iterable<Session> entries);
}
