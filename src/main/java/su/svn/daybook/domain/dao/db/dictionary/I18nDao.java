/*
 * This file was last modified at 2020.09.05 17:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * I18nDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import su.svn.daybook.domain.model.db.dictionary.I18n;

public interface I18nDao extends ReactiveCrudRepository<I18n, Long> {
}
