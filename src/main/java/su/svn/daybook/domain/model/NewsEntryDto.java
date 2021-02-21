/*
 * This file was last modified at 2021.02.21 20:37 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class NewsEntryDto implements Serializable {

    private static final long serialVersionUID = -6563712499695288184L;

    @Getter
    @Setter
    @Size(max = 37)
    @Schema(description = "news entry id", example = "00000000-0000-0000-FFFF-000000101001")
    private String id;

    @Getter
    @Setter
    @Size(max = 37)
    @Schema(description = "news group id", example = "00000000-0000-0000-FFFF-000000101001")
    private String newsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    private String title;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Schema(description = "content", example = "string", required = true)
    private String content;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    private String userName;

    @Getter
    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "create time", example = "1970-01-01T00:00:00Z")
    private LocalDateTime createTime;

    @Getter
    @Setter
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @Schema(description = "update time", example = "1970-01-01T00:00:00Z")
    private LocalDateTime updateTime;

    @Getter
    @Setter
    private Boolean enabled;

    @Getter
    @Setter
    private Boolean visible;

    @Getter
    @Setter
    private Integer flags;

    @Override
    public String toString() {
        return "NewsEntryDto{" +
                "id='" + id + '\'' +
                ", newsGroupId='" + newsGroupId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
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
        if (!(o instanceof NewsEntryDto)) return false;
        final NewsEntryDto other = (NewsEntryDto) o;
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
        final Object this$content = this.content;
        final Object other$content = other.content;
        if (this$content == null ? other$content != null : !this$content.equals(other$content)) return false;
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
        return other instanceof NewsEntryDto;
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
        final Object $content = this.content;
        result = result * PRIME + ($content == null ? 43 : $content.hashCode());
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
