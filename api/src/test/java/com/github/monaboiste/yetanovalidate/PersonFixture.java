package com.github.monaboiste.yetanovalidate;

record PersonFixture(String name, String email, int age, Address livesAt) {

    static PersonFixture person() {
        return new PersonFixture(
                "Johnie",
                "johndoe@email.com",
                23,
                new Address("ul. Lipka 756", "68-994, Parczew")
        );
    }

    static PersonFixture underagePerson() {
        return new PersonFixture(
                "Lil Anne",
                "anne@email.com",
                17,
                new Address("al. Jaros 1289", "93-868, Jawor")
        );
    }

    static PersonFixture personWithInvalidEmailFormat() {
        return new PersonFixture(
                "Dad John",
                "xxx.xxx.xx",
                60,
                new Address("ul. Strzelczyk 34512", "8-811, Krynki")
        );
    }

    record Address(String line1, String line2) {
    }
}