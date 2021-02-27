/*
 * This file was last modified at 2021.02.27 22:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelDao.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary;

import io.micrometer.core.lang.NonNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;

import java.util.Set;

public interface TagLabelDao extends ReactiveCrudRepository<TagLabel, String>, TagLabelCustomizedDao {

    @Query("SELECT * FROM dictionary.tag_label WHERE id IN (:ids) AND enabled")
    Flux<TagLabel> fluxAllById(Set<String> ids);

    @Query("SELECT * FROM dictionary.tag_label WHERE label IN (:labels) AND enabled")
    Flux<TagLabel> fluxAllLabelIn(Set<String> labels);

    @NonNull
    @Query("SELECT dictionary.next_val_tag_label_seq()")
    Mono<String> dictionaryNextValTagLabelSeq();
}
