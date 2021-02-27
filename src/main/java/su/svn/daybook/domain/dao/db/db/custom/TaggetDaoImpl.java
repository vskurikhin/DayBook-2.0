/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.db.custom;

import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.TaggetCustomizedDao;
import su.svn.daybook.domain.model.db.db.Tagget;

public class TaggetDaoImpl implements TaggetCustomizedDao {

    private final DatabaseClient client;

    public TaggetDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    public static final String INSERT_ENTRY = "INSERT INTO db.tagget " +
            " (id, record_id, tag_label_id, user_name,  create_time, update_time, enabled, visible, flags) VALUES " +
            " (:id, :recordId, :tagLabelId, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(Tagget entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_ENTRY)
                .bind("id", entry.getId())
                .bind("recordId", entry.getRecordId())
                .bind("tagLabelId", entry.getTagLabelId())
                .bind("userName", entry.getUserName());

        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

        return execSpec.fetch().rowsUpdated();
    }
}
