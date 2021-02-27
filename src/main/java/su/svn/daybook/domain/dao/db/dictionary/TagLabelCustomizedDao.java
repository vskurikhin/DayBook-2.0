/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelCustomizedDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary;

import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;

public interface TagLabelCustomizedDao {

    Mono<Integer> insert(TagLabel entry);
}
