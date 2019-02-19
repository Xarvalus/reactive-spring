package spring.reactive.market.transaction;

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
public class TransactionRouterTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void transactionRoutesBuy() {
        String jsonTrader = "{ \"value\": 123.45 }";

        client.post()
                .uri("/buy")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(jsonTrader), String.class)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void transactionRoutesSell() {
        String jsonTrader = "{ \"value\": 456.78 }";

        client.post()
                .uri("/sell")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(jsonTrader), String.class)
                .exchange()
                .expectStatus().isOk();
    }
}