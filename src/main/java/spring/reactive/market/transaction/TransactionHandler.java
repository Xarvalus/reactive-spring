package spring.reactive.market.transaction;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TransactionHandler {

    private final TransactionRepository transactionRepository;

    private final Validator validator = new TransactionValidator();

    public TransactionHandler(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Mono<ServerResponse> buy(ServerRequest request) {
        Mono<Transaction> transactionResponse = request
                .bodyToMono(Transaction.class)
                .doOnNext(this::validate);

        Mono<Transaction> saved = transactionResponse.flatMap(transaction -> {
            transaction.setType("BUY");

            return transactionRepository.save(transaction);
        });

        return ok().contentType(APPLICATION_JSON).body(saved, Transaction.class);
    }

    public Mono<ServerResponse> sell(ServerRequest request) {
        Mono<Transaction> transactionResponse = request
                .bodyToMono(Transaction.class)
                .doOnNext(this::validate);

        Mono<Transaction> saved = transactionResponse.flatMap(transaction -> {
            transaction.setType("SELL");

            return transactionRepository.save(transaction);
        });

        return ok().contentType(APPLICATION_JSON).body(saved, Transaction.class);
    }

    private void validate(Transaction transaction) {
        Errors errors = new BeanPropertyBindingResult(transaction, "transaction");

        validator.validate(transaction, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
