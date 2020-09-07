package su.svn.daybook.domain.dao.db.db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import su.svn.daybook.domain.dao.db.TestConnectionFactoryConfiguration;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
public class DbDaoTest {

    static final Class<?> tClass = DbDaoTest.class;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}
