/*
 * This file was last modified at 2020.09.22 18:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntry.java
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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.news_entry")
public class NewsEntry {
    static final long serialVersionUID = -220L;

    @Id
    @Getter
    @Setter
    @Column("record_id")
    private UUID id;

    @Getter
    @Setter
    @Column("news_group_id")
    private UUID newsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 128)
    @Column("title")
    private String title;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("content")
    private String content;

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
        NewsEntry newsEntry = (NewsEntry) o;
        return Objects.equals(id, newsEntry.id) &&
                Objects.equals(newsGroupId, newsEntry.newsGroupId) &&
                Objects.equals(title, newsEntry.title) &&
                Objects.equals(content, newsEntry.content) &&
                Objects.equals(userName, newsEntry.userName) &&
                Objects.equals(createTime, newsEntry.createTime) &&
                Objects.equals(updateTime, newsEntry.updateTime) &&
                Objects.equals(enabled, newsEntry.enabled) &&
                Objects.equals(visible, newsEntry.visible) &&
                Objects.equals(flags, newsEntry.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newsGroupId, title, content, userName, createTime, updateTime, enabled, visible, flags);
    }
}
