package ru.netology.web;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static java.time.Duration.ofSeconds;
import static com.codeborne.selenide.Condition.text;

public class CardDeliveryNegativeTest {

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");

    }

    private String generateDate(long addDays, String pattern) {
        return LocalDate.now()
                .plusDays(addDays)
                .format(DateTimeFormatter.ofPattern(pattern));
    }


    // Пустое поле города
    @Test
    public void noCityName() {
        $("[data-test-id=city] input").setValue("");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }


    //Город не в юрисдикции РФ
    @Test
    public void cityInvalid() {
        $("[data-test-id=city] input").setValue("Марокко");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='city'].input_invalid .input__sub").shouldBe(visible)
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    // буквы вместо телефона
    @Test
    public void phoneNumberLetters() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("Вав");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='phone'].input_invalid .input__sub").shouldBe(visible)
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    // Пустое поле Дата встречи
    @Test
    public void dateClean() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(3, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79111111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='date'] .input__sub").shouldBe(visible)
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Неверно введена дата"));




    }
    @Test
            public void invalidDate() {
    $("[data-test-id=city] input").setValue("Москва");
    String planningDate = generateDate(33462, "dd.MM.yyyy");
    $("[data-test-id='date'] input")
            .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
            .setValue(planningDate);
    $("[data-test-id='name'] input").setValue("Иван Иванов");
    $("[data-test-id='phone'] input").setValue("+79111111111");
    $("[data-test-id='agreement']").click();
    $("button.button").click();
    $(".notification__content");
    $("[data-test-id='date'] .input__sub").shouldBe(visible)
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Неверно введена дата"));
    }

    // Имя английскими буквами
    @Test
    public void invalidName() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Ivan Ivanov");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='name'].input_invalid .input__sub")
                .shouldHave(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Имя спецсимволами
    @Test
    public void simbolsName() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("!@@!");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='name'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    // Пустое поле телефона
    @Test
    public void cleanPhone() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='phone'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Поле обязательно для заполнения"));
    }

    //Телефон без +
    @Test
    public void invalidePhone() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("79811111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='phone'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //Без галочки согласия на обработку
    @Test
    public void checkboxOff() {
        $("[data-test-id=city] input").setValue("Москва");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Иван Иванов");
        $("[data-test-id='phone'] input").setValue("+79811111111");
        $("[data-test-id='agreement']").doubleClick();
        $("button.button").click();
        $(".notification__content");
        $("[data-test-id='agreement'].input_invalid .input__sub")
                .should(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Требуется согласие с условиями обработки и использованием моих персональных данных"));
    }
}