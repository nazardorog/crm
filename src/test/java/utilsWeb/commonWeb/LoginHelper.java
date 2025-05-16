package utilsWeb.commonWeb;

import utilsWeb.configWeb.GlobalConfig;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class LoginHelper {

    @Description("Авторизація користувача в системі")
    public static void login() {
        GlobalConfig.setCredentials();

        Allure.step("Авторизація користувача", () -> {
            Allure.step("Вводить логін", () ->
                    $("#loginform-username").setValue(GlobalConfig.USERNAME));

            Allure.step("Вводить пароль", () ->
                    $("#loginform-password").setValue(GlobalConfig.PASSWORD));

            Allure.step("Клік по кнопці Submit", () ->
                    $(".btn.btn-primary.btn-block.btn-flat").click());

            Allure.step("Перевіряє закриття фрейм Login", () ->
                    $(".login-page").shouldNotBe(visible, EXPECT_5));
        });
    }
}
