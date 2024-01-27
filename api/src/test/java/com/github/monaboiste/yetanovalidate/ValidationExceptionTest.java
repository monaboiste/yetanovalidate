package com.github.monaboiste.yetanovalidate;

import org.junit.jupiter.api.Test;

import static com.github.monaboiste.yetanovalidate.ExceptionFixture.CannotBuyAlcoholException;
import static com.github.monaboiste.yetanovalidate.ExceptionFixture.cannotBuyAlcoholExceptionType;
import static com.github.monaboiste.yetanovalidate.PersonFixture.person;
import static com.github.monaboiste.yetanovalidate.PersonFixture.personWithInvalidEmailFormat;
import static com.github.monaboiste.yetanovalidate.PersonFixture.underagePerson;
import static com.github.monaboiste.yetanovalidate.PredicateFixture.isAdult;
import static com.github.monaboiste.yetanovalidate.PredicateFixture.stringMatchesEmailFormat;
import static com.github.monaboiste.yetanovalidate.ValidationException.throwWhen;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ValidationExceptionTest {

    @Test
    void does_not_throw_when_person_satisfies_the_rule() {
        var validPerson = person();

        var rule = throwWhen(validPerson)
                .extracting(PersonFixture::email)
                .doesNotSatisfy(stringMatchesEmailFormat());

        assertThatNoException().isThrownBy(rule::check);

    }

    @Test
    void should_throw_when_person_has_invalid_email_format() {
        var personWithInvalidEmail = personWithInvalidEmailFormat();

        var rule = throwWhen(personWithInvalidEmail)
                .extracting(PersonFixture::email)
                .doesNotSatisfy(stringMatchesEmailFormat());

        assertThatThrownBy(rule::check).isInstanceOf(ValidationException.class);
    }

    @Test
    void should_throw_when_person_is_underage() {
        var underagePerson = underagePerson();

        var rule = throwWhen(underagePerson)
                .extracting(PersonFixture::age)
                .doesNotSatisfy(isAdult());

        assertThatThrownBy(rule::check).isInstanceOf(ValidationException.class);
    }

    @Test
    void thrown_exception_should_have_code_property() {
        var personWithInvalidEmail = personWithInvalidEmailFormat();

        var rule = throwWhen(personWithInvalidEmail)
                .extracting(PersonFixture::email)
                .doesNotSatisfy(stringMatchesEmailFormat())
                .withCode("bad_email_format");

        var exception = catchThrowableOfType(rule::check, ValidationException.class);

        assertThat(exception)
                .extracting(ValidationException::ruleViolation)
                .extracting(RuleViolation::code)
                .isEqualTo("bad_email_format");
    }

    @Test
    void thrown_exception_should_have_field_property() {
        var personWithInvalidEmail = personWithInvalidEmailFormat();

        var rule = throwWhen(personWithInvalidEmail)
                .extracting(PersonFixture::email)
                .doesNotSatisfy(stringMatchesEmailFormat())
                .withField("email");

        var exception = catchThrowableOfType(rule::check, ValidationException.class);

        assertThat(exception)
                .extracting(ValidationException::ruleViolation)
                .extracting(RuleViolation::field)
                .isEqualTo("email");
    }

    @Test
    void thrown_exception_should_have_error_property() {
        var personWithInvalidEmail = personWithInvalidEmailFormat();

        var rule = throwWhen(personWithInvalidEmail)
                .extracting(PersonFixture::email)
                .doesNotSatisfy(stringMatchesEmailFormat())
                .withMessage("Email has incorrect format.");

        var exception = catchThrowableOfType(rule::check, ValidationException.class);

        assertThat(exception)
                .extracting(ValidationException::ruleViolation)
                .extracting(RuleViolation::message)
                .isEqualTo("Email has incorrect format.");
    }

    @Test
    void throws_custom_exception_with_message() {
        var underagePerson = underagePerson();

        var rule = throwWhen(underagePerson)
                .extracting(PersonFixture::age)
                .doesNotSatisfy(isAdult())
                .withField("age")
                .withMessage("You're too young to drink an alcohol!");

        var cannotBuyAlcoholExceptionClass = cannotBuyAlcoholExceptionType();

        assertThatThrownBy(() -> rule.withExceptionTypeOf(cannotBuyAlcoholExceptionClass))
                .isInstanceOf(CannotBuyAlcoholException.class)
                .hasMessage("You're too young to drink an alcohol!");
    }

    @Test
    void can_extract_nested_properties() {
        var person = person();

        var rule = throwWhen(person)
                .extracting(PersonFixture::livesAt)
                .extracting(PersonFixture.Address::line1);

        assertThat(rule.instance).isEqualTo(person.livesAt().line1());
    }
}