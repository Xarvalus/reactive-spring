package spring.reactive.market.trader;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TraderValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public boolean supports(Class<?> clazz) {
        return Trader.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Trader trader = (Trader) target;

        // TODO: complete the validation

        // Simple name validation
        if (!StringUtils.hasLength(trader.getName())) {
            errors.rejectValue("name", REQUIRED, REQUIRED);
        }
    }
}
