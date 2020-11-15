/*
 * This file was last modified at 2020.11.15 19:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * WebFluxConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.thymeleaf.spring5.ISpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.SpringWebFluxTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements ApplicationContextAware, WebFluxConfigurer {

    ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Bean
    public ITemplateResolver thymeleafTemplateResolver() {
        final SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.context);
        resolver.setPrefix("classpath:templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(false);
        resolver.setCheckExistence(false);

        return resolver;
    }

    @Bean
    public ISpringWebFluxTemplateEngine thymeleafTemplateEngine() {
        SpringWebFluxTemplateEngine templateEngine = new SpringWebFluxTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    @Bean
    public ThymeleafReactiveViewResolver thymeleafReactiveViewResolver() {
        ThymeleafReactiveViewResolver viewResolver = new ThymeleafReactiveViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        return viewResolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver());
    }

    @Bean
    public RouterFunction<ServerResponse> cssRouter() {
        return RouterFunctions.resources("/css/**", new ClassPathResource("static/css/"));
    }

    @Bean
    public RouterFunction<ServerResponse> dataRouter() {
        return RouterFunctions.resources("/data/**", new ClassPathResource("static/data/"));
    }

    @Bean
    public RouterFunction<ServerResponse> fontsRouter() {
        return RouterFunctions.resources("/fonts/**", new ClassPathResource("static/fonts/"));
    }

    @Bean
    public RouterFunction<ServerResponse> generatedRouter() {
        return RouterFunctions.resources("/generated/**", new ClassPathResource("static/generated/"));
    }

    @Bean
    public RouterFunction<ServerResponse> raw_svgRouter() {
        return RouterFunctions.resources("/raw-svg/**", new ClassPathResource("static/raw-svg/"));
    }

    @Bean
    public RouterFunction<ServerResponse> swagger_ui_html() {
        return RouterFunctions.resources("/swagger-ui.html", new ClassPathResource("swagger-ui.html"));
    }
}
