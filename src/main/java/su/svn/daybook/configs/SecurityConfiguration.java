package su.svn.daybook.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import su.svn.daybook.services.security.AuthenticationSuccessHandler;
import su.svn.daybook.services.security.LogoutSuccessHandler;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
    @Value("${application.security.strength}")
    private int strength;

    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .authorizeExchange()
                .pathMatchers("/", "/index.html", "/register", "/registration.html", "/login", "/logout", "/error").permitAll()
                .pathMatchers("/i18n/**",
                        "/css/**",
                        "/fonts/**",
                        "/img/**",
                        "/js/**",
                        "/public/**",
                        "/webjars/**").permitAll()
                .pathMatchers("/application").hasRole("USER")
                .pathMatchers("/user/**").hasRole("USER")
                .anyExchange()
                .authenticated()
                .and()
                .formLogin()
                .authenticationSuccessHandler(new AuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutSuccessHandler(new LogoutSuccessHandler())
                .and()
                .build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }
}
