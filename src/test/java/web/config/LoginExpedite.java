package web.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import static com.codeborne.selenide.Selenide.$;

public class LoginExpedite {

    public static String webSite = "https://preprod.empirenational.com/adm";

    @Description("Авторизація користувача в системі")
    public static void loginWeb() throws InterruptedException {
        Allure.step("Авторизація користувача", () -> {
            Allure.step("Вводить логін", () ->
                    $("#loginform-username").setValue("test1te"));

            Allure.step("Вводить пароль", () ->
                    $("#loginform-password").setValue("t34n2215P391"));

            Allure.step("Клік по кнопці Submit", () ->
                    $(".btn.btn-primary.btn-block.btn-flat").click());
        });
    }
}
