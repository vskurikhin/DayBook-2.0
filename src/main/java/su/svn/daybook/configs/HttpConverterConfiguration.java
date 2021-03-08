/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * HttpConverterConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class HttpConverterConfiguration {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(LocalDateTimeSerializer ldtSerializer) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .serializers(ldtSerializer)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(builder.build());
    }
}
