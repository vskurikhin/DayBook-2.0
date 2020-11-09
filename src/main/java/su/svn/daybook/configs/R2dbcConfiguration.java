/*
 * This file was last modified at 2020.09.01 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * R2dbcConfiguration.java
 * $Id$
 */

package su.svn.daybook.configs;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.Option;
import io.r2dbc.spi.ValidationDepth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.connectionfactory.R2dbcTransactionManager;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.core.ReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.transaction.ReactiveTransactionManager;

import java.time.Duration;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.ACQUIRE_RETRY;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.INITIAL_SIZE;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.VALIDATION_DEPTH;
import static io.r2dbc.pool.PoolingConnectionFactoryProvider.VALIDATION_QUERY;
import static io.r2dbc.spi.ConnectionFactoryOptions.CONNECT_TIMEOUT;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Configuration
@EnableR2dbcRepositories(basePackages = "su.svn.daybook.domain.dao.db")
public class R2dbcConfiguration {
    @Value("${application.db.r2dbc.host-rw}")
    private String dbHostRw;

    @Value("${application.db.r2dbc.host-ro}")
    private String dbHostRo;

    @Value("${application.db.r2dbc.port-rw}")
    private int dbPortRw;

    @Value("${application.db.r2dbc.port-ro}")
    private int dbPortRo;

    @Value("${application.db.r2dbc.database}")
    private String dbName;

    @Value("${application.db.r2dbc.pool.connect-timeout:500}")
    private int connectTimeout;

    @Value("${application.db.r2dbc.pool.validation-depth}")
    private ValidationDepth validationDepth;

    @Value("${application.db.r2dbc.pool.validation-query}")
    private String validationQuery;

    @Value("${spring.r2dbc.pool.acquire-retry:3}")
    private int acquireRetry;

    @Value("${spring.r2dbc.pool.initial-size:10}")
    private int initialSize;

    @Value("${spring.r2dbc.pool.max-size:10}")
    private int maxSize;

    @Value("${application.db.username}")
    private String dbUser;

    @Value("${application.db.password}")
    private String dbPassword;

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "pool")
                .option(PROTOCOL, "postgresql")
                .option(HOST, dbHostRw)
                .option(PORT, dbPortRw)
                .option(USER, dbUser)
                .option(PASSWORD, dbPassword)
                .option(DATABASE, dbName)
                .option(ACQUIRE_RETRY, acquireRetry)
                .option(CONNECT_TIMEOUT, Duration.ofMillis(connectTimeout))
                .option(INITIAL_SIZE, initialSize)
                .option(MAX_SIZE, Math.max(maxSize, 100))
                .option(VALIDATION_DEPTH, validationDepth)
                .option(VALIDATION_QUERY, validationQuery)
                .option(Option.valueOf("maxIdleTime"), Duration.ofMillis(100))
                .option(Option.valueOf("registerJmx"), true)
                .option(Option.valueOf("useServerPrepareStatement"), true) // optional, default false
                .build();
        return ConnectionFactories.get(options);
    }

    @Bean(name = "connectionFactoryRo")
    public ConnectionFactory connectionFactoryRo() {
        ConnectionFactoryOptions options = ConnectionFactoryOptions.builder()
                .option(DRIVER, "pool")
                .option(PROTOCOL, "postgresql")
                .option(HOST, dbHostRo)
                .option(PORT, dbPortRo)
                .option(USER, dbUser)
                .option(PASSWORD, dbPassword)
                .option(DATABASE, dbName)
                .option(ACQUIRE_RETRY, acquireRetry)
                .option(CONNECT_TIMEOUT, Duration.ofMillis(connectTimeout))
                .option(INITIAL_SIZE, initialSize)
                .option(MAX_SIZE, maxSize)
                .option(VALIDATION_DEPTH, validationDepth)
                .option(VALIDATION_QUERY, validationQuery)
                .option(Option.valueOf("maxIdleTime"), Duration.ofMillis(100))
                .option(Option.valueOf("registerJmx"), true)
                .option(Option.valueOf("useServerPrepareStatement"), true) // optional, default false
                .build();
        return ConnectionFactories.get(options);
    }

    @Bean
    public ReactiveDataAccessStrategy reactiveDataAccessStrategy() {
        return new DefaultReactiveDataAccessStrategy(PostgresDialect.INSTANCE);
    }

    @Bean
    public R2dbcRepositoryFactory factory(DatabaseClient client, ReactiveDataAccessStrategy strategy) {
        return new R2dbcRepositoryFactory(client, strategy);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }
}
