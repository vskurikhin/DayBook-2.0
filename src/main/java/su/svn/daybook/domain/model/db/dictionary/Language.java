/*
 * This file was last modified at 2020.09.05 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Language.java
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
import su.svn.daybook.domain.model.DBEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("dictionary.language")
public class Language implements Serializable, DBEntry<Long> {
    static final long serialVersionUID = -150L;

    @Id
    @Getter
    @Setter
    @Column("language_id")
    private Long id;

    @Getter
    @Setter
    @Size(max = 256)
    @Column("language")
    private String language;

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
        Language language1 = (Language) o;
        return Objects.equals(id, language1.id) &&
                Objects.equals(language, language1.language) &&
                Objects.equals(userName, language1.userName) &&
                Objects.equals(createTime, language1.createTime) &&
                Objects.equals(updateTime, language1.updateTime) &&
                Objects.equals(enabled, language1.enabled) &&
                Objects.equals(visible, language1.visible) &&
                Objects.equals(flags, language1.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, language, userName, createTime, updateTime, enabled, visible, flags);
    }
}
