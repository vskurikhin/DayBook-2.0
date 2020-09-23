/*
 * This file was last modified at 2020.09.22 16:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroup.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBUuidEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.news_group")
public class NewsGroup implements Serializable, DBUuidEntry {
    static final long serialVersionUID = -210L;

    @Id
    @Getter
    @Setter
    @Column("record_id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("\"group\"")
    private String group;

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
        NewsGroup newsGroup = (NewsGroup) o;
        return Objects.equals(id, newsGroup.id) &&
                Objects.equals(group, newsGroup.group) &&
                Objects.equals(userName, newsGroup.userName) &&
                Objects.equals(createTime, newsGroup.createTime) &&
                Objects.equals(updateTime, newsGroup.updateTime) &&
                Objects.equals(enabled, newsGroup.enabled) &&
                Objects.equals(visible, newsGroup.visible) &&
                Objects.equals(flags, newsGroup.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, userName, createTime, updateTime, enabled, visible, flags);
    }
}
