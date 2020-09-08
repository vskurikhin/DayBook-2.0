package su.svn.daybook.domain.dao.db.security;

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
import su.svn.daybook.domain.model.db.security.Role;
import su.svn.daybook.domain.model.db.security.UserName;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.util.Arrays;

import static su.svn.daybook.domain.model.db.dictionary.TestDataDictionary.LOCAL_DATE_TIME_EPOCH;
import static su.svn.daybook.domain.model.db.security.TestDataSecurity.ROLE_USER_1;
import static su.svn.daybook.domain.model.db.security.TestDataSecurity.USERNAME_1;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
class SecurityDaoTest {

    static final Class<?> tClass = SecurityDaoTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    RoleDao roleDao;

    @Autowired
    UserNameDao userNameDao;

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
    class RoleDaoTest {

        final Role ROLE_2 = Role.builder()
                .roleName("roleName2")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final Role ROLE_3 = Role.builder()
                .roleName("roleName3")
                .userName("userName1")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executeFluxByUserName() throws IOException {
            Hooks.onOperatorDebug();
            roleDao.fluxByUserName("userName1")
                    .as(StepVerifier::create)
                    .assertNext(ROLE_USER_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFindAll() throws IOException {
            Hooks.onOperatorDebug();
            roleDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(ROLE_USER_1::equals)
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

    @Nested
    class UserNameDaoTest {

        final UserName USERNAME_2 = UserName.builder()
                .userName("userName2")
                .password("password2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        final UserName USERNAME_3 = UserName.builder()
                .userName("userName3")
                .password("password3")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .enabled(true)
                .visible(true)
                .flags(0)
                .build();

        @Test
        void executeFetchByUserName() throws IOException {
            Hooks.onOperatorDebug();
            userNameDao.fetchByUserName("userName1")
                    .as(StepVerifier::create)
                    .assertNext(USERNAME_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesFindAll() throws IOException {
            Hooks.onOperatorDebug();
            userNameDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(USERNAME_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            userNameDao.saveAll(Arrays.asList(USERNAME_2, USERNAME_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }
}