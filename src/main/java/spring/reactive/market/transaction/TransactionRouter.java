package spring.reactive.market.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TransactionRouter {
    @Bean
    public RouterFunction<ServerResponse> transactionRoutes(TransactionHandler handler) {
        return route()
                .POST("/buy", accept(APPLICATION_JSON), handler::buy)
                .POST("/sell", accept(APPLICATION_JSON), handler::sell)
                .build();
    }
}
