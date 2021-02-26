/*
 * This file was last modified at 2021.02.26 17:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db.custom;

import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.NewsGroupCustomizedDao;
import su.svn.daybook.domain.model.db.db.NewsGroup;

public class NewsGroupDaoImpl implements NewsGroupCustomizedDao {

    private final DatabaseClient client;

    public NewsGroupDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    public static final String INSERT_NEWS_ENTRY = "INSERT INTO db.news_group " +
            " (id, group_name, user_name,  create_time, update_time, enabled, visible, flags) VALUES " +
            " (:id, :groupName, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(NewsGroup entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_NEWS_ENTRY)
                .bind("id", entry.getId())
                .bind("groupName", entry.getGroupName())
                .bind("userName", entry.getUserName());

        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

        return execSpec.fetch().rowsUpdated();
    }
}
