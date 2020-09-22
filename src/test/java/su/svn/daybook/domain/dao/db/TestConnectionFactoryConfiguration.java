package su.svn.daybook.domain.dao.db;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import su.svn.daybook.domain.dao.db.db.NewsGroupDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.dao.db.security.RoleDao;
import su.svn.daybook.domain.dao.db.security.UserNameDao;
import su.svn.daybook.services.security.DbUserService;
import su.svn.daybook.services.security.UserService;

@SpringBootApplication(scanBasePackageClasses = {
        UserService.class, DbUserService.class, UserNameDao.class, RoleDao.class, RecordDao.class, NewsGroupDao.class
})
@EnableTransactionManagement
@EnableR2dbcRepositories(basePackages = "su.svn.daybook.domain.dao.db")
public class TestConnectionFactoryConfiguration {
    @TestConfiguration
    static class ContextConfiguration {

        @Bean
        @Primary
        public ConnectionFactory connectionFactory() {
            return ConnectionFactories.get("r2dbc:h2:mem:///db?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
        }

        @Bean(name = "connectionFactoryRo")
        public ConnectionFactory connectionFactoryRo() {
            return ConnectionFactories.get("r2dbc:h2:mem:///db?options=DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL");
        }
    }
}
