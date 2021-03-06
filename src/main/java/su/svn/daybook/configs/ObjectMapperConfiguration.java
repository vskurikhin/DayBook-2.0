/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ObjectMapperConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class ObjectMapperConfiguration {

    @Bean
    @Primary
    public ObjectMapper objectMapper(LocalDateTimeSerializer localDateTimeSerializer) {

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(localDateTimeSerializer);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);  // NON_EMPTY for '' or NULL value
        mapper.registerModule(module);

        return mapper;
    }
}
