package su.svn.daybook.domain.model.db.dictionary;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TestDataDictionary {

    public static final LocalDateTime LOCAL_DATE_TIME_EPOCH = LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIN);

    public static final Codifier CODIFIER_1 = Codifier.builder()
            .id(1L)
            .code("code1")
            .value("value1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .isDisabled(false)
            .visible(true)
            .flags(0)
            .build();

    public static final TagLabel TAG_LABEL_1 = TagLabel.builder()
            .id("1")
            .label("label1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .isDisabled(false)
            .visible(true)
            .flags(0)
            .build();

    public static final Vocabulary VOCABULARY_1 = Vocabulary.builder()
            .id(1L)
            .wordId(1L)
            .value("value1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .isDisabled(false)
            .visible(true)
            .flags(0)
            .build();

    public static final Word WORD_1 = Word.builder()
            .id(1L)
            .word("word1")
            .userName("userName1")
            .createTime(LOCAL_DATE_TIME_EPOCH)
            .updateTime(LOCAL_DATE_TIME_EPOCH)
            .isDisabled(false)
            .visible(true)
            .flags(0)
            .build();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Nested
    class CodifierTest {
    }

    @Nested
    class TagLabelTest {

    }

    @Nested
    class VocabularyTest {

    }

    @Nested
    class WordTest {

    }
}
