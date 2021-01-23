/*
 * This file was last modified at 2021.01.23 16:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TaggetRecordView.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBUuidEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.tagget_record_view")
public class TaggetRecordView implements Serializable, DBUuidEntry {

    private static final long serialVersionUID = -5003684148204624504L;

    @Id
    @Getter
    @Setter
    @Column("record_id")
    @Schema(description = "record id", example = "00000000-0000-0000-FFFF-000000101001")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column("record_position")
    private int position;

    @Getter
    @Setter
    @Size(max = 256)
    @Column("record_type")
    private String type;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("record_user_name")
    private String userName;

    @Getter
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Column("record_create_time")
    @Schema(description = "create time", example = "1970-01-01T00:00:00Z")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column("record_update_time")
    private LocalDateTime updateTime;

    @Getter
    @Setter
    @Column("record_enabled")
    private Boolean enabled;

    @Getter
    @Setter
    @Column("record_visible")
    private Boolean visible;

    @Getter
    @Setter
    @Column("record_flags")
    private Integer flags;

    @Getter
    @Setter
    @Column("tags")
    private String[] tags;

    @Override
    public Class<? extends DBUuidEntry> getEClass() {
        return TaggetRecordView.class;
    }

    @Override
    public String toString() {
        return "TaggetRecordView{" +
                "id=" + id +
                ", position=" + position +
                ", type='" + type + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enabled=" + enabled +
                ", visible=" + visible +
                ", flags=" + flags +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
