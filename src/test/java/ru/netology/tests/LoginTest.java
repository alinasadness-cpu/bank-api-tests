package ru.netology.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;
import ru.netology.data.UserGenerator;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginActiveUser() {
        RegistrationDto user = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(user);

        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        loginPage.login(user.getLogin(), user.getPassword());
        loginPage.checkSuccess();
    }

    @Test
    void shouldNotLoginBlockedUser() {
        RegistrationDto user = UserGenerator.generateBlockedUser();
        UserGenerator.registerUser(user);

        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        loginPage.login(user.getLogin(), user.getPassword());
        loginPage.checkError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldNotLoginNonExistentUser() {
        RegistrationDto user = UserGenerator.generateActiveUser();

        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        loginPage.login(user.getLogin(), user.getPassword());
        loginPage.checkError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldNotLoginWithWrongPassword() {
        RegistrationDto user = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(user);

        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        loginPage.login(user.getLogin(), "wrong_password");
        loginPage.checkError("Ошибка! Неверно указан логин или пароль");
    }

    @Test
    void shouldNotLoginWithWrongLogin() {
        RegistrationDto user = UserGenerator.generateActiveUser();
        UserGenerator.registerUser(user);

        LoginPage loginPage = new LoginPage();
        loginPage.openPage();
        loginPage.login("wrong_login", user.getPassword());
        loginPage.checkError("Ошибка! Неверно указан логин или пароль");
    }
}
