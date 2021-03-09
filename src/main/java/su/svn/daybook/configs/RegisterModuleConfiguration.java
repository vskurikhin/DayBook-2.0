/*
 * This file was last modified at 2021.03.08 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RegisterModuleConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RegisterModuleConfiguration {

    @Bean
    public Module javaTimeModule(LocalDateTimeSerializer localDateTimeSerializer) {

        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(localDateTimeSerializer);

        return module;
    }
}
