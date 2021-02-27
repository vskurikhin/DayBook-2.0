/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db.custom;

import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.ArticleCustomizedDao;
import su.svn.daybook.domain.model.db.db.Article;

public class ArticleDaoImpl implements ArticleCustomizedDao {

    private final DatabaseClient client;

    public ArticleDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    public static final String INSERT_NEWS_ENTRY = "INSERT INTO db.article " +
            " (id, user_name, title, news_group_id, include, anchor, summary, create_time, update_time, enabled," +
            " visible, flags) VALUES " +
            " (:id, :userName, :title, :newsGroupId, :include, :anchor, :summary, :createTime, :updateTime, :enabled, " +
            "  :visible, :flags)";

    @Override
    public Mono<Integer> insert(Article entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_NEWS_ENTRY)
                .bind("id", entry.getId())
                .bind("userName", entry.getUserName())
                .bind("title", entry.getTitle());

        execSpec = GenericExecuteSpec.setUuid(execSpec, entry::getNewsGroupId, "newsGroupId");
        execSpec = GenericExecuteSpec.setString(execSpec, entry::getInclude, "include");
        execSpec = GenericExecuteSpec.setString(execSpec, entry::getAnchor, "anchor");
        execSpec = GenericExecuteSpec.setString(execSpec, entry::getSummary, "summary");

        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

        return execSpec.fetch().rowsUpdated();
    }
}
