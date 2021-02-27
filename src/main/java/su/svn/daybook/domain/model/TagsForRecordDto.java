/*
 * This file was last modified at 2021.02.27 22:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagsForRecordDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import su.svn.daybook.domain.model.db.db.Record;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TagsForRecordDto implements Serializable {

    private static final long serialVersionUID = -110454584419077149L;

    @Getter
    @Setter
    private Record record;

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
                "record=" + record +
                ", tags=" + tags +
                ", userName='" + userName + '\'' +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TagsForRecordDto)) return false;
        final TagsForRecordDto other = (TagsForRecordDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$record = this.record;
        final Object other$record = other.record;
        if (this$record == null ? other$record != null : !this$record.equals(other$record)) return false;
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
        final Object $record = this.record;
        result = result * PRIME + ($record == null ? 43 : $record.hashCode());
        final Object $tags = this.tags;
        result = result * PRIME + ($tags == null ? 43 : $tags.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        return result;
    }
}
