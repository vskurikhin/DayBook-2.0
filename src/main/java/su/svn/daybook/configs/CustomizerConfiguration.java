/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CustomizerConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class CustomizerConfiguration extends WebFluxAutoConfiguration {

    private final String dateTimeFormat;

    private final DateTimeFormatter dateTimeFormatterDeserializer;

    private final DateTimeFormatter dateTimeFormatterSerializer;

    public CustomizerConfiguration(
            @Value("${spring.jackson.date-format}") String dateFormat,
            @Value("${spring.jackson.serializer-date-time-format}") String dateTimeFormat) {
        this.dateTimeFormat = dateTimeFormat;
        dateTimeFormatterDeserializer = DateTimeFormatter.ofPattern(dateFormat);
        dateTimeFormatterSerializer = DateTimeFormatter.ofPattern(dateTimeFormat);
    }

    @Bean
    @Primary
    public LocalDateTimeSerializer localDateTimeSerializer() {
        return new LocalDateTimeSerializer(dateTimeFormatterSerializer);
    }

    @Bean
    @Primary
    public LocalDateTimeDeserializer localDateTimeDeserializer() {
        return new LocalDateTimeDeserializerConfiguration();
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.simpleDateFormat(dateTimeFormat).failOnEmptyBeans(false)
                .serializerByType(LocalDateTime.class, localDateTimeSerializer())
                .deserializerByType(LocalDateTime.class, localDateTimeDeserializer());
    }

    class LocalDateTimeDeserializerConfiguration extends LocalDateTimeDeserializer {

        private static final long serialVersionUID = 4089365051977962645L;

        LocalDateTimeDeserializerConfiguration() {
            super(dateTimeFormatterDeserializer);
            withShape(JsonFormat.Shape.STRING);
        }
    }
}
