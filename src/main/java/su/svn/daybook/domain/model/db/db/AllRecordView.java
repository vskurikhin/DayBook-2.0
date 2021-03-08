/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordView.java
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
@Table("db.all_record_view")
public class AllRecordView implements Serializable, DBUuidEntry {

    private static final long serialVersionUID = 8687481988373035949L;

    @Id
    @Getter
    @Setter
    @Schema(description = "record id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Schema(description = "record position", example = "1")
    @Column("position")
    private int position;

    @Getter
    @Setter
    @Size(max = 256)
    @Schema(description = "type", example = "string")
    @Column("type")
    private String type;

    @Getter
    @NotNull
    @Schema(description = "public time", example = "1970-01-01T00:00:00")
    @Column("public_time")
    private LocalDateTime publicTime;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "record user name", example = "login")
    @Column("record_user_name")
    private String recordUserName;

    @Getter
    @Setter
    @Column("record_visible")
    private Boolean recordVisible;

    @Getter
    @Setter
    @Column("tags")
    private String[] tags;

    @Getter
    @Setter
    @Schema(description = "news group id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("news_group_id")
    private UUID newsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "title", example = "string")
    @Column("title")
    private String title;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    @Column("user_name")
    private String userName;

    @Getter
    @Setter
    @Column("visible")
    private Boolean visible;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "article include", example = "string")
    @Column("article_include")
    private String articleInclude;

    @Getter
    @Setter
    @NotNull
    @Size(max = 128)
    @Schema(description = "article anchor", example = "string")
    @Column("article_anchor")
    private String articleAnchor;

    @Getter
    @Setter
    @NotNull
    @Size(max = 10485760)
    @Schema(description = "article summary", example = "string")
    @Column("article_summary")
    private String articleSummary;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Schema(description = "news entry content", example = "string")
    @Column("news_entry_content")
    private String newsEntryContent;

    @Getter
    @Setter
    @Column("links")
    private String[] links;

    @Override
    public Class<? extends DBUuidEntry> getEClass() {
        return AllRecordView.class;
    }

    @Override
    public String toString() {
        return "AllRecordView{" +
                "id=" + id +
                ", position=" + position +
                ", type='" + type + '\'' +
                ", publicTime=" + publicTime +
                ", recordUserName='" + recordUserName + '\'' +
                ", recordVisible=" + recordVisible +
                ", tags=" + Arrays.toString(tags) +
                ", newsGroupId=" + newsGroupId +
                ", title='" + title + '\'' +
                ", userName='" + userName + '\'' +
                ", visible=" + visible +
                ", articleInclude='" + articleInclude + '\'' +
                ", articleAnchor='" + articleAnchor + '\'' +
                ", articleSummary='" + articleSummary + '\'' +
                ", newsEntryContent='" + newsEntryContent + '\'' +
                ", links=" + Arrays.toString(links) +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AllRecordView)) return false;
        final AllRecordView other = (AllRecordView) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        if (this.position != other.position) return false;
        final Object this$type = this.type;
        final Object other$type = other.type;
        if (this$type == null ? other$type != null : !this$type.equals(other$type)) return false;
        final Object this$publicTime = this.publicTime;
        final Object other$publicTime = other.publicTime;
        if (this$publicTime == null ? other$publicTime != null : !this$publicTime.equals(other$publicTime))
            return false;
        final Object this$recordUserName = this.recordUserName;
        final Object other$recordUserName = other.recordUserName;
        if (this$recordUserName == null ? other$recordUserName != null : !this$recordUserName.equals(other$recordUserName))
            return false;
        final Object this$recordVisible = this.recordVisible;
        final Object other$recordVisible = other.recordVisible;
        if (this$recordVisible == null ? other$recordVisible != null : !this$recordVisible.equals(other$recordVisible))
            return false;
        if (!Arrays.deepEquals(this.tags, other.tags)) return false;
        final Object this$newsGroupId = this.newsGroupId;
        final Object other$newsGroupId = other.newsGroupId;
        if (this$newsGroupId == null ? other$newsGroupId != null : !this$newsGroupId.equals(other$newsGroupId))
            return false;
        final Object this$title = this.title;
        final Object other$title = other.title;
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        final Object this$articleInclude = this.articleInclude;
        final Object other$articleInclude = other.articleInclude;
        if (this$articleInclude == null ? other$articleInclude != null : !this$articleInclude.equals(other$articleInclude))
            return false;
        final Object this$articleAnchor = this.articleAnchor;
        final Object other$articleAnchor = other.articleAnchor;
        if (this$articleAnchor == null ? other$articleAnchor != null : !this$articleAnchor.equals(other$articleAnchor))
            return false;
        final Object this$articleSummary = this.articleSummary;
        final Object other$articleSummary = other.articleSummary;
        if (this$articleSummary == null ? other$articleSummary != null : !this$articleSummary.equals(other$articleSummary))
            return false;
        final Object this$newsEntryContent = this.newsEntryContent;
        final Object other$newsEntryContent = other.newsEntryContent;
        if (this$newsEntryContent == null ? other$newsEntryContent != null : !this$newsEntryContent.equals(other$newsEntryContent))
            return false;
        if (!Arrays.deepEquals(this.links, other.links)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AllRecordView;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        result = result * PRIME + this.position;
        final Object $type = this.type;
        result = result * PRIME + ($type == null ? 43 : $type.hashCode());
        final Object $publicTime = this.publicTime;
        result = result * PRIME + ($publicTime == null ? 43 : $publicTime.hashCode());
        final Object $recordUserName = this.recordUserName;
        result = result * PRIME + ($recordUserName == null ? 43 : $recordUserName.hashCode());
        final Object $recordVisible = this.recordVisible;
        result = result * PRIME + ($recordVisible == null ? 43 : $recordVisible.hashCode());
        result = result * PRIME + Arrays.deepHashCode(this.tags);
        final Object $newsGroupId = this.newsGroupId;
        result = result * PRIME + ($newsGroupId == null ? 43 : $newsGroupId.hashCode());
        final Object $title = this.title;
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        final Object $articleInclude = this.articleInclude;
        result = result * PRIME + ($articleInclude == null ? 43 : $articleInclude.hashCode());
        final Object $articleAnchor = this.articleAnchor;
        result = result * PRIME + ($articleAnchor == null ? 43 : $articleAnchor.hashCode());
        final Object $articleSummary = this.articleSummary;
        result = result * PRIME + ($articleSummary == null ? 43 : $articleSummary.hashCode());
        final Object $newsEntryContent = this.newsEntryContent;
        result = result * PRIME + ($newsEntryContent == null ? 43 : $newsEntryContent.hashCode());
        result = result * PRIME + Arrays.deepHashCode(this.links);
        return result;
    }
}
