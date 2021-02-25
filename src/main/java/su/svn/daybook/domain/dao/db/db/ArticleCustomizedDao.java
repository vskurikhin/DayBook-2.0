/*
 * This file was last modified at 2021.02.25 16:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.db.Article;

public interface ArticleCustomizedDao {

    Mono<Integer> insert(Article entry);
}
