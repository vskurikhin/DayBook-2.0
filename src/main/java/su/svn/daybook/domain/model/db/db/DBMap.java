/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBMap.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBUserOwnedEntry;
import su.svn.daybook.domain.model.DBUuidEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.map")
public class DBMap implements Serializable, DBUuidEntry, DBUserOwnedEntry {

    private static final long serialVersionUID = 6269491811865636304L;

    @Id
    @Getter
    @Setter
    @Column("id")
    @Schema(description = "DB map id", example = "00000000-0000-0000-FFFF-000000101001")
    private UUID id;

    @Getter
    @Setter
    @Size(max = 1024)
    @Column("key")
    private String key;

    @Getter
    @Setter
    @Column("value")
    private String value;

    @Getter
    @Setter
    @Column("value_type_id")
    private Long valueTypeId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    @Column("user_name")
    private String userName;

    @Getter
    @NotNull
    @Schema(description = "create time", example = "1970-01-01T00:00:00Z")
    @Column("create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @Schema(description = "update time", example = "1970-01-01T00:00:00Z")
    @Column("update_time")
    private LocalDateTime updateTime;

    @Getter
    @Setter
    @Column("enabled")
    private Boolean enabled;

    @Getter
    @Setter
    @Column("visible")
    private Boolean visible;

    @Getter
    @Setter
    @Column("flags")
    private Integer flags;

    @Override
    public Class<? extends DBUuidEntry> getEClass() {
        return DBMap.class;
    }

    @Override
    public String toString() {
        return "DBMap{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", valueTypeId=" + valueTypeId +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enabled=" + enabled +
                ", visible=" + visible +
                ", flags=" + flags +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DBMap)) return false;
        final DBMap other = (DBMap) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$key = this.key;
        final Object other$key = other.key;
        if (this$key == null ? other$key != null : !this$key.equals(other$key)) return false;
        final Object this$value = this.value;
        final Object other$value = other.value;
        if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
        final Object this$valueTypeId = this.valueTypeId;
        final Object other$valueTypeId = other.valueTypeId;
        if (this$valueTypeId == null ? other$valueTypeId != null : !this$valueTypeId.equals(other$valueTypeId))
            return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$createTime = this.createTime;
        final Object other$createTime = other.createTime;
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$updateTime = this.updateTime;
        final Object other$updateTime = other.updateTime;
        if (this$updateTime == null ? other$updateTime != null : !this$updateTime.equals(other$updateTime))
            return false;
        final Object this$enabled = this.enabled;
        final Object other$enabled = other.enabled;
        if (this$enabled == null ? other$enabled != null : !this$enabled.equals(other$enabled)) return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        final Object this$flags = this.flags;
        final Object other$flags = other.flags;
        if (this$flags == null ? other$flags != null : !this$flags.equals(other$flags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DBMap;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $key = this.key;
        result = result * PRIME + ($key == null ? 43 : $key.hashCode());
        final Object $value = this.value;
        result = result * PRIME + ($value == null ? 43 : $value.hashCode());
        final Object $valueTypeId = this.valueTypeId;
        result = result * PRIME + ($valueTypeId == null ? 43 : $valueTypeId.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $createTime = this.createTime;
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $updateTime = this.updateTime;
        result = result * PRIME + ($updateTime == null ? 43 : $updateTime.hashCode());
        final Object $enabled = this.enabled;
        result = result * PRIME + ($enabled == null ? 43 : $enabled.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        final Object $flags = this.flags;
        result = result * PRIME + ($flags == null ? 43 : $flags.hashCode());
        return result;
    }
}
