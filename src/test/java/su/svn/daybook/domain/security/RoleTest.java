package su.svn.daybook.domain.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static su.svn.daybook.domain.security.Role.ROLE_ADMIN;
import static su.svn.daybook.domain.security.Role.ROLE_USER;

class RoleTest {
    public static final UUID UUID_1 = new UUID(0L, 1L);
    public static final UUID UUID_2 = new UUID(0L, 2L);

    @Test
    void testGetId() {
        Assertions.assertEquals(UUID_1, ROLE_USER.getId());
        Assertions.assertEquals(UUID_2, ROLE_ADMIN.getId());
    }

    @Test
    void testGetById() {
        Assertions.assertEquals(ROLE_USER, Role.getById(UUID_1));
        Assertions.assertEquals(ROLE_ADMIN, Role.getById(UUID_2));
        Assertions.assertNull(Role.getById(UUID.randomUUID()));
    }

    @Test
    void testValueOfName() {
        Assertions.assertEquals(ROLE_USER, Role.valueOfName("ROLE_USER"));
        Assertions.assertEquals(ROLE_ADMIN, Role.valueOfName("ROLE_ADMIN"));
        Assertions.assertNull(Role.valueOfName("NONE"));
    }
}