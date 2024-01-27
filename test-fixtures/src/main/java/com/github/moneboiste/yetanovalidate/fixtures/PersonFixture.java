package com.github.moneboiste.yetanovalidate.fixtures;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PersonFixture {

    public static Person person() {
        return new Person(
                "Johnie",
                "johndoe@email.com",
                23,
                new Address("ul. Lipka 756", "68-994, Parczew")
        );
    }

    public static Person underagePerson() {
        return new Person(
                "Lil Anne",
                "anne@email.com",
                17,
                new Address("al. Jaros 1289", "93-868, Jawor")
        );
    }

    public static Person personWithInvalidEmailFormat() {
        return new Person(
                "Dad John",
                "xxx.xxx.xx",
                60,
                new Address("ul. Strzelczyk 34512", "8-811, Krynki")
        );
    }

    public record Person(String name, String email, int age, Address livesAt) {
    }

    public record Address(String line1, String line2) {
    }
}