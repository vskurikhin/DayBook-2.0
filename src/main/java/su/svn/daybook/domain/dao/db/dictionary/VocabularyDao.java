/*
 * This file was last modified at 2020.09.02 10:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import su.svn.daybook.domain.model.db.dictionary.Vocabulary;

public interface VocabularyDao extends ReactiveCrudRepository<Vocabulary, Long> {
}
