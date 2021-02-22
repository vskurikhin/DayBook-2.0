/*
 * This file was last modified at 2021.02.22 14:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SessionRolesView.java
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
import su.svn.daybook.domain.model.DBStringEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder(builderClassName = "Builder")
@Table("security.session_roles_view")
public class SessionRolesView implements Serializable, DBStringEntry {

    private static final long serialVersionUID = 8231201883245482458L;

    @Id
    @Getter
    @Setter
    @Size(max = 64)
    @Column("user_name")
    private String id;

    @Getter
    @Setter
    @NotNull
    @Column("session_id")
    private UUID sessionId;

    @Getter
    @Setter
    @Column("roles")
    private String[] roles;

    @Override
    public Class<? extends DBStringEntry> getEClass() {
        return null;
    }

    @Override
    public String toString() {
        return "SessionRolesView{" +
                "id='" + id + '\'' +
                ", sessionId=" + sessionId +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SessionRolesView)) return false;
        final SessionRolesView other = (SessionRolesView) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.id;
        final Object other$id = other.id;
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$sessionId = this.sessionId;
        final Object other$sessionId = other.sessionId;
        if (this$sessionId == null ? other$sessionId != null : !this$sessionId.equals(other$sessionId)) return false;
        if (!java.util.Arrays.deepEquals(this.roles, other.roles)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SessionRolesView;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.id;
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $sessionId = this.sessionId;
        result = result * PRIME + ($sessionId == null ? 43 : $sessionId.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.roles);
        return result;
    }
}
