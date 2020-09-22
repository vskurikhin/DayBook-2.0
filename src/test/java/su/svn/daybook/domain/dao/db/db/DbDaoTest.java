package su.svn.daybook.domain.dao.db.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;
import su.svn.daybook.domain.dao.db.TestConnectionFactoryConfiguration;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.NewsGroup;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import static su.svn.daybook.domain.model.db.db.TestDataDb.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
public class DbDaoTest {

    static final Class<?> tClass = DbDaoTest.class;

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    public static final UUID UUID_2 = new UUID(0L, 2L);

    public static final UUID UUID_3 = new UUID(0L, 3L);

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    RecordDao recordDao;

    @Autowired
    NewsGroupDao newsGroupDao;

    @Autowired
    NewsEntryDao newsEntryDao;

    DatabaseClient databaseClient;

    TransactionalOperator transactionalOperator;

    @BeforeEach
    void setUp() {
        databaseClient = DatabaseClient.create(connectionFactory);
        transactionalOperator = TransactionalOperator.create(new R2dbcTransactionManager(connectionFactory));
        TestDatabaseUtil.constructTestDatabase(databaseClient, transactionalOperator, tClass);
        System.err.println("setUp");
    }

    @AfterEach
    void tearDown() {
        transactionalOperator = TransactionalOperator.create(new R2dbcTransactionManager(connectionFactory));
        TestDatabaseUtil.dropTestDatabase(databaseClient, transactionalOperator, tClass);
        System.err.println("tearDown");
    }

    @Nested
    class RecordDaoTest {

        final Record RECORD_2 = Record.builder()
                .index(2)
                .type("type2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final Record RECORD_3 = Record.builder()
                .index(3)
                .type("type3")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            recordDao.saveAll(Arrays.asList(RECORD_2, RECORD_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            recordDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(RECORD_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            recordDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(RECORD_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            recordDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(RECORD_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class NewsGroupDaoTest {
        final NewsGroup NEWS_GROUP_2 = NewsGroup.builder()
                .group("group2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final NewsGroup NEWS_GROUP_3 = NewsGroup.builder()
                .group("group3")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            newsGroupDao.saveAll(Arrays.asList(NEWS_GROUP_2, NEWS_GROUP_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            newsGroupDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(NEWS_GROUP_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            newsGroupDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(NEWS_GROUP_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            newsGroupDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(NEWS_GROUP_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class NewsEntryDaoTest {

        final NewsEntry NEWS_ENTRY_2 = NewsEntry.builder()
                .newsGroupId(UUID_1)
                .title("title2")
                .content("content2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final NewsEntry NEWS_ENTRY_3 = NewsEntry.builder()
                .newsGroupId(UUID_1)
                .title("title2")
                .content("content2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();
        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            newsEntryDao.saveAll(Arrays.asList(NEWS_ENTRY_2, NEWS_ENTRY_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            newsEntryDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(NEWS_ENTRY_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            newsEntryDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(NEWS_ENTRY_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            newsEntryDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(NEWS_ENTRY_1::equals)
                    .verifyComplete();
        }
    }
}
