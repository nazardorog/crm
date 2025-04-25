package web.config;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import static com.codeborne.selenide.Selenide.$;

public class LoginBigTruck {

    @Description("Авторизація користувача в системі")
    public static void loginWeb() throws InterruptedException {
        Allure.step("Авторизація користувача", () -> {
            Allure.step("Вводить логін", () ->
                    $("#loginform-username").setValue("auto2t"));

            Allure.step("Вводить пароль", () ->
                    $("#loginform-password").setValue("sxLM8Gi0T76o"));

            Allure.step("Клік по кнопці Submit", () ->
                    $(".btn.btn-primary.btn-block.btn-flat").click());
        });
    }
}
