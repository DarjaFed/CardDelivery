package ru.netology.web;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;
import static com.codeborne.selenide.Condition.text;

public class CardDeliveryNegativeTest {

    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999/");

    }
// Пустое поле города
    @Test
    public void shouldReturnErrorMessageIfCityEmpty() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //Город не в юрисдикции РФ
    @Test
    public void shouldReturnErrorMessageIfCityInvalid() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Марокко");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    // Англ.буквы вместо телефона
    @Test
    public void shouldReturnErrorMessageIfPhoneWithLetters() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("Fgh");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    // Пустое поле Дата встречи
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameIsEmpty() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] [class='input__box'] [placeholder='Дата встречи']").setValue("");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Неверно введена дата"));
    }

    // Имя английскими буквами
    @Test
    public void shouldReturnErrorMessageIfInvalidSurnameAndName() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Имя спецсимволами
    @Test
    public void shouldReturnErrorMessageIfSurnameAndNameConsistsOfNumbers() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("#$#%!");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Пустое поле телефона
    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    //Телефон без +
    @Test
    public void shouldReturnErrorMessageIfPhoneIsWrong() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }
    //Без галочки согласия на обработку
    @Test
    public void shouldReturnErrorMessageIfDoNotTick() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").shouldBe(visible);
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}
