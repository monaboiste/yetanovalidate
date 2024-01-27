# Yet Another Validation Library
### Prerequisites
- Java 21

### Why?
The project is heavily inspired by the AssertJ library. I just wanted to test out the idea of building the fluent API.

### Are you going to maintain this library?
Nope. I made the project on a whim to test some ideas. I don't expect me going back here really.

### Where are the artefacts?
The artefacts aren't published anywhere. The only way to use the library is compiling it yourself:
```shell
./gradlew build publishToMavenLocal
```
Applying:
```groovy
implementation("com.github.monaboiste.yetanovalidate:api:${version}")
```

### Example usage:
```java
throwWhen(underagePerson)                                  // object instance to assert
    .extracting(Person::age)                               // optionally extract the nested attribute
    .doesNotSatisfy(isAdult())                             // provide a predicate to the instance against
    .withField("age")                                      // optionally provide the name of the field
    .withMessage("You're too young to drink an alcohol!")  // optionally provide the error message
    .withCode("illegal_age")                               // optionally provide the error code
    .withExceptionTypeOf(CannotBuyAlcoholException.class); // the type of your custom exception to throw if the object didn't pass the checks
```
The exception will contain the metadata:
```java
ex.ruleViolation() // caught exception will contain provided field, code and the error message
```

See [more examples](./api/src/test/java/com/github/monaboiste/yetanovalidate/ValidationExceptionTest.java).