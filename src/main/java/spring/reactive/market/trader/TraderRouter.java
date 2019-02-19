package spring.reactive.market.trader;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TraderRouter {
    @Bean
    public RouterFunction<ServerResponse> traderRoutes(TraderHandler handler) {
        return route()
                .POST("/register", accept(APPLICATION_JSON), handler::register)
                .build();
    }
}
