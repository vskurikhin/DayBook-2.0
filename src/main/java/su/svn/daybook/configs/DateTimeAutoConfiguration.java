/*
 * This file was last modified at 2021.01.13 00:44 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DateTimeAutoConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
@AutoConfigureBefore({JacksonAutoConfiguration.class})
public class DateTimeAutoConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jacksonObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            final String dateFormat = "dd/MM/yyyy";
            final String timeFormat = "hh:mm:ss a";
            final String dateTimeFormat = "dd/MM/yyyy hh:mm:ss a";
            jacksonObjectMapperBuilder
                    .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(dateFormat)))
                    .deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(dateFormat)))
                    .serializers(new LocalTimeSerializer(DateTimeFormatter.ofPattern(timeFormat)))
                    .deserializers(new LocalTimeDeserializer(DateTimeFormatter.ofPattern(timeFormat)))
                    .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(dateTimeFormat)))
                    .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(dateTimeFormat)));
        };
    }
}
