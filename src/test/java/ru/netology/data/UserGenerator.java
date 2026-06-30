package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class UserGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private UserGenerator() {
    }

    public static RegistrationDto generateActiveUser() {
        return new RegistrationDto(
                faker.name().username(),
                faker.internet().password(6, 12),
                UserStatus.ACTIVE.getValue()
        );
    }


    public static RegistrationDto generateBlockedUser() {
        return new RegistrationDto(
                faker.name().username(),
                faker.internet().password(6, 12),
                UserStatus.BLOCKED.getValue()
        );
    }


    public static RegistrationDto generateUserWithEmptyLogin() {
        return new RegistrationDto(
                "",
                faker.internet().password(6, 12),
                UserStatus.ACTIVE.getValue()
        );
    }


    public static RegistrationDto generateUserWithEmptyPassword() {
        return new RegistrationDto(
                faker.name().username(),
                "",
                UserStatus.ACTIVE.getValue()
        );
    }


    public static RegistrationDto generateUserWithShortLogin() {
        return new RegistrationDto(
                faker.lorem().characters(2),
                faker.internet().password(6, 12),
                UserStatus.ACTIVE.getValue()
        );
    }


    public static RegistrationDto generateUserWithShortPassword() {
        return new RegistrationDto(
                faker.name().username(),
                faker.lorem().characters(3),
                UserStatus.ACTIVE.getValue()
        );
    }


    public static void registerUser(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}