/*
 * This file was last modified at 2020.09.02 19:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Words.java
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
@Table("dictionary.word")
public class Word implements Serializable, DBEntry<Long> {
    static final long serialVersionUID = -120L;

    @Id
    @Getter
    @Setter
    @Column("word_id")
    private Long id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Column("word")
    private String word;

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
        Word words = (Word) o;
        return Objects.equals(id, words.id) &&
                Objects.equals(word, words.word) &&
                Objects.equals(userName, words.userName) &&
                Objects.equals(createTime, words.createTime) &&
                Objects.equals(updateTime, words.updateTime) &&
                Objects.equals(enabled, words.enabled) &&
                Objects.equals(visible, words.visible) &&
                Objects.equals(flags, words.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, userName, createTime, updateTime, enabled, visible, flags);
    }
}
