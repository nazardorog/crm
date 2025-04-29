package web.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginExpedite {

    public static String webSite = "https://preprod.empirenational.com/adm";

    @Description("Авторизація користувача в системі")
    public static void loginWeb() throws InterruptedException {
        Allure.step("Авторизація користувача", () -> {
            Allure.step("Вводить логін", () ->
                    $("#loginform-username").shouldBe(visible, Duration.ofSeconds(10)).setValue("test1te"));

            Allure.step("Вводить пароль", () ->
                    $("#loginform-password").setValue("t34n2215P392"));

            Allure.step("Клік по кнопці Submit", () ->
                    $(".btn.btn-primary.btn-block.btn-flat").click());
        });
    }
}
