/*
 * This file was last modified at 2020.08.27 08:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Role.java
 * $Id$
 */

package su.svn.daybook.domain.security;

import java.util.UUID;

public enum Role {
    ROLE_USER(new UUID(0, 1)),
    ROLE_ADMIN(new UUID(0, 2));

    private final UUID id;

    Role(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public static Role getById(final UUID id) {
        for (Role role : Role.values()) {
            if (id.equals(role.id)) {
                return role;
            }
        }
        return null;
    }

    public static Role valueOfName(final String roleName) {
        try {
            return Role.valueOf(roleName);
        } catch (Exception ignored) {}

        return null;
    }
}
