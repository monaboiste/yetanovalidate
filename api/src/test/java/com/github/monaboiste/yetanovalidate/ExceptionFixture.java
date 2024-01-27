package com.github.monaboiste.yetanovalidate;

final class ExceptionFixture {

    static Class<CannotBuyAlcoholException> cannotBuyAlcoholExceptionType() {
        return CannotBuyAlcoholException.class;
    }

    static class CannotBuyAlcoholException extends ValidationException {
        public CannotBuyAlcoholException(final RuleViolation ruleViolation) {
            super(ruleViolation);
        }
    }
}
