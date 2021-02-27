/*
 * This file was last modified at 2021.02.27 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelDaoImpl.java
 * $Id$
 */

package su.svn.daybook.domain.dao.db.dictionary.custom;

import org.springframework.data.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Mono;
import su.svn.daybook.domain.dao.db.db.custom.GenericExecuteSpec;
import su.svn.daybook.domain.dao.db.dictionary.TagLabelCustomizedDao;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;

public class TagLabelDaoImpl implements TagLabelCustomizedDao {

    private final DatabaseClient client;

    public TagLabelDaoImpl(DatabaseClient client) {
        this.client = client;
    }

    public static final String INSERT_ENTRY = "INSERT INTO dictionary.tag_label " +
            " (id, label, user_name,  create_time, update_time, enabled, visible, flags) VALUES " +
            " (:id, :label, :userName, :createTime, :updateTime, :enabled, :visible, :flags)";

    @Override
    public Mono<Integer> insert(TagLabel entry) {
        DatabaseClient.GenericExecuteSpec execSpec = client.execute(INSERT_ENTRY)
                .bind("id", entry.getId())
                .bind("label", entry.getLabel())
                .bind("userName", entry.getUserName());

        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getCreateTime, "createTime");
        execSpec = GenericExecuteSpec.setLocalDateTimeNow(execSpec, entry::getUpdateTime, "updateTime");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getVisible, true,"enabled");
        execSpec = GenericExecuteSpec.setBoolean(execSpec, entry::getEnabled, true,"visible");
        execSpec = GenericExecuteSpec.setInteger(execSpec, entry::getFlags, "flags");

        return execSpec.fetch().rowsUpdated();
    }
}
