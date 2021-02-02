/*
 * This file was last modified at 2021.02.02 19:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AllRecordView.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
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

@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @Column("record_id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Schema(description = "record position", example = "1")
    @Column("record_position")
    private int position;

    @Getter
    @Setter
    @Size(max = 256)
    @Schema(description = "type", example = "string")
    @Column("record_type")
    private String type;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    @Column("record_user_name")
    private String userName;

    @Getter
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "create time", example = "1970-01-01T00:00:00")
    @Column("record_create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "update time", example = "1970-01-01T00:00:00")
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

    @Getter
    @Setter
    @Schema(description = "article news group id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("article_news_group_id")
    private UUID articleNewsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "article title", example = "string")
    @Column("article_title")
    private String articleTitle;

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
    @NotNull
    @Size(max = 64)
    @Schema(description = "article user name", example = "login")
    @Column("article_user_name")
    private String articleUserName;

    @Getter
    @Setter
    @Column("article_flags")
    private Integer articleFlags;

    @Getter
    @Setter
    @Schema(description = "news entry news group id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("news_entry_news_group_id")
    private UUID newsEntryNewsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "news entry title", example = "string")
    @Column("news_entry_title")
    private String newsEntryTitle;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Schema(description = "news entry content", example = "string")
    @Column("news_entry_content")
    private String newsEntryContent;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "news entry user name", example = "login")
    @Column("news_entry_user_name")
    private String newsEntryUserName;

    @Getter
    @Setter
    @Column("news_entry_flags")
    private Integer newsEntryFlags;

    @Getter
    @Setter
    @Schema(description = "news links news group id", example = "00000000-0000-0000-FFFF-000000101001")
    @Column("news_links_news_group_id")
    private UUID newsLinksNewsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Schema(description = "news links title", example = "string")
    @Column("news_links_title")
    private String newsLinksTitle;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "news links user name", example = "login")
    @Column("news_links_user_name")
    private String newsLinksUserName;

    @Getter
    @Setter
    @Column("news_links_flags")
    private Integer newsLinksFlags;

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
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enabled=" + enabled +
                ", visible=" + visible +
                ", flags=" + flags +
                ", tags=" + Arrays.toString(tags) +
                ", articleNewsGroupId=" + articleNewsGroupId +
                ", articleTitle='" + articleTitle + '\'' +
                ", articleInclude='" + articleInclude + '\'' +
                ", articleAnchor='" + articleAnchor + '\'' +
                ", articleSummary='" + articleSummary + '\'' +
                ", articleUserName='" + articleUserName + '\'' +
                ", articleFlags=" + articleFlags +
                ", newsEntryNewsGroupId=" + newsEntryNewsGroupId +
                ", newsEntryTitle='" + newsEntryTitle + '\'' +
                ", newsEntryContent='" + newsEntryContent + '\'' +
                ", newsEntryUserName='" + newsEntryUserName + '\'' +
                ", newsEntryFlags=" + newsEntryFlags +
                ", newsLinksNewsGroupId=" + newsLinksNewsGroupId +
                ", newsLinksTitle='" + newsLinksTitle + '\'' +
                ", newsLinksUserName='" + newsLinksUserName + '\'' +
                ", newsLinksFlags=" + newsLinksFlags +
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
        if (!Arrays.deepEquals(this.tags, other.tags)) return false;
        final Object this$articleNewsGroupId = this.articleNewsGroupId;
        final Object other$articleNewsGroupId = other.articleNewsGroupId;
        if (this$articleNewsGroupId == null ? other$articleNewsGroupId != null : !this$articleNewsGroupId.equals(other$articleNewsGroupId))
            return false;
        final Object this$articleTitle = this.articleTitle;
        final Object other$articleTitle = other.articleTitle;
        if (this$articleTitle == null ? other$articleTitle != null : !this$articleTitle.equals(other$articleTitle))
            return false;
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
        final Object this$articleUserName = this.articleUserName;
        final Object other$articleUserName = other.articleUserName;
        if (this$articleUserName == null ? other$articleUserName != null : !this$articleUserName.equals(other$articleUserName))
            return false;
        final Object this$articleFlags = this.articleFlags;
        final Object other$articleFlags = other.articleFlags;
        if (this$articleFlags == null ? other$articleFlags != null : !this$articleFlags.equals(other$articleFlags))
            return false;
        final Object this$newsEntryNewsGroupId = this.newsEntryNewsGroupId;
        final Object other$newsEntryNewsGroupId = other.newsEntryNewsGroupId;
        if (this$newsEntryNewsGroupId == null ? other$newsEntryNewsGroupId != null : !this$newsEntryNewsGroupId.equals(other$newsEntryNewsGroupId))
            return false;
        final Object this$newsEntryTitle = this.newsEntryTitle;
        final Object other$newsEntryTitle = other.newsEntryTitle;
        if (this$newsEntryTitle == null ? other$newsEntryTitle != null : !this$newsEntryTitle.equals(other$newsEntryTitle))
            return false;
        final Object this$newsEntryContent = this.newsEntryContent;
        final Object other$newsEntryContent = other.newsEntryContent;
        if (this$newsEntryContent == null ? other$newsEntryContent != null : !this$newsEntryContent.equals(other$newsEntryContent))
            return false;
        final Object this$newsEntryUserName = this.newsEntryUserName;
        final Object other$newsEntryUserName = other.newsEntryUserName;
        if (this$newsEntryUserName == null ? other$newsEntryUserName != null : !this$newsEntryUserName.equals(other$newsEntryUserName))
            return false;
        final Object this$newsEntryFlags = this.newsEntryFlags;
        final Object other$newsEntryFlags = other.newsEntryFlags;
        if (this$newsEntryFlags == null ? other$newsEntryFlags != null : !this$newsEntryFlags.equals(other$newsEntryFlags))
            return false;
        final Object this$newsLinksNewsGroupId = this.newsLinksNewsGroupId;
        final Object other$newsLinksNewsGroupId = other.newsLinksNewsGroupId;
        if (this$newsLinksNewsGroupId == null ? other$newsLinksNewsGroupId != null : !this$newsLinksNewsGroupId.equals(other$newsLinksNewsGroupId))
            return false;
        final Object this$newsLinksTitle = this.newsLinksTitle;
        final Object other$newsLinksTitle = other.newsLinksTitle;
        if (this$newsLinksTitle == null ? other$newsLinksTitle != null : !this$newsLinksTitle.equals(other$newsLinksTitle))
            return false;
        final Object this$newsLinksUserName = this.newsLinksUserName;
        final Object other$newsLinksUserName = other.newsLinksUserName;
        if (this$newsLinksUserName == null ? other$newsLinksUserName != null : !this$newsLinksUserName.equals(other$newsLinksUserName))
            return false;
        final Object this$newsLinksFlags = this.newsLinksFlags;
        final Object other$newsLinksFlags = other.newsLinksFlags;
        if (this$newsLinksFlags == null ? other$newsLinksFlags != null : !this$newsLinksFlags.equals(other$newsLinksFlags))
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
        result = result * PRIME + Arrays.deepHashCode(this.tags);
        final Object $articleNewsGroupId = this.articleNewsGroupId;
        result = result * PRIME + ($articleNewsGroupId == null ? 43 : $articleNewsGroupId.hashCode());
        final Object $articleTitle = this.articleTitle;
        result = result * PRIME + ($articleTitle == null ? 43 : $articleTitle.hashCode());
        final Object $articleInclude = this.articleInclude;
        result = result * PRIME + ($articleInclude == null ? 43 : $articleInclude.hashCode());
        final Object $articleAnchor = this.articleAnchor;
        result = result * PRIME + ($articleAnchor == null ? 43 : $articleAnchor.hashCode());
        final Object $articleSummary = this.articleSummary;
        result = result * PRIME + ($articleSummary == null ? 43 : $articleSummary.hashCode());
        final Object $articleUserName = this.articleUserName;
        result = result * PRIME + ($articleUserName == null ? 43 : $articleUserName.hashCode());
        final Object $articleFlags = this.articleFlags;
        result = result * PRIME + ($articleFlags == null ? 43 : $articleFlags.hashCode());
        final Object $newsEntryNewsGroupId = this.newsEntryNewsGroupId;
        result = result * PRIME + ($newsEntryNewsGroupId == null ? 43 : $newsEntryNewsGroupId.hashCode());
        final Object $newsEntryTitle = this.newsEntryTitle;
        result = result * PRIME + ($newsEntryTitle == null ? 43 : $newsEntryTitle.hashCode());
        final Object $newsEntryContent = this.newsEntryContent;
        result = result * PRIME + ($newsEntryContent == null ? 43 : $newsEntryContent.hashCode());
        final Object $newsEntryUserName = this.newsEntryUserName;
        result = result * PRIME + ($newsEntryUserName == null ? 43 : $newsEntryUserName.hashCode());
        final Object $newsEntryFlags = this.newsEntryFlags;
        result = result * PRIME + ($newsEntryFlags == null ? 43 : $newsEntryFlags.hashCode());
        final Object $newsLinksNewsGroupId = this.newsLinksNewsGroupId;
        result = result * PRIME + ($newsLinksNewsGroupId == null ? 43 : $newsLinksNewsGroupId.hashCode());
        final Object $newsLinksTitle = this.newsLinksTitle;
        result = result * PRIME + ($newsLinksTitle == null ? 43 : $newsLinksTitle.hashCode());
        final Object $newsLinksUserName = this.newsLinksUserName;
        result = result * PRIME + ($newsLinksUserName == null ? 43 : $newsLinksUserName.hashCode());
        final Object $newsLinksFlags = this.newsLinksFlags;
        result = result * PRIME + ($newsLinksFlags == null ? 43 : $newsLinksFlags.hashCode());
        result = result * PRIME + Arrays.deepHashCode(this.links);
        return result;
    }
}
