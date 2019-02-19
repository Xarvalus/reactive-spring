package spring.reactive.market.transaction;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * MongoDB repository of transactions
 *
 * TODO: Implement specific data operations
 */
public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String> {
    @Query("{ type: ?0 }")
    Flux<Transaction> findAllByType(String transactionType);
}
