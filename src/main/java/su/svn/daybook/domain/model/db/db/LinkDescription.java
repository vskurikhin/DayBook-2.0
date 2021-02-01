/*
 * This file was last modified at 2021.02.01 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescription.java
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
import su.svn.daybook.domain.model.RecordEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.link_description")
public class LinkDescription implements Serializable, RecordEntry {

    private static final long serialVersionUID = 2179926828824660841L;

    @Id
    @Getter
    @Setter
    @Schema(description = "news entry id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Schema(description = "news link id", example = "00000000-0000-0000-FFFF-000000101001", required = true)
    @Column("news_links_id")
    private UUID newsLinksId;

    @Getter
    @Setter
    @NotNull
    @Schema(description = "link id", example = "00000000-0000-0000-FFFF-000000101001", required = true)
    @Column("link_id")
    private UUID linkId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "description", example = "string", required = true)
    @Column("description")
    private String description;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "details", example = "string")
    @Column("details")
    private String details;

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
    @Schema(description = "create time", example = "1970-01-01T00:00:00Z")
    @Column("create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
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
        return LinkDescription.class;
    }

    @Override
    public String toString() {
        return "LinkDescription{" +
                "id=" + id +
                ", newsLinksId=" + newsLinksId +
                ", linkId=" + linkId +
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
        if (!(o instanceof LinkDescription)) return false;
        final LinkDescription other = (LinkDescription) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$newsLinksId = this.newsLinksId;
        final Object other$newsLinksId = other.newsLinksId;
        if (this$newsLinksId == null ? other$newsLinksId != null : !this$newsLinksId.equals(other$newsLinksId))
            return false;
        final Object this$linkId = this.linkId;
        final Object other$linkId = other.linkId;
        if (this$linkId == null ? other$linkId != null : !this$linkId.equals(other$linkId)) return false;
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
        return other instanceof LinkDescription;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $newsLinksId = this.newsLinksId;
        result = result * PRIME + ($newsLinksId == null ? 43 : $newsLinksId.hashCode());
        final Object $linkId = this.linkId;
        result = result * PRIME + ($linkId == null ? 43 : $linkId.hashCode());
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
