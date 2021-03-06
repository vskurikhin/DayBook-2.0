/*
 * This file was last modified at 2021.03.07 23:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.dictionary.TagLabelDao;
import su.svn.daybook.domain.model.DBUserOwnedEntry;
import su.svn.daybook.domain.model.TagLabelDto;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;
import su.svn.daybook.exceptions.NameRequiredException;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

@Slf4j
@Service
public class TagLabelService {

    private final TagLabelDao entryDao;

    public TagLabelService(TagLabelDao entryDao) {
        this.entryDao = entryDao;
    }

    @Transactional
    public Mono<TagLabel> create(TagLabelDto dto) {
        log.trace("create({})", dto);

        TagLabel entry = TagLabel.builder()
                .label(dto.getLabel())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        return this.create(entry);
    }

    @Transactional
    public Mono<TagLabel> create(TagLabel entry) {
        log.trace("create({})", entry);
        return doCreate(entry).flatMap(i -> doOnInsert(i, entry))
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(entry)));
    }

    @Transactional
    public Flux<TagLabel> create(Collection<TagLabel> entries) {
        log.trace("create({})", entries);
        return Flux.fromStream(entries.stream()).flatMap(this::create);
    }

    private Mono<Integer> doCreate(TagLabel entry) {
        log.trace("create({})", entry);
        return entryDao.dictionaryNextValTagLabelSeq()
                .flatMap(id -> setIdInsert(id, entry))
                .doOnSuccess(i -> log.debug("insert {} count of entry: {}", i, entry))
                .doOnError(e -> log.error("insert ", e));
    }

    @Transactional(readOnly = true)
    public Flux<TagLabelDto> readAll() {
        return entryDao.findAll().map(this::buildTagLabelDto);
    }

    @Transactional(readOnly = true)
    public Flux<TagLabelDto> readLabelIn(Set<String> labels) {
        return entryDao.fluxAllLabelIn(labels).map(this::buildTagLabelDto);
    }

    @Transactional(readOnly = true)
    public Flux<TagLabel> entriesLabelIn(Set<String> labels) {
        return entryDao.fluxAllLabelIn(labels);
    }

    public TagLabelDto buildTagLabelDto(TagLabel tagLabel) {
        return TagLabelDto.builder()
                .id(tagLabel.getId())
                .label(tagLabel.getLabel())
                .userName(tagLabel.getUserName())
                .visible(tagLabel.getVisible())
                .build();
    }

    @Transactional
    public  Mono<TagLabel> update(TagLabelDto dto) {
        log.trace("update({})", dto);

        TagLabel entry = TagLabel.builder()
                .id(dto.getId())
                .label(dto.getLabel())
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        return this.update(entry);
    }

    @Transactional
    public  Mono<TagLabel> update(TagLabel entry) {
        log.trace("update({})", entry);
        return entryDao.save(entry);
    }

    private Mono<Integer> setIdInsert(String id, TagLabel entry) {
        entry.setId(id);
        return entryDao.insert(entry);
    }

    private Mono<TagLabel> doOnInsert(Integer i, final TagLabel entry) {
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
