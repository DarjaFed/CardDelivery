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

public class CardDeliveryPositiveTest {


    @BeforeEach
    public void setUp() {
        Selenide.open("http://localhost:9999/");

    }

    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + dateText));
    }

    @Test
    public void shouldReturnSuccessfullyIfSurnameWithHyphen() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов-Смирнов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + dateText));
    }

    @Test
    public void shouldReturnSuccessfullyIfCityWithHyphen() {
        LocalDate deliveryDateCard = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String dateText = deliveryDateCard.format(formatter);
        $("[data-test-id=city] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input");
        $("[data-test-id='name'] input").setValue("Иван Иванов-Смирнов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + dateText));
    }
}