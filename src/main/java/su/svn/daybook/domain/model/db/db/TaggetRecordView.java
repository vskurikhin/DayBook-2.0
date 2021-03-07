/*
 * This file was last modified at 2021.03.07 23:13 by Victor N. Skurikhin.
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
    @Column("id")
    @Schema(description = "record id", example = "00000000-0000-0000-FFFF-000000101001")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column("position")
    private int position;

    @Getter
    @Setter
    @Size(max = 256)
    @Column("type")
    private String type;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    @Column("user_name")
    private String userName;

    @Getter
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "public time", example = "1970-01-01T00:00:00")
    @Column("public_time")
    private LocalDateTime publicTime;

    @Getter
    @Setter
    @Column("visible")
    private Boolean visible;

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
                ", publicTime=" + publicTime +
                ", visible=" + visible +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TaggetRecordView)) return false;
        final TaggetRecordView other = (TaggetRecordView) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.position != other.position) return false;
        final Object this$type = this.type;
        final Object other$type = other.type;
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$publicTime = this.publicTime;
        final Object other$publicTime = other.publicTime;
        if (this$publicTime == null ? other$publicTime != null : !this$publicTime.equals(other$publicTime))
            return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        if (!Arrays.deepEquals(this.tags, other.tags)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TaggetRecordView;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.position;
        final Object $type = this.type;
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $publicTime = this.publicTime;
        result = result * PRIME + ($publicTime == null ? 43 : $publicTime.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        result = result * PRIME + Arrays.deepHashCode(this.tags);
        return result;
    }
}
