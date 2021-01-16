/*
 * This file was last modified at 2021.01.16 15:51 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ValueTypeDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import su.svn.daybook.domain.model.db.dictionary.ValueType;

public interface ValueTypeDao extends ReactiveCrudRepository<ValueType, Long> {
}
