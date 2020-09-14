/*
 * This file was last modified at 2020.09.14 19:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Codifier.java
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
import su.svn.daybook.domain.model.DBLongEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("dictionary.codifier")
public class Codifier implements Serializable, DBLongEntry {
    static final long serialVersionUID = -110L;

    @Id
    @Getter
    @Setter
    @Column("codifier_id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("code")
    private String code;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("value")
    private String value;

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
        Codifier codifier = (Codifier) o;
        return Objects.equals(id, codifier.id) &&
                Objects.equals(code, codifier.code) &&
                Objects.equals(value, codifier.value) &&
                Objects.equals(userName, codifier.userName) &&
                Objects.equals(createTime, codifier.createTime) &&
                Objects.equals(updateTime, codifier.updateTime) &&
                Objects.equals(enabled, codifier.enabled) &&
                Objects.equals(visible, codifier.visible) &&
                Objects.equals(flags, codifier.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, value, userName, createTime, updateTime, enabled, visible, flags);
    }
}
