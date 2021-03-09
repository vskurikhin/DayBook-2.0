/*
 * This file was last modified at 2021.03.09 22:38 by Victor N. Skurikhin.
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
import su.svn.daybook.domain.dao.db.db.TaggetRecordViewDao;
import su.svn.daybook.domain.model.ArticleDto;
import su.svn.daybook.domain.model.db.db.Article;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.domain.model.db.db.TaggetRecordView;
import su.svn.daybook.exceptions.NameRequiredException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ArticleService extends AbstractRecordService<Article> {

    private final ArticleDao entryDao;

    private final RecordDao recordDao;

    private final TaggetRecordViewDao taggetRecordViewDao;

    public ArticleService(ArticleDao articleDao, RecordDao recordDao, TaggetRecordViewDao taggetRecordViewDao) {
        this.entryDao = articleDao;
        this.recordDao = recordDao;
        this.taggetRecordViewDao = taggetRecordViewDao;
    }

    @Transactional
    public Mono<Article> create(ArticleDto dto) {
        log.trace("create({})", dto);
        Article entry = Article.builder()
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .anchor(dto.getAnchor())
                .include(dto.getInclude())
                .summary(dto.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible() != null ? dto.getVisible() : true)
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        Record record = Record.builder()
                .position(Integer.MAX_VALUE / 2)
                .type(Article.class.getSimpleName())
                .createTime(LocalDateTime.now())
                .publicTime(dto.getPublicTime() != null ? dto.getPublicTime() : LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible() != null ? dto.getVisible() : true)
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        record.setId(UUID.randomUUID());
        entry.setId(record.getId());

        return this.create(record, entry);
    }

    @Transactional
    public Mono<Article> create(Record record, Article entry) {
        log.trace("create({}, {})", record, entry);
        return super.insert(record, entry);
    }

    @Transactional(readOnly = true)
    public Mono<ArticleDto> read(UUID id) {
        log.trace("read({})", id);
        return entryDao.monoById(id)
                .flatMap(entry -> findRecordConvertToDto(entry, id))
                .switchIfEmpty(Mono.error(NameRequiredException.notFoundException(id)));
    }

    @Transactional
    public Mono<Article> update(ArticleDto dto) {
        log.trace("update({})", dto);

        Article entry = Article.builder()
                .id(UUID.fromString(dto.getId()))
                .newsGroupId(UUID.fromString(dto.getNewsGroupId()))
                .title(dto.getTitle())
                .anchor(dto.getAnchor())
                .include(dto.getInclude())
                .summary(dto.getSummary())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible())
                .build();
        setUserName(SecurityContextHolder.getContext(), entry);

        Record record = Record.builder()
                .id(UUID.fromString(dto.getId()))
                .position(Integer.MAX_VALUE / 2)
                .type(Article.class.getSimpleName())
                .publicTime(dto.getPublicTime() != null ? dto.getPublicTime() : LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(dto.getVisible())
                .build();
        setUserName(SecurityContextHolder.getContext(), record);

        return this.update(record, entry);
    }

    @Transactional
    public Mono<Article> update(Record record, Article entry) {
        log.trace("update({}, {})", record, entry);
        return recordDao.save(record).flatMap(r -> entryDao.save(entry));
    }

    @Override
    protected RecordDao getRecordDao() {
        return recordDao;
    }

    @Override
    protected Mono<Integer> insertEntry(Article entry) {
        return entryDao.insert(entry);
    }

    private Mono<ArticleDto> findRecordConvertToDto(Article newsEntry, UUID id) {
        return taggetRecordViewDao.monoById(id)
                .map(record -> buildDto(newsEntry, record));
    }

    private ArticleDto buildDto(Article entry, TaggetRecordView record) {
        return ArticleDto.builder()
                .id(record.getId().toString())
                .newsGroupId(entry.getNewsGroupId().toString())
                .title(entry.getTitle())
                .anchor(entry.getAnchor())
                .include(entry.getInclude())
                .summary(entry.getSummary())
                .tags(CollectionUtil.getTags(record.getTags()))
                .publicTime(record.getPublicTime())
                .visible(entry.getVisible())
                .build();
    }
}
