package com.github.monaboiste.yetanovalidate;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;


@SuppressWarnings("squid:S119")
public abstract class ValidationExceptionBuilder
        <SELF extends ValidationExceptionBuilder<SELF, ACTUAL>, ACTUAL> {

    private final SELF self;
    private final RuleViolation.RuleViolationBuilder ruleViolationBuilder
            = new RuleViolation.RuleViolationBuilder();
    protected ACTUAL instance;
    private Predicate<? super ACTUAL> predicate;

    protected ValidationExceptionBuilder(final ACTUAL instance, Class<?> selfType) {
        // noinspection unchecked
        this.self = (SELF) selfType.cast(this);
        this.instance = instance;
    }

    /**
     * Extract nested attribute from {@link #instance}.
     *
     * @param extractor field extractor
     * @param <P>       class type of extracted field
     * @param <BUILDER> this builder class type
     * @return this builder
     */
    public <P, BUILDER extends ValidationExceptionBuilder<?, P>>
    BUILDER extracting(final Function<? super ACTUAL, ? extends P> extractor) {
        P property = extractor.apply(instance);
        // noinspection unchecked
        this.instance = (ACTUAL) property;
        // noinspection unchecked
        return (BUILDER) self;
    }

    /**
     * Condition check.
     *
     * @param predicate constraint to check against
     * @return this builder
     */
    public SELF doesNotSatisfy(final Predicate<? super ACTUAL> predicate) {
        this.predicate = predicate;
        return this.self;
    }

    /**
     * End chaining, perform validation checks against the {@link #predicate}.
     *
     * @param exceptionClazz the class of the exception to instantiate and throw
     *                       when the instance didn't pass the validation
     * @param <E>            exception type
     * @throws E exception of type passed in {@code exceptionClazz} parameter
     *
     * @see #check()
     */
    public <E extends ValidationException> void withExceptionTypeOf(final Class<E> exceptionClazz) throws E {
        requireNonNull(this.predicate, "'predicate' must not be null, use 'doesNotSatisfy' method");
        if (this.predicate.test(instance)) {
            return;
        }
        throwException(exceptionClazz);
    }

    /**
     * End chaining, perform validation check against the {@link #predicate}.
     *
     * @throws ValidationException generic validation exception
     *
     * @see #withExceptionTypeOf(Class)
     */
    public void check() throws ValidationException {
        withExceptionTypeOf(ValidationException.class);
    }

    /**
     * Set the error code passed to {@link RuleViolation}.
     *
     * @param code error code
     * @return this builder
     */
    public SELF withCode(String code) {
        this.ruleViolationBuilder.code(code);
        return this.self;
    }

    /**
     * Set the field name passed to {@link RuleViolation}.
     *
     * @param field name of the field
     * @return this builder
     */
    public SELF withField(String field) {
        this.ruleViolationBuilder.field(field);
        return this.self;
    }

    /**
     * Set the error message passed to {@link RuleViolation}.
     *
     * @param message error message
     * @return this builder
     */
    public SELF withMessage(String message) {
        this.ruleViolationBuilder.message(message);
        return this.self;
    }

    private <E extends ValidationException> void throwException(final Class<E> exceptionClazz) {
        try {
            throw exceptionClazz
                    .getConstructor(RuleViolation.class)
                    .newInstance(this.ruleViolationBuilder.build());
        } catch (NoSuchMethodException
                 | SecurityException
                 | InstantiationException
                 | IllegalAccessException
                 | IllegalArgumentException
                 | InvocationTargetException ex) {
            throw new YetanoInternalException(
                    "Constructor in class not found (RuleViolation) or it's not public", ex);
        }
    }
}