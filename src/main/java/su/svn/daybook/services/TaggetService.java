/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.dao.db.db.TaggetDao;
import su.svn.daybook.domain.model.TagLabelDto;
import su.svn.daybook.domain.model.TagsForRecordDto;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.domain.model.db.db.Tagget;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TaggetService extends AbstractService<Tagget> {

    private final RecordDao recordDao;

    private final TaggetDao taggetDao;

    private final TagLabelService tagLabelService;

    public TaggetService(RecordDao recordDao, TaggetDao entryDao, TagLabelService tagLabelService) {
        this.recordDao = recordDao;
        this.taggetDao = entryDao;
        this.tagLabelService = tagLabelService;
    }

    @Transactional
    public Mono<Record> addTags(TagsForRecordDto tags) {
        log.trace("addTags({})", tags);
        return recordDao.monoById(tags.getId())
                .flatMap(record -> Mono.from(addTags(record, tags)));
    }

    private Publisher<Record> addTags(Record record, TagsForRecordDto tags) {
        log.trace("addTags({}, {})", record, tags);
        if (null == tags.getTags() || tags.getTags().isEmpty()) {
            return Flux.just(record);
        }
        return tagLabelService.readLabelIn(tags.getTags())
                .buffer(tags.getTags().size())
                .flatMap(labels -> addTags(record, new HashSet<>(labels), tags.getTags()));
    }

    private Publisher<Record> addTags(Record record, Set<TagLabelDto> saved, Set<String> tags) {
        log.trace("addTags({}, {}, {})", record, saved, tags);

        Set<String> ids = saved.stream().map(TagLabelDto::getId).collect(Collectors.toSet());
        tags.removeAll(saved.stream().map(TagLabelDto::getLabel).collect(Collectors.toSet()));

        if (!tags.isEmpty()) {
            Set<TagLabel> forSave = tags.stream()
                    .map(this::buildTagLabel)
                    .collect(Collectors.toSet());
            log.trace("addTags forSave: {}", forSave);
            return getSource(ids, forSave)
                    .buffer(forSave.size())
                    .flatMap(label -> insertFromSelect(record, ids));
        }
        return Flux.just(1).flatMap(i -> insertFromSelect(record, ids));
    }

    @Nonnull
    private Flux<TagLabel> getSource(Set<String> ids, Set<TagLabel> forSave) {
        return tagLabelService.create(forSave).doOnNext(label -> ids.add(label.getId()));
    }

    private Publisher<Record> insertFromSelect(Record record, Set<String> ids) {
        log.trace("insertFromSelect({}, {})", record, ids);
        String userName = SecurityContextUtil.getName(SecurityContextHolder.getContext());
        log.trace("insertFromSelect => userName: {}", userName);
        return taggetDao.deleteNotInIds(record.getId(), userName, ids)
                .doOnNext(i -> log.trace("deleteNotInIds i: {}", i))
                .doOnError(e -> log.error("delete ", e))
                .flatMap(i -> taggetDao.insertFromSelect(record.getId(), userName, ids))
                .doOnNext(i -> log.trace("insertFromSelect i: {}", i))
                .doOnError(e -> log.error("insert ", e))
                .map(l -> record);
    }

    private TagLabel buildTagLabel(String label) {
        String userName = SecurityContextUtil.getName(SecurityContextHolder.getContext());
        log.trace("buildTagLabel => userName: {}", userName);
        return TagLabel.builder()
                .label(label)
                .userName(userName)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
    }
}
