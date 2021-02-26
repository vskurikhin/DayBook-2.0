/*
 * This file was last modified at 2021.02.26 17:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsGroupDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.model.NewsGroupDto;
import su.svn.daybook.domain.model.db.db.NewsGroup;
import su.svn.daybook.exceptions.NameRequiredException;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class NewsGroupService extends AbstractService<NewsGroup> {

    public static final Sort.Order ORDER_ASC_BY_USER_NAME = Sort.Order.by("userName");

    public static final Sort DEFAULT_SORT = Sort.by(ORDER_ASC_BY_USER_NAME);

    private final NewsGroupDao entryDao;

    public NewsGroupService(NewsGroupDao articleDao) {
        this.entryDao = articleDao;
    }

    @Transactional
    public  Mono<NewsGroup> create(NewsGroupDto dto) {
        log.trace("create({})", dto);

        NewsGroup entry = NewsGroup.builder()
                .groupName(dto.getGroupName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        entry.setId(UUID.randomUUID());

        return entryDao.insert(entry)
                .map(e -> entry)
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(entry)));
    }

    @Transactional(readOnly = true)
    public  Mono<NewsGroupDto> read(UUID id) {
        log.trace("read({})", id);
        return entryDao.monoById(id)
                .map(this::buildDto)
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(id)));
    }

    @Transactional(readOnly = true)
    public Flux<NewsGroup> getAll() {
        return entryDao.findAllByEnabledIsTrue(DEFAULT_SORT);
    }

    @Transactional(readOnly = true)
    public Flux<NewsGroup> getAllLike(String prefix) {
        return entryDao.fluxAllLike(prefix);
    }

    @Transactional(readOnly = true)
    public Mono<Page<NewsGroup>> getPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, DEFAULT_SORT);
        return Mono.from(findAllToPage(pageable, size));
    }

    @Nonnull
    private Flux<Page<NewsGroup>> findAllToPage(final Pageable pageable, int size) {
        return entryDao.monoCount()
                .map(mayBeNull -> mayBeNull != null ? mayBeNull : pageable.getPageSize())
                .flatMapMany(totalCount -> findAllToPage(pageable, totalCount, size));
    }

    private Flux<Page<NewsGroup>> findAllToPage(final Pageable pageable, long totalCount, int size) {
        return entryDao.findAllByEnabledIsTrue(pageable)
                .buffer(size)
                .map(list -> newPage(list, pageable, totalCount));
    }

    private Page<NewsGroup> newPage(List<NewsGroup> list, Pageable pageable, long totalCount) {
        return new PageImpl<>(list, pageable, totalCount);
    }

    @Transactional
    public  Mono<NewsGroup> update(NewsGroupDto dto) {
        log.trace("update({})", dto);

        NewsGroup entry = NewsGroup.builder()
                .id(UUID.fromString(dto.getId()))
                .groupName(dto.getGroupName())
                .createTime(dto.getCreateTime())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        return entryDao.save(entry);
    }

    @Override
    protected RecordDao getRecordDao() {
        return null;
    }

    @Override
    protected Mono<Integer> insertEntry(NewsGroup entry) {
        return entryDao.insert(entry);
    }

    private NewsGroupDto buildDto(NewsGroup entry) {
        return NewsGroupDto.builder()
                .id(entry.getId().toString())
                .groupName(entry.getGroupName())
                .createTime(entry.getCreateTime())
                .updateTime(entry.getUpdateTime())
                .enabled(entry.getEnabled())
                .visible(entry.getVisible())
                .flags(entry.getFlags())
                .build();
    }
}
