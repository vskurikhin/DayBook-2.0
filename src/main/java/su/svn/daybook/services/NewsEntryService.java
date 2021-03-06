/*
 * This file was last modified at 2021.03.09 22:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsEntryDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.dao.db.db.TaggetRecordViewDao;
import su.svn.daybook.domain.model.NewsEntryDto;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.domain.model.db.db.TaggetRecordView;
import su.svn.daybook.exceptions.NameRequiredException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class NewsEntryService extends AbstractRecordService<NewsEntry> {

    private final NewsEntryDao entryDao;

    private final RecordDao recordDao;

    private final TaggetRecordViewDao taggetRecordViewDao;

    public NewsEntryService(NewsEntryDao articleDao, RecordDao recordDao, TaggetRecordViewDao taggetRecordViewDao) {
        this.entryDao = articleDao;
        this.recordDao = recordDao;
        this.taggetRecordViewDao = taggetRecordViewDao;
    }

    @Transactional
    public  Mono<NewsEntry> create(NewsEntryDto dto) {
        log.trace("create({})", dto);

        NewsEntry newsEntry = NewsEntry.builder()
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .content(dto.getContent())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible() != null ? dto.getVisible() : true)
                .build();
        setUserName(SecurityContextHolder.getContext(), newsEntry);

        Record record = Record.builder()
                .position(Integer.MAX_VALUE / 2)
                .type(NewsEntry.class.getSimpleName())
                .createTime(LocalDateTime.now())
                .publicTime(dto.getPublicTime() != null ? dto.getPublicTime() : LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible() != null ? dto.getVisible() : true)
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        record.setId(UUID.randomUUID());
        newsEntry.setId(record.getId());

        return super.insert(record, newsEntry);
    }

    @Transactional
    public  Mono<NewsEntry> create(Record record, NewsEntry entry) {
        log.trace("create({}, {})", record, entry);
        return super.insert(record, entry);
    }

    @Transactional(readOnly = true)
    public  Mono<NewsEntryDto> read(UUID id) {
        log.trace("read({})", id);
        return entryDao.monoById(id)
                .flatMap(entry -> findRecordConvertToDto(entry, id))
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(id)));
    }

    @Transactional
    public  Mono<NewsEntry> update(NewsEntryDto dto) {
        log.trace("update({})", dto);

        NewsEntry entry = NewsEntry.builder()
                .id(UUID.fromString(dto.getId()))
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .content(dto.getContent())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible())
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        Record record = Record.builder()
                .id(UUID.fromString(dto.getId()))
                .position(Integer.MAX_VALUE / 2)
                .type(NewsEntry.class.getSimpleName())
                .publicTime(dto.getPublicTime() != null ? dto.getPublicTime() : LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible())
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        return this.update(record, entry);
    }

    @Transactional
    public  Mono<NewsEntry> update(Record record, NewsEntry entry) {
        log.trace("update({}, {})", record, entry);
        return recordDao.save(record).flatMap(r -> entryDao.save(entry));
    }

    @Override
    protected RecordDao getRecordDao() {
        return recordDao;
    }

    @Override
    protected Mono<Integer> insertEntry(NewsEntry entry) {
        return entryDao.insert(entry);
    }


    private Mono<NewsEntryDto> findRecordConvertToDto(NewsEntry newsEntry, UUID id) {
        return taggetRecordViewDao.monoById(id)
                .map(record -> buildDto(newsEntry, record));
    }

    private NewsEntryDto buildDto(NewsEntry dto, TaggetRecordView record) {
        log.trace("buildNewsEntryDto({}, {})", dto, record);
        return NewsEntryDto.builder()
                .id(record.getId().toString())
                .newsGroupId(dto.getNewsGroupId().toString())
                .title(dto.getTitle())
                .content(dto.getContent())
                .tags(CollectionUtil.getTags(record.getTags()))
                .publicTime(record.getPublicTime())
                .visible(dto.getVisible())
                .build();
    }

}
