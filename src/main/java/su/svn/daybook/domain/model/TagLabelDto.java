/*
 * This file was last modified at 2021.03.07 23:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabelDto.java
 * $Id$
 */

package su.svn.daybook.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
public class TagLabelDto implements Serializable  {

    private static final long serialVersionUID = 3635058631153606999L;

    @Id
    @Getter
    @Setter
    @Size(max = 16)
    @Schema(description = "Tag label id", example = "0fffffffffffffff")
    private String id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 128)
    @Schema(description = "Tag label", example = "label")
    private String label;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Schema(description = "user name", example = "login")
    private String userName;

    @Getter
    @Setter
    private Boolean visible;

    @Override
    public String toString() {
        return "TagLabelDto{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", userName='" + userName + '\'' +
                ", visible=" + visible +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof TagLabelDto)) return false;
        final TagLabelDto other = (TagLabelDto) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$label = this.label;
        final Object other$label = other.label;
        if (this$label == null ? other$label != null : !this$label.equals(other$label)) return false;
        final Object this$userName = this.userName;
        final Object other$userName = other.userName;
        if (this$userName == null ? other$userName != null : !this$userName.equals(other$userName)) return false;
        final Object this$visible = this.visible;
        final Object other$visible = other.visible;
        if (this$visible == null ? other$visible != null : !this$visible.equals(other$visible)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof TagLabelDto;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $label = this.label;
        result = result * PRIME + ($label == null ? 43 : $label.hashCode());
        final Object $userName = this.userName;
        result = result * PRIME + ($userName == null ? 43 : $userName.hashCode());
        final Object $visible = this.visible;
        result = result * PRIME + ($visible == null ? 43 : $visible.hashCode());
        return result;
    }
}
