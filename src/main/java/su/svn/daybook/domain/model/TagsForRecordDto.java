/*
 * This file was last modified at 2021.02.28 23:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagsForRecordDto.java
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
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TagsForRecordDto implements Serializable {

    private static final long serialVersionUID = -110454584419077149L;

    @Getter
    @Setter
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Schema(description = "set of tags")
    private Set<String> tags;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    private String userName;

    @Override
    public String toString() {
        return "TagsForRecordDto{" +
                "id=" + id +
                ", tags=" + tags +
                ", userName='" + userName + '\'' +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TagsForRecordDto)) return false;
        final TagsForRecordDto other = (TagsForRecordDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$tags = this.tags;
        final Object other$tags = other.tags;
        if (this$tags == null ? other$tags != null : !this$tags.equals(other$tags)) return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TagsForRecordDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $tags = this.tags;
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }
}
