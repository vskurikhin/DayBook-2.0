package su.svn.daybook.domain.dao.db;

import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import su.svn.daybook.domain.dao.db.db.RecordNewsEntryService;
import su.svn.daybook.domain.security.User;
import su.svn.daybook.services.security.DbUserService;
import su.svn.daybook.services.security.UserService;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.util.ArrayList;

import static su.svn.daybook.domain.security.Role.ROLE_USER;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
class DbBasedServiceIntegrationTest {
    static final Class<?> tClass = DbBasedServiceIntegrationTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    UserService userService;

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

    @Nested
    class DbUserServiceTest {

        User USER_1 = User.builder()
                .username("userName1")
                .password("password1")
                .enabled(true)
                .roles(new ArrayList<>() {{ add(ROLE_USER); }})
                .build();

        @Test
        @Disabled
        void checkClass() {
            Assertions.assertEquals(DbUserService.class, userService.getClass());
        }

        @Test
        void findByUsername() {
            User test = userService.findByUsername("userName1").block();
            assert test != null;
            Assertions.assertEquals(USER_1, test);
        }
    }
}