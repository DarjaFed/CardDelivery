package ru.netology.web;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static java.time.Duration.ofSeconds;
import static com.codeborne.selenide.Condition.text;

public class CardDeliveryPositiveTest {


    @BeforeEach
    void setUp() { Selenide.open("http://localhost:9999/");
        private String generateDate(long addDays, String pattern) {
            return LocalDate.now()
                    .plusDays(addDays)
                    .format(DateTimeFormatter.ofPattern(pattern));
        }
    }

    @Test
    public void shouldReturnSuccessIfFieldsAreFilledInCorrectly() {
        $("[data-test-id=city] input").setValue("Казань");
        String planningDate = generateDate();
        $("[data-test-id='date'] input")
                .press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Елена Иванова");
        $("[data-test-id='phone'] input").setValue("+79833349918");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .should(Condition.visible, Duration.ofSeconds(15));
                .should(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}

