/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

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
import java.util.Set;

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
    @Setter
    @NotNull
    @Schema(description = "public time", example = "1970-01-01T00:00:00")
    private LocalDateTime publicTime;

    @Getter
    @Setter
    private Boolean visible;

    @Getter
    @Setter
    private Set<String> tags;

    @Override
    public String toString() {
        return "NewsEntryDto{" +
                "id='" + id + '\'' +
                ", newsGroupId='" + newsGroupId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userName='" + userName + '\'' +
                ", publicTime=" + publicTime +
                ", visible=" + visible +
                ", tags=" + tags +
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
        final Object this$publicTime = this.publicTime;
        final Object other$publicTime = other.publicTime;
        if (this$publicTime == null ? other$publicTime != null : !this$publicTime.equals(other$publicTime))
            return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        final Object this$tags = this.tags;
        final Object other$tags = other.tags;
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
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
        final Object $publicTime = this.publicTime;
        result = result * PRIME + ($publicTime == null ? 43 : $publicTime.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        final Object $tags = this.tags;
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        return result;
    }
}
