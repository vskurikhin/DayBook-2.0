package su.svn.daybook.domain.model.db.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class TestDataSecurity {

    public static final UUID UUID_1 = new UUID(0L, 1L);

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    public static final Role ROLE_1 = Role.builder()
            .id(UUID_1)
            .roleName("roleName1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .build();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class RoleTest {

    }
}