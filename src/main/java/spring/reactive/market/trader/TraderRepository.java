package spring.reactive.market.trader;

import org.springframework.data.r2dbc.repository.query.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * R2DBC PostgreSQL repository of traders
 *
 * TODO: Implement specific data operations from R2DBC
 */
public interface TraderRepository extends R2dbcRepository<Trader, Integer> {
    @Query("SELECT id, name FROM trader WHERE name = $1")
    Flux<Trader> findAllByName(String name);
}
