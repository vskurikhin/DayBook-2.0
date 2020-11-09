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
            .position(1)
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
            .userName("userName1")
            .groupName("groupName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .build();

    public static final NewsEntry NEWS_ENTRY_1 = NewsEntry.builder()
            .id(UUID_1)
            .userName("userName1")
            .newsGroupId(UUID_1)
            .title("title1")
            .content("content1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .build();
}