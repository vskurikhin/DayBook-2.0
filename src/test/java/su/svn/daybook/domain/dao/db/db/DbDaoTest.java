package su.svn.daybook.domain.dao.db.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.*;
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
import su.svn.daybook.domain.model.db.db.*;
import su.svn.daybook.utils.SerializeUtil;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

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

    @Autowired
    NewsLinksDao newsLinksDao;

    @Autowired
    LinkDao linkDao;

    @Autowired
    LinkDescriptionDao linkDescriptionDao;

    @Autowired
    ArticleDao articleDao;

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
                .position(2)
                .type("type2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final Record RECORD_3 = Record.builder()
                .position(3)
                .type("type3")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void insert() {
            Hooks.onOperatorDebug();
            Record record2 = SerializeUtil.clone(RECORD_2);
            assert record2 != null;
            record2.setId(UUID.randomUUID());
            Integer test = recordDao.insert(record2).block();
            Assertions.assertEquals(1, test);
        }

        @Test
        void transactionalInsertAll() {
            Hooks.onOperatorDebug();
            Record record2 = SerializeUtil.clone(RECORD_2);
            assert record2 != null;
            record2.setId(UUID.randomUUID());
            Record record3 = SerializeUtil.clone(RECORD_3);
            assert record3 != null;
            record3.setId(UUID.randomUUID());
            recordDao.transactionalInsertAll(Arrays.asList(record2, record3))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();
            List<Record> list = recordDao.fluxAll().collectList().block();
            assert list != null;
            Assertions.assertTrue(list.contains(record2));
            Assertions.assertTrue(list.contains(record3));
        }

        @Test
        void transactionalInsert() {
            Hooks.onOperatorDebug();
            Record record2 = SerializeUtil.clone(RECORD_2);
            assert record2 != null;
            record2.setId(UUID.randomUUID());
            recordDao.transactionalInsert(record2)
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();
            List<Record> list = recordDao.fluxAll().collectList().block();
            System.err.println("list = " + list);
            assert list != null;
            Assertions.assertTrue(list.contains(record2));
        }

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
                    .assertNext(NEWS_GROUP_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            List<Record> list = recordDao.fluxAll().collectList().block();
            assert list != null;
            Assertions.assertTrue(list.contains(RECORD_1));
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            recordDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(NEWS_GROUP_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class NewsGroupDaoTest {
        final NewsGroup NEWS_GROUP_2 = NewsGroup.builder()
                .groupName("groupName2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final NewsGroup NEWS_GROUP_3 = NewsGroup.builder()
                .groupName("groupName3")
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
                .id(UUID_2)
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

        final NewsEntry NEWS_ENTRY_3 = NewsEntry.builder()
                .id(UUID_3)
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


        @Test
        void transactionalInsertAll() {
            Hooks.onOperatorDebug();
            newsEntryDao.transactionalInsertAll(Arrays.asList(NEWS_ENTRY_2, NEWS_ENTRY_3))
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();
            List<NewsEntry> list = newsEntryDao.fluxAll().collectList().block();
            assert list != null;
            Assertions.assertTrue(list.contains(NEWS_ENTRY_2));
            Assertions.assertTrue(list.contains(NEWS_ENTRY_3));
        }

        @Test
        void transactionalInsert() {
            Hooks.onOperatorDebug();
            newsEntryDao.transactionalInsert(NEWS_ENTRY_2)
                    .as(StepVerifier::create)
                    .expectNextCount(1)
                    .verifyComplete();
            List<NewsEntry> list = newsEntryDao.fluxAll().collectList().block();
            assert list != null;
            Assertions.assertTrue(list.contains(NEWS_ENTRY_2));
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

    @Nested
    class NewsLinksDaoTest {

        final NewsLinks NEWS_ENTRY_2 = NewsLinks.builder()
                .id(UUID_2)
                .userName("userName1")
                .newsGroupId(UUID_1)
                .title("title1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final NewsLinks NEWS_ENTRY_3 = NewsLinks.builder()
                .id(UUID_3)
                .userName("userName1")
                .newsGroupId(UUID_1)
                .title("title1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(NEWS_LINKS_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(NEWS_LINKS_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(NEWS_LINKS_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class LinkDaoTest {

        final Link LINK_2 = Link.builder()
                .id(UUID_2)
                .link("link2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final Link LINK_3 = Link.builder()
                .id(UUID_3)
                .link("link3")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(LINK_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(LINK_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            newsLinksDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(LINK_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class LinkDescriptionDaoTest {

        final LinkDescription LINK_DESCRIPTION_2 = LinkDescription.builder()
                .id(UUID_2)
                .newsLinksId(UUID_1)
                .linkId(UUID_1)
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final LinkDescription LINK_DESCRIPTION_3 = LinkDescription.builder()
                .id(UUID_3)
                .newsLinksId(UUID_1)
                .linkId(UUID_1)
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            linkDescriptionDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(LINK_DESCRIPTION_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            linkDescriptionDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(LINK_DESCRIPTION_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            linkDescriptionDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(LINK_DESCRIPTION_1::equals)
                    .verifyComplete();
        }
    }

    @Nested
    class ArticleDaoTest {

        final Article ARTICLE_2 = Article.builder()
                .id(UUID_2)
                .title("title1")
                .include("include1")
                .anchor("anchor1")
                .summary("summary1")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final Article ARTICLE_3 = Article.builder()
                .id(UUID_3)
                .title("title1")
                .include("include1")
                .anchor("anchor1")
                .summary("summary1")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesMonoById() throws IOException {
            Hooks.onOperatorDebug();
            articleDao.monoById(UUID_1)
                    .as(StepVerifier::create)
                    .assertNext(ARTICLE_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAll() throws IOException {
            Hooks.onOperatorDebug();
            articleDao.fluxAll()
                    .as(StepVerifier::create)
                    .assertNext(ARTICLE_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFluxAllById() throws IOException {
            Hooks.onOperatorDebug();
            articleDao.fluxAllById(new ArrayList<>() {{ add(UUID_1); }})
                    .as(StepVerifier::create)
                    .assertNext(ARTICLE_1::equals)
                    .verifyComplete();
        }
    }
}
