/*
 * This file was last modified at 2020.09.02 19:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Vocabulary.java
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
@Table("dictionary.vocabulary")
public class Vocabulary {
    static final long serialVersionUID = -130L;

    @Id
    @Getter
    @Setter
    @Column("vocabulary_id")
    private Long id;

    @Getter
    @Setter
    @Column("word_id")
    private Long wordId;

    @Getter
    @Setter
    @Column("value")
    private String value;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
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
    @Column("is_disabled")
    private Boolean isDisabled;

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
        Vocabulary that = (Vocabulary) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(wordId, that.wordId) &&
                Objects.equals(value, that.value) &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(isDisabled, that.isDisabled) &&
                Objects.equals(visible, that.visible) &&
                Objects.equals(flags, that.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, wordId, value, userName, createTime, updateTime, isDisabled, visible, flags);
    }
}
