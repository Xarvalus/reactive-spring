package spring.reactive.market;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;
import spring.reactive.market.trader.Trader;

import java.util.List;
import java.util.Random;

@AutoConfigureWebTestClient
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MarketApplicationTests {

    @LocalServerPort
    private String port;

    @Autowired
    private WebTestClient testClient;

    private Random random = new Random();

    /**
     * Many-write Transactions Test Case
     *
     * Registers 10 traders
     * Executes adding 10k transactions reactively in parallel
     */
    @Test
    public void reactiveManyWriteTransactionsTest() {
        // Separate WebClient for registering & retrieving Trader's Id, used in adding Transaction
        // TODO: in production should be handled with Auth (eg via JWT)
        WebClient client = WebClient.create("http://localhost:" + port);

        final String[] traderNames = new String[]{"Mike", "Adam", "John"};
        final String BUY = "/buy";
        final String SELL = "/sell";

        // 10 traders
        final List<Trader> traders = Flux.range(1, 10)
                .flatMap(index -> {
                    // Pseudo-random trader name
                    String name = traderNames[random.nextInt(traderNames.length)];

                    String trader = "{ \"name\": \"" + name + "\" }";

                    return client.post()
                            .uri("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(trader), String.class)
                            .retrieve()
                            .bodyToMono(Trader.class);
                })
                .collectList()
                .block();

        // 10.000 transactions
        ParallelFlux transactions = Flux.range(1, 10000)
                .flatMap(index -> {
                    // Pseudo-random transaction value, type & trader
                    Double value = Math.random() * 1000 + Math.random();
                    String url = Math.random() > 0.5 ? BUY : SELL;
                    Trader trader = traders.get(random.nextInt(traders.size()));

                    String transaction = "{ \"value\": " + value.toString() + ", \"trader\": " + trader.getId() + " }";

                    testClient.post()
                            .uri(url)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(transaction), String.class)
                            .exchange()
                            .expectStatus().isOk();

                    return Flux.empty();
                })
                .parallel()
                .runOn(Schedulers.parallel());

        // Execute concurrently
        transactions
                .sequential()
                .subscribe();
    }
}