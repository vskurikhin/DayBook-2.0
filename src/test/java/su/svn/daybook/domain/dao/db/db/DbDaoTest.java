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
import su.svn.daybook.domain.model.db.db.Role;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.util.Arrays;

import static su.svn.daybook.domain.model.db.db.TestDataDb.ROLE_1;
import static su.svn.daybook.domain.model.db.dictionary.TestDataDictionary.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
public class DbDaoTest {

    static final Class<?> tClass = DbDaoTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    RoleDao roleDao;

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
    class CodifierDaoTest {

        final Role ROLE_2 = Role.builder()
                .roleName("roleName2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final Role ROLE_3 = Role.builder()
                .roleName("roleName3")
                .userName("userName3")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executesFindAll() throws IOException {
            Hooks.onOperatorDebug();
            roleDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(ROLE_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            roleDao.saveAll(Arrays.asList(ROLE_2, ROLE_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    static class RoleDaoTest {

    }
}
