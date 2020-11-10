package su.svn.daybook.domain.dao.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.util.function.Tuple2;
import su.svn.daybook.domain.model.db.db.NewsEntry;
import su.svn.daybook.domain.model.db.db.Record;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.time.LocalDateTime;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
class NewsEntryServiceTest {

    static final Class<?> tClass = NewsEntryServiceTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    NewsEntryService newsEntryService;

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
        newsEntryService.insertNewsEntry(record, newsEntry).block();
    }

    @Test
    void insertNewsEntry2() {
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
        Tuple2<Record, NewsEntry> t = newsEntryService.insertNewsEntry2(record, newsEntry).block();
        System.err.println("t.getT1() = " + t.getT1());
        System.err.println("t.getT2() = " + t.getT2());
    }
}