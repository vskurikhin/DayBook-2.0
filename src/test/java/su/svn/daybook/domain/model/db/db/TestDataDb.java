package su.svn.daybook.domain.model.db.db;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class TestDataDb {

    public static final UUID UUID_1 = new UUID(0L, 1L);

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    public static final Record RECORD_1 = Record.builder()
            .id(UUID_1)
            .index(1)
            .type("type1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .build();

    public static final NewsGroup NEWS_GROUP_1 = NewsGroup.builder()
            .id(UUID_1)
            .group("group1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .build();
}