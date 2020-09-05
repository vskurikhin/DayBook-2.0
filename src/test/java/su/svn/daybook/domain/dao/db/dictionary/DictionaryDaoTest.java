package su.svn.daybook.domain.dao.db.dictionary;

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
import su.svn.daybook.domain.model.db.dictionary.*;
import su.svn.daybook.utils.TestDatabaseUtil;

import java.io.IOException;
import java.util.Arrays;

import static su.svn.daybook.domain.model.db.dictionary.TestDataDictionary.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConnectionFactoryConfiguration.class)
public class DictionaryDaoTest {

    static final Class<?> tClass = DictionaryDaoTest.class;

    @Autowired
    ConnectionFactory connectionFactory;

    @Autowired
    CodifierDao codifierDao;

    @Autowired
    TagLabelDao tagLabelDao;

    @Autowired
    VocabularyDao vocabularyDao;

    @Autowired
    WordDao wordDao;

    @Autowired
    LanguageDao languageDao;

    @Autowired
    I18nDao i18nDao;

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

        final Codifier CODIFIER_2 = Codifier.builder()
                .code("code2")
                .value("value2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final Codifier CODIFIER_3 = Codifier.builder()
                .code("code3")
                .value("value3")
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
            codifierDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(CODIFIER_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            codifierDao.saveAll(Arrays.asList(CODIFIER_2, CODIFIER_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    @Nested
    class TagLabelDaoTest {

        final TagLabel TAG_LABEL_2 = TagLabel.builder()
                .label("label2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final TagLabel TAG_LABEL_3 = TagLabel.builder()
                .label("label3")
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
            tagLabelDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(TAG_LABEL_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            tagLabelDao.saveAll(Arrays.asList(TAG_LABEL_2, TAG_LABEL_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    @Nested
    class VocabularyDaoTest {

        final Vocabulary VOCABULARY_2 = Vocabulary.builder()
                .wordId(1L)
                .value("value2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final Vocabulary VOCABULARY_3 = Vocabulary.builder()
                .wordId(1L)
                .value("value3")
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
            vocabularyDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(VOCABULARY_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            vocabularyDao.saveAll(Arrays.asList(VOCABULARY_2, VOCABULARY_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    @Nested
    class WordDaoTest {

        final Word WORD_2 = Word.builder()
                .word("word2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();


        final Word WORD_3 = Word.builder()
                .word("word3")
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
            wordDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(WORD_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            wordDao.saveAll(Arrays.asList(WORD_2, WORD_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    @Nested
    class LanguageDaoTest {

        final Language LANGUAGE_2 = Language.builder()
                .language("language2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final Language LANGUAGE_3 = Language.builder()
                .language("language3")
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
            languageDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(LANGUAGE_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            languageDao.saveAll(Arrays.asList(LANGUAGE_2, LANGUAGE_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }

    class I18nDaoTest {

        final I18n I18N_2 = I18n.builder()
                .languageId(1L)
                .message("message2")
                .translation("translation2")
                .userName("userName2")
                .createTime(LOCAL_DATE_TIME_EPOCH)
                .updateTime(LOCAL_DATE_TIME_EPOCH)
                .isDisabled(false)
                .visible(true)
                .flags(0)
                .build();

        final I18n I18N_3 = I18n.builder()
                .languageId(1L)
                .message("message3")
                .translation("translation3")
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
            i18nDao.findAll()
                    .as(StepVerifier::create)
                    .assertNext(I18N_1::equals)
                    .verifyComplete();
        }

        @Test
        void executesSaveAll() {
            Hooks.onOperatorDebug();
            i18nDao.saveAll(Arrays.asList(I18N_2, I18N_3))
                    .as(StepVerifier::create)
                    .expectNextCount(2)
                    .verifyComplete();
        }
    }
}
