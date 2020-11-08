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
@Table("db.news_entry")
public class NewsEntry implements Serializable, DBUuidEntry {
    static final long serialVersionUID = -220L;

    @Id
    @Getter
    @Setter
    @Column("news_entry_id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("user_name")
    private String userName;

    @Getter
    @Setter
    @Column("news_group_id")
    private UUID newsGroupId;

    @Getter
    @Setter
    @NotNull
    @Size(max = 256)
    @Column("title")
    private String title;

    @Getter
    @Setter
    @Size(max = 10485760)
    @Column("content")
    private String content;

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
        if (!(o instanceof NewsEntry)) return false;
        NewsEntry newsEntry = (NewsEntry) o;
        return Objects.equals(id, newsEntry.id) &&
                Objects.equals(userName, newsEntry.userName) &&
                Objects.equals(newsGroupId, newsEntry.newsGroupId) &&
                Objects.equals(title, newsEntry.title) &&
                Objects.equals(content, newsEntry.content) &&
                Objects.equals(createTime, newsEntry.createTime) &&
                Objects.equals(updateTime, newsEntry.updateTime) &&
                Objects.equals(enabled, newsEntry.enabled) &&
                Objects.equals(visible, newsEntry.visible) &&
                Objects.equals(flags, newsEntry.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, newsGroupId, title, content, createTime, updateTime, enabled, visible, flags);
    }
}
