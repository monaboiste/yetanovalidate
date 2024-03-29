package com.github.monaboiste.yetanovalidate;

import lombok.Getter;

/**
 * The exception class, which extends this class, may be nested static BUT MUSTN'T BE INNER of another class.
 * The class must provide the 1-param {@link RuleViolation} constructor as well.
 */
@Getter
public class ValidationException extends RuntimeException {

    private final transient RuleViolation ruleViolation;

    public ValidationException(final RuleViolation ruleViolation) {
        super(ruleViolation.message());
        this.ruleViolation = ruleViolation;
    }

    /**
     * Start condition check chain.
     * <p>
     * Example usage:
     * <pre>
     * throwWhen(underagePerson)
     *     .extracting(Person::age)
     *     .doesNotSatisfy(isAdult())
     *     .withField("age")
     *     .withMessage("You're too young to drink an alcohol!")
     *     .withCode("illegal_age")
     *     .withExceptionTypeOf(CannotBuyAlcoholException.class);
     * </pre>
     * @param instance object to validate
     * @return builder, which allows to chain constraints and checks on the object
     * @param <T> instance's type
     */
    public static <T> ValidationExceptionBuilder<T> throwWhen(final T instance) {
        return new ValidationExceptionBuilder<>(instance);
    }

    public static class ValidationExceptionBuilder<T>
            extends com.github.monaboiste.yetanovalidate.ValidationExceptionBuilder<ValidationExceptionBuilder<T>, T> {

        public ValidationExceptionBuilder(final T instance) {
            super(instance, ValidationExceptionBuilder.class);
        }
    }
}