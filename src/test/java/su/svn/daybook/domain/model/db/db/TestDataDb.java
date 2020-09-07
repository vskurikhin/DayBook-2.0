package su.svn.daybook.domain.model.db.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import su.svn.daybook.domain.model.db.security.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class TestDataDb {

    public static final UUID UUID_1 = new UUID(0L, 1L);

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}