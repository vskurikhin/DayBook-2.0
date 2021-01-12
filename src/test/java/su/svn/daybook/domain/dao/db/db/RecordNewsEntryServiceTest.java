package su.svn.daybook.domain.dao.db.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import su.svn.daybook.domain.dao.db.TestConnectionFactoryConfiguration;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
class RecordNewsEntryServiceTest {

    static final Class<?> tClass = RecordNewsEntryServiceTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    RecordNewsEntryService recordNewsEntryService;

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

    @Test
    void insertNewsEntry() {
        Record record = Record.builder()
                .position(13)
                .type("type13")
                .userName("userName1")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();
        NewsEntry newsEntry = NewsEntry.builder()
                .userName("userName1")
                .newsGroupId(new UUID(0L, 1L))
                .title("title13")
                .content("content13")
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();
        NewsEntry test = recordNewsEntryService.insertNewsEntry(record, newsEntry).block();
        Assertions.assertEquals(newsEntry, test);
    }
}