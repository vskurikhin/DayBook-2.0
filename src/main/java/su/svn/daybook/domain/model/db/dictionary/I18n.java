/*
 * This file was last modified at 2020.09.05 17:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * I18n.java
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("dictionary.i18")
public class I18n {
    static final long serialVersionUID = -160L;

    @Id
    @Getter
    @Setter
    @Column("i18n_id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Column("language_id")
    private Long languageId;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("message")
    private String message;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("translation")
    private String translation;

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
        I18n i18n = (I18n) o;
        return Objects.equals(id, i18n.id) &&
                Objects.equals(languageId, i18n.languageId) &&
                Objects.equals(message, i18n.message) &&
                Objects.equals(translation, i18n.translation) &&
                Objects.equals(userName, i18n.userName) &&
                Objects.equals(createTime, i18n.createTime) &&
                Objects.equals(updateTime, i18n.updateTime) &&
                Objects.equals(enabled, i18n.enabled) &&
                Objects.equals(visible, i18n.visible) &&
                Objects.equals(flags, i18n.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, languageId, message, translation, userName, createTime, updateTime, enabled, visible, flags);
    }
}
