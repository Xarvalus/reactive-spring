package spring.reactive.market.transaction;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class TransactionValidator implements Validator {
    private static final String REQUIRED = "required";

    @Override
    public boolean supports(Class<?> clazz) {
        return Transaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Transaction transaction = (Transaction) target;

        // TODO: complete the validation

        // Simple value validation
        if (transaction.getValue() == null) {
            errors.rejectValue("type", REQUIRED, REQUIRED);
        }
    }
}
