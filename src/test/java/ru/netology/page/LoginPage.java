package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginPage {

    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("input[name='password']");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $(".notification__content");

    public LoginPage openPage() {
        open("http://localhost:9999");
        return this;
    }

    public void login(String login, String password) {
        loginField.shouldBe(visible).setValue(login);
        passwordField.shouldBe(visible).setValue(password);
        loginButton.shouldBe(visible).click();
    }

    public void checkError(String expectedText) {
        errorNotification.shouldBe(visible)
                .shouldHave(text(expectedText));
    }

    public void checkSuccess() {

        errorNotification.shouldNotBe(visible);
    }
}