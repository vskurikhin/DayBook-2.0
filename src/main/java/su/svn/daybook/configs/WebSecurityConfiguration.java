/*
 * This file was last modified at 2021.03.04 13:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * WebSecurityConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import reactor.core.publisher.Mono;
import su.svn.daybook.services.security.AuthenticationManager;
import su.svn.daybook.services.security.SecurityContextRepository;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfiguration {

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfiguration(
            AuthenticationManager authenticationManager,
            SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() ->
                        swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .headers().frameOptions().mode(XFrameOptionsServerHttpHeadersWriter.Mode.SAMEORIGIN)
                .and()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.GET, "/").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/pages").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/news-group/all").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/news-group/like").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/news-group/page").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/profile").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/record/fetch/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/records").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/tag-label/all").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/v1/resource/tag-label/in").permitAll()
                .pathMatchers(HttpMethod.GET, "/css/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/favicon.ico").permitAll()
                .pathMatchers(HttpMethod.GET, "/generated/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/public/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/raw-svg/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/static/favicon.ico").permitAll()
                .pathMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .pathMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange().authenticated()
                .and()
                .build();
    }
}
