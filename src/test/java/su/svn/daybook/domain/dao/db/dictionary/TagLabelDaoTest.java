package su.svn.daybook.domain.dao.db.dictionary;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Hooks;
import reactor.test.StepVerifier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import su.svn.daybook.configs.TestContextConfiguration;
import su.svn.daybook.domain.model.db.dictionary.TagLabel;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestContextConfiguration.class)
class TagLabelDaoTest {
    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    static final Class<?> tClass = TagLabelDaoTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    TagLabelDao tagLabelDao;

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
    public void executesFindAll() throws IOException {
        Hooks.onOperatorDebug();
        TagLabel tagLabel = new TagLabel("1", "label1", "user_name1", LOCAL_DATE_TIME_EPOCH, LOCAL_DATE_TIME_EPOCH, false, true, 0);
        tagLabelDao.findAll()
                .as(StepVerifier::create)
                .assertNext(tagLabel::equals)
                .verifyComplete();
    }
}
