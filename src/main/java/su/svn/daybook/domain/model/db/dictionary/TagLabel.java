/*
 * This file was last modified at 2020.11.15 21:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagLabel.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.dictionary;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBStringEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("dictionary.tag_label")
public class TagLabel implements Serializable, DBStringEntry {
    static final long serialVersionUID = -140L;

    @Id
    @Getter
    @Setter
    @Size(max = 16)
    @Column("tag_label_id")
    private String id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 128)
    @Column("label")
    private String label;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("user_name")
    private String userName;

    @Getter
    @NotNull
    @Column("create_time")
    private LocalDateTime createTime;

    @Getter
    @Setter
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
    public Class<? extends DBStringEntry> getEClass() {
        return TagLabel.class;
    }

    @Override
    public String toString() {
        return "TagLabel{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
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
        if (!(o instanceof TagLabel)) return false;
        final TagLabel other = (TagLabel) o;
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
        return other instanceof TagLabel;
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
