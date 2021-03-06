/*
 * This file was last modified at 2021.03.07 23:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Article.java
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
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBUserOwnedEntry;
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
@Table("db.article")
public class Article implements Serializable, RecordEntry, DBUserOwnedEntry {

    private static final long serialVersionUID = 6273130195279157204L;

    @Id
    @Getter
    @Setter
    @Schema(description = "news entry id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("id")
    private UUID id;

    @Getter
    @Setter
    @Schema(description = "news group id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("news_group_id")
    private UUID newsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "title", example = "string", required = true)
    @Column("title")
    private String title;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "include", example = "string", required = true)
    @Column("include")
    private String include;

    @Getter
    @Setter
    @NotNull
    @Size(max = 128)
    @Schema(description = "anchor", example = "string", required = true)
    @Column("anchor")
    private String anchor;

    @Getter
    @Setter
    @NotNull
    @Size(max = 10485760)
    @Schema(description = "summary", example = "string")
    @Column("summary")
    private String summary;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    @Column("user_name")
    private String userName;

    @Getter
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "create time", example = "1970-01-01T00:00:00Z")
    @Column("create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @NotNull
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
        return Article.class;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", newsGroupId=" + newsGroupId +
                ", title='" + title + '\'' +
                ", include='" + include + '\'' +
                ", anchor='" + anchor + '\'' +
                ", summary='" + summary + '\'' +
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
        if (!(o instanceof Article)) return false;
        final Article other = (Article) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$newsGroupId = this.newsGroupId;
        final Object other$newsGroupId = other.newsGroupId;
        if (this$newsGroupId == null ? other$newsGroupId != null : !this$newsGroupId.equals(other$newsGroupId))
            return false;
        final Object this$title = this.title;
        final Object other$title = other.title;
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$include = this.include;
        final Object other$include = other.include;
        if (this$include == null ? other$include != null : !this$include.equals(other$include)) return false;
        final Object this$anchor = this.anchor;
        final Object other$anchor = other.anchor;
        if (this$anchor == null ? other$anchor != null : !this$anchor.equals(other$anchor)) return false;
        final Object this$summary = this.summary;
        final Object other$summary = other.summary;
        if (this$summary == null ? other$summary != null : !this$summary.equals(other$summary)) return false;
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
        return other instanceof Article;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $newsGroupId = this.newsGroupId;
        result = result * PRIME + ($newsGroupId == null ? 43 : $newsGroupId.hashCode());
        final Object $title = this.title;
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $include = this.include;
        result = result * PRIME + ($include == null ? 43 : $include.hashCode());
        final Object $anchor = this.anchor;
        result = result * PRIME + ($anchor == null ? 43 : $anchor.hashCode());
        final Object $summary = this.summary;
        result = result * PRIME + ($summary == null ? 43 : $summary.hashCode());
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
