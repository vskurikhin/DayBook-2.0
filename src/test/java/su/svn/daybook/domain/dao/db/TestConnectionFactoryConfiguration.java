package su.svn.daybook.domain.dao.db;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import su.svn.daybook.domain.dao.db.db.AllRecordViewDao;
import su.svn.daybook.domain.dao.db.db.NewsGroupDao;
import su.svn.daybook.domain.dao.db.db.RecordDao;
import su.svn.daybook.domain.dao.db.db.TaggetRecordViewDao;
import su.svn.daybook.domain.dao.db.dictionary.CodifierDao;
import su.svn.daybook.domain.dao.db.dictionary.I18nDao;
import su.svn.daybook.domain.dao.db.dictionary.LanguageDao;
import su.svn.daybook.domain.dao.db.dictionary.TagLabelDao;
import su.svn.daybook.domain.dao.db.dictionary.VocabularyDao;
import su.svn.daybook.domain.dao.db.dictionary.WordDao;
import su.svn.daybook.domain.dao.db.security.RoleDao;
import su.svn.daybook.domain.dao.db.security.UserNameDao;
import su.svn.daybook.services.security.DbUserService;
import su.svn.daybook.services.security.UserService;

@SpringBootApplication(scanBasePackageClasses = {
        CodifierDao.class, DbUserService.class, I18nDao.class, LanguageDao.class, RecordDao.class, RoleDao.class,
        TagLabelDao.class, UserNameDao.class, UserService.class, VocabularyDao.class, WordDao.class, UserService.class,
        DbUserService.class, UserNameDao.class, RoleDao.class, RecordDao.class, NewsGroupDao.class,
        TaggetRecordViewDao.class, AllRecordViewDao.class
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
