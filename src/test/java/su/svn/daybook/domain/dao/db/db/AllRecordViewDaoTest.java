package su.svn.daybook.domain.dao.db.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Hooks;
import su.svn.daybook.domain.dao.db.TestConnectionFactoryConfiguration;
import su.svn.daybook.domain.model.db.db.AllRecordView;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static su.svn.daybook.domain.dao.db.db.DbDaoTest.*;
import static su.svn.daybook.domain.model.db.db.TestDataDb.UUID_1;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
class AllRecordViewDaoTest {

    static final Class<?> tClass = AllRecordViewDaoTest.class;

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH_PLUS = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN.plus(13, ChronoUnit.HOURS));

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    RecordDao recordDao;

    @Autowired
    AllRecordViewDao allRecordViewDao;

    DatabaseClient databaseClient;

    TransactionalOperator transactionalOperator;

    final AllRecordView ALL_RECORD_VIEW_1 = AllRecordView.builder()
            .id(UUID_1)
            .position(1)
            .type("NewsEntry")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .tags(new String[]{"label1", "label2"})
            .newsEntryNewsGroupId(UUID_1)
            .newsEntryTitle("title1")
            .newsEntryContent("content1")
            .newsEntryUserName("userName1")
            .newsEntryFlags(0)
            .build();

    final AllRecordView ALL_RECORD_VIEW_2 = AllRecordView.builder()
            .id(UUID_2)
            .position(2)
            .type("NewsLinks")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .enabled(true)
            .visible(true)
            .flags(0)
            .tags(new String[]{"label2", "label3"})
            .newsLinksNewsGroupId(UUID_1)
            .newsLinksTitle("title1")
            .newsLinksUserName("userName1")
            .newsLinksFlags(0)
            .links(new String[]{"link1 ±± description1 ±± details1"})
            .build();

    final AllRecordView ALL_RECORD_VIEW_3 = AllRecordView.builder()
            .id(UUID_3)
            .position(13)
            .type("Article")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH_PLUS)
            .enabled(true)
            .visible(true)
            .flags(0)
            .tags(new String[]{"label1", "label3"})
            .articleNewsGroupId(UUID_1)
            .articleTitle("title1")
            .articleInclude("include1")
            .articleAnchor("anchor1")
            .articleSummary("summary1")
            .articleUserName("userName1")
            .articleFlags(0)
            .build();

    @BeforeEach
    void setUp() {
        databaseClient = DatabaseClient.create(connectionFactory);
        transactionalOperator = TransactionalOperator.create(new R2dbcTransactionManager(connectionFactory));
        TestDatabaseUtil.constructTestDatabase(databaseClient, transactionalOperator, tClass);
    }

    @AfterEach
    void tearDown() {
        transactionalOperator = TransactionalOperator.create(new R2dbcTransactionManager(connectionFactory));
        TestDatabaseUtil.dropTestDatabase(databaseClient, transactionalOperator, tClass);
    }

    @Test
    void executesFluxAll() throws IOException {
        Hooks.onOperatorDebug();
        List<AllRecordView> list = allRecordViewDao.fluxAll().collectList().block();
        assert list != null;
        Assertions.assertFalse(list.isEmpty());
        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.contains(ALL_RECORD_VIEW_1));
        Assertions.assertTrue(list.contains(ALL_RECORD_VIEW_2));
        Assertions.assertTrue(list.contains(ALL_RECORD_VIEW_3));
    }

    @Test
    void executesFluxAllPageRequest() throws IOException {
        Hooks.onOperatorDebug();
        Pageable pageable0 = PageRequest.of(0, 1);
        List<AllRecordView> list0 = allRecordViewDao.findAllByEnabledIsTrueOrderByUpdateTimeDescPositionAsc(pageable0)
                .collectList()
                .block();
        assert list0 != null;
        Assertions.assertFalse(list0.isEmpty());
        Assertions.assertEquals(1, list0.size());
        Assertions.assertTrue(list0.contains(ALL_RECORD_VIEW_3));

        Pageable pageable1 = PageRequest.of(1, 1, Sort.by(Sort.Order.desc("updateTime"), Sort.Order.by("position")));
        List<AllRecordView> list1 = allRecordViewDao.findAllByEnabledIsTrue(pageable1)
                .collectList()
                .block();
        assert list1 != null;
        Assertions.assertFalse(list1.isEmpty());
        Assertions.assertEquals(1, list1.size());
        Assertions.assertTrue(list1.contains(ALL_RECORD_VIEW_1));


        Pageable pageable2 = PageRequest.of(2, 1);
        List<AllRecordView> list2 = allRecordViewDao.findAllByEnabledIsTrueOrderByUpdateTimeDescPositionAsc(pageable2)
                .collectList()
                .block();
        assert list2 != null;
        Assertions.assertFalse(list2.isEmpty());
        Assertions.assertEquals(1, list2.size());
        Assertions.assertTrue(list2.contains(ALL_RECORD_VIEW_2));
    }
}