package su.svn.daybook.domain.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
class ArticleDtoTest implements Jackson2ObjectMapperBuilderCustomizer {

    @Autowired
    Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder;

    @Autowired
    private ObjectMapper objectMapper;

    ObjectReader objectReader;

    InputStream test;

    String json = "{\"id\":\"ac5cc359-c1b7-4dc2-a13f-57a3ac6f130b\"," +
            "\"newsGroupId\":\"00000000-0000-0000-0000-000000000001\"," +
            "\"title\":\"test3.\"," +
            "\"include\":\"include3.\"," +
            "\"anchor\":\"anchor3.\"," +
            "\"summary\":\"Summary3\"," +
            "\"userName\":null," +
            "\"publicTime\":\"2021-03-01T11:28:03.020\"," +
            "\"visible\":true," +
            "\"tags\":[\"API\"]}";
    @BeforeEach
    void setUp() {
        objectReader = new ObjectMapper().readerFor(ArticleDto.class);
        setupObject();
        test = new ByteArrayInputStream(json.getBytes());
    }

    private void setupObject() {
        objectMapper.registerModule(new ParameterNamesModule());
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        objectMapper.setDateFormat(df);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

    @Test
    @Disabled
    void getPublicTime() throws IOException {
        ArticleDto value = objectReader.readValue(test, ArticleDto.class);
        System.out.println("value = " + value);
    }

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S][X]");
        LocalDateTimeSerializer localDateTimeSerializer = new LocalDateTimeSerializer(formatter);
        LocalDateTimeDeserializer localDateTimeDeserializer = new LocalDateTimeDeserializer(formatter);

        jacksonObjectMapperBuilder.failOnEmptyBeans(false) // prevent InvalidDefinitionException Error
                .serializerByType(LocalDateTime.class, localDateTimeSerializer)
                .deserializerByType(LocalDateTime.class, localDateTimeDeserializer);
    }
}