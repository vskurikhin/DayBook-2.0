/*
 * This file was last modified at 2020.09.14 19:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserName.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.security;

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
@Table("security.user_name")
public class UserName implements Serializable, DBUuidEntry {
    static final long serialVersionUID = -1L;

    @Id
    @Getter
    @Setter
    @Column("user_name_id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 64)
    @Column("user_name")
    private String userName;

    @Getter
    @Setter
    @NotNull
    @Size(max = 1024)
    @Column("password")
    private String password;

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
        UserName userName1 = (UserName) o;
        return Objects.equals(id, userName1.id) &&
                Objects.equals(userName, userName1.userName) &&
                Objects.equals(createTime, userName1.createTime) &&
                Objects.equals(updateTime, userName1.updateTime) &&
                Objects.equals(enabled, userName1.enabled) &&
                Objects.equals(visible, userName1.visible) &&
                Objects.equals(flags, userName1.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, createTime, updateTime, enabled, visible, flags);
    }
}
