package com.github.monaboiste.yetanovalidate;

import java.util.function.Predicate;
import java.util.regex.Pattern;

final class PredicateFixture {

    static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    static Predicate<String> stringMatchesEmailFormat() {
        return EMAIL_PATTERN.asMatchPredicate();
    }

    static Predicate<Integer> isAdult() {
        return age -> age > 17;
    }
}