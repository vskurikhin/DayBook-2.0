/*
 * This file was last modified at 2020.09.06 12:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Role.java
 * $Id$
 */

package su.svn.daybook.domain.model.db.db;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import su.svn.daybook.domain.model.DBEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("db.role")
public class Role implements Serializable, DBEntry<UUID> {
    static final long serialVersionUID = -200L;

    @Id
    @Getter
    @Setter
    @Column("role_id")
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Size(max = 32)
    @Column("role_name")
    private String roleName;

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
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(roleName, role.roleName) &&
                Objects.equals(userName, role.userName) &&
                Objects.equals(createTime, role.createTime) &&
                Objects.equals(updateTime, role.updateTime) &&
                Objects.equals(isDisabled, role.isDisabled) &&
                Objects.equals(visible, role.visible) &&
                Objects.equals(flags, role.flags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName, userName, createTime, updateTime, isDisabled, visible, flags);
    }
}
