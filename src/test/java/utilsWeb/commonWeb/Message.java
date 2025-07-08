package utilsWeb.commonWeb;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.ex.ElementNotFound;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;
import static org.assertj.core.api.Assertions.assertThat;

public class Message {

    public static void dellToast() {
        try {
            SelenideElement toast = $("#toast-container");
            toast.should(exist, Duration.ofSeconds(1));
            executeJavaScript("arguments[0].remove();", toast);
        } catch (ElementNotFound e) {
            // якщо toast не з'явився, йде далі без помилки
        }
    }

    public static void checkToast(String massage) {
        try {
            // Toast massage
            $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
            $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text(massage));
            $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);
        } catch (ElementNotFound e) {
            // якщо toast не з'явився, йде далі без помилки
        }
    }

    public static void acceptAlert(String massage) {
        // [Alert] Accept message
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo(massage);
        switchTo().alert().accept();
    }



}
