/*
 * This file was last modified at 2020.09.14 19:19 by Victor N. Skurikhin.
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
import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagLabel tagLabel = (TagLabel) o;
        return Objects.equals(id, tagLabel.id) &&
                Objects.equals(label, tagLabel.label) &&
                Objects.equals(userName, tagLabel.userName) &&
                Objects.equals(createTime, tagLabel.createTime) &&
                Objects.equals(updateTime, tagLabel.updateTime) &&
                Objects.equals(enabled, tagLabel.enabled) &&
                Objects.equals(visible, tagLabel.visible) &&
                Objects.equals(flags, tagLabel.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, userName, createTime, updateTime, enabled, visible, flags);
    }
}
