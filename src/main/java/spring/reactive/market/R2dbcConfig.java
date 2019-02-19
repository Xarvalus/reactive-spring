package spring.reactive.market;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.r2dbc.dialect.PostgresDialect;
import org.springframework.data.r2dbc.function.DatabaseClient;
import org.springframework.data.r2dbc.function.DefaultReactiveDataAccessStrategy;
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory;
import org.springframework.data.relational.core.mapping.RelationalMappingContext;
import spring.reactive.market.trader.TraderRepository;

/**
 * Collection of configuration beans for using R2DBC PostgreSQL
 *
 * R2DBC does not facilitate ORM capabilities, needs manual wiring
 */
@Configuration
public class R2dbcConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        return new PostgresqlConnectionFactory(PostgresqlConnectionConfiguration
                .builder()
                .host("127.0.0.1")
                .database("market-postgres")
                .username("postgres")
                .password("docker-postgres")
                .build());
    }

    @Bean
    public DatabaseClient databaseClient() {
        return DatabaseClient.builder()
                .connectionFactory(connectionFactory())
                .build();
    }

    @Bean
    public MappingContext mappingContext() {
        final RelationalMappingContext relationalMappingContext = new RelationalMappingContext();

        relationalMappingContext.afterPropertiesSet();

        return relationalMappingContext;
    }

    @Bean
    public R2dbcRepositoryFactory repositoryFactory() {
        return new R2dbcRepositoryFactory(databaseClient(), mappingContext(),
                new DefaultReactiveDataAccessStrategy(new PostgresDialect()));
    }

    @Bean
    public TraderRepository traderRepository() {
        return repositoryFactory().getRepository(TraderRepository.class);
    }

    /**
     * Setups {@link spring.reactive.market.trader.Trader} entity table in database
     */
    @EventListener(ApplicationReadyEvent.class)
    public void setupTraderTableAfterStartup() {
        try {
            databaseClient().execute()
                    .sql("CREATE TABLE trader (id SERIAL PRIMARY KEY, name VARCHAR(100))")
                    .fetch()
                    .all()
                    .subscribe();
        } catch (Exception e) {
            // TODO: keep it silent
        }
    }
}
