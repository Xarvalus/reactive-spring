package spring.reactive.market.trader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TraderRouterTest {
    @Autowired
    private WebTestClient client;

    @Test
    public void traderRoutes() {
        String jsonTrader = "{ \"name\": \"Mike\" }";

        client.post()
                .uri("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(jsonTrader), String.class)
                .exchange()
                .expectStatus().isOk();
    }
}