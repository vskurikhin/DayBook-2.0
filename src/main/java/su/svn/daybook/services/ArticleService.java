/*
 * This file was last modified at 2021.02.25 19:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleService.java
 * $Id$
 */

package su.svn.daybook.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.ArticleDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.model.ArticleDto;
import su.svn.daybook.domain.model.db.db.Article;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.exceptions.NameRequiredException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService extends AbstractService<Article> {

    private final ArticleDao entryDao;

    private final RecordDao recordDao;

    public ArticleService(ArticleDao articleDao, RecordDao recordDao) {
        this.entryDao = articleDao;
        this.recordDao = recordDao;
    }

    @Transactional
    public Mono<Article> create(ArticleDto dto) {
        log.trace("create({})", dto);
        Article newsEntry = Article.builder()
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .anchor(dto.getAnchor())
                .include(dto.getInclude())
                .summary(dto.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), newsEntry);

        Record record = Record.builder()
                .position(Integer.MAX_VALUE / 2)
                .type(Article.class.getSimpleName())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        record.setId(UUID.randomUUID());
        newsEntry.setId(record.getId());

        return insert(record, newsEntry);
    }

    @Transactional(readOnly = true)
    public Mono<ArticleDto> read(UUID id) {
        log.trace("read({})", id);
        return entryDao.monoById(id)
                .flatMap(entry -> findRecordConvertToNewsEntry(entry, id))
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(id)));
    }

    @Transactional
    public Mono<Article> update(ArticleDto dto) {
        log.trace("update({})", dto);

        Article newsEntry = Article.builder()
                .id(UUID.fromString(dto.getId()))
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .anchor(dto.getAnchor())
                .include(dto.getInclude())
                .summary(dto.getSummary())
                .createTime(dto.getCreateTime())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), newsEntry);

        Record record = Record.builder()
                .id(UUID.fromString(dto.getId()))
                .position(Integer.MAX_VALUE / 2)
                .type(Article.class.getSimpleName())
                .createTime(dto.getCreateTime())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        return recordDao.save(record).flatMap(r -> entryDao.save(newsEntry));
    }

    @Override
    protected RecordDao getRecordDao() {
        return recordDao;
    }

    @Override
    protected Mono<Integer> insertEntry(Article entry) {
        return entryDao.insert(entry);
    }

    private Mono<ArticleDto> findRecordConvertToNewsEntry(Article newsEntry, UUID id) {
        return recordDao.monoById(id)
                .map(record -> buildNewsEntryDto(newsEntry, record));
    }

    private ArticleDto buildNewsEntryDto(Article entry, Record record) {
        return ArticleDto.builder()
                .id(record.getId().toString())
                .newsGroupId(entry.getNewsGroupId().toString())
                .title(entry.getTitle())
                .title(entry.getTitle())
                .anchor(entry.getAnchor())
                .include(entry.getInclude())
                .summary(entry.getSummary())
                .createTime(entry.getCreateTime())
                .updateTime(entry.getUpdateTime())
                .enabled(entry.getEnabled())
                .visible(entry.getVisible())
                .flags(entry.getFlags())
                .build();
    }
}