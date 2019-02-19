package spring.reactive.market.trader;

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
public class TraderHandler {
    private final TraderRepository traderRepository;

    private final Validator validator = new TraderValidator();

    public TraderHandler(TraderRepository traderRepository) {
        this.traderRepository = traderRepository;
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        Mono<Trader> trader = request
                .bodyToMono(Trader.class)
                .doOnNext(this::validate);

        Mono<Trader> saved = trader.flatMap(traderRepository::save);

        return ok().contentType(APPLICATION_JSON).body(saved, Trader.class);
    }

    private void validate(Trader trader) {
        Errors errors = new BeanPropertyBindingResult(trader, "trader");

        validator.validate(trader, errors);
        if (errors.hasErrors()) {
            throw new ServerWebInputException(errors.toString());
        }
    }
}
