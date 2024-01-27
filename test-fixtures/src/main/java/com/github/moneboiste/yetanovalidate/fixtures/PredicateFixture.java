package com.github.moneboiste.yetanovalidate.fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PredicateFixture {

    public static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)$");

    public static Predicate<String> stringMatchesEmailFormat() {
        return EMAIL_PATTERN.asMatchPredicate();
    }

    public static Predicate<Integer> isAdult() {
        return age -> age > 17;
    }
}