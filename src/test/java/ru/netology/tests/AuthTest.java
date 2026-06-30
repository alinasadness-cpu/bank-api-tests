package ru.netology.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;
import ru.netology.data.UserGenerator;
import ru.netology.data.UserStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest {
    private static RequestSpecification requestSpec;

    @BeforeAll
    static void setUpAll() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(9999)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

  

    @Test
    void shouldCreateAndLoginActiveUser() {
        RegistrationDto user = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(user);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(200)
                .body("status", equalTo(UserStatus.ACTIVE.getValue()));
    }

    @Test
    void shouldRecreateUserWithDifferentStatus() {
        RegistrationDto user = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(user);

        RegistrationDto updatedUser = new RegistrationDto(
                user.getLogin(),
                user.getPassword(),
                UserStatus.BLOCKED.getValue()
        );
        UserGenerator.registerUser(updatedUser);

        given()
                .spec(requestSpec)
                .body(updatedUser)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(403);
    }

 

    @Test
    void shouldNotLoginBlockedUser() {
        RegistrationDto user = UserGenerator.generateBlockedUser();
        UserGenerator.registerUser(user);

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(403)
                .body("message", containsStringIgnoringCase("заблокирован"));
    }

    @Test
    void shouldNotLoginWithEmptyLogin() {
        RegistrationDto user = UserGenerator.generateUserWithEmptyLogin();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotLoginWithEmptyPassword() {
        RegistrationDto user = UserGenerator.generateUserWithEmptyPassword();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotLoginWithInvalidLogin() {
        RegistrationDto validUser = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(validUser);

        RegistrationDto invalidLoginUser = new RegistrationDto(
                "wrong_login",
                validUser.getPassword(),
                UserStatus.ACTIVE.getValue()
        );

        given()
                .spec(requestSpec)
                .body(invalidLoginUser)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldNotLoginWithInvalidPassword() {
        RegistrationDto validUser = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(validUser);

        RegistrationDto invalidPasswordUser = new RegistrationDto(
                validUser.getLogin(),
                "wrong_password",
                UserStatus.ACTIVE.getValue()
        );

        given()
                .spec(requestSpec)
                .body(invalidPasswordUser)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(401);
    }

    @Test
    void shouldNotLoginNonExistentUser() {
        RegistrationDto nonExistentUser = UserGenerator.generateActiveUser();

        given()
                .spec(requestSpec)
                .body(nonExistentUser)
                .when()
                .post("/api/auth")
                .then()
                .statusCode(401);
    }


    @Test
    void shouldNotCreateUserWithShortLogin() {
        RegistrationDto user = UserGenerator.generateUserWithShortLogin();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(400);
    }

    @Test
    void shouldNotCreateUserWithShortPassword() {
        RegistrationDto user = UserGenerator.generateUserWithShortPassword();

        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(400);
    }
}
