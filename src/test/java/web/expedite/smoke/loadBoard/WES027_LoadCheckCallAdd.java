package web.expedite.smoke.loadBoard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES027_LoadCheckCallAdd {
    // https://app.clickup.com/t/86991f1hp
    // Чек Колы - Создание

    @Test
    public void createCheckCall() {

        // Логин диспетчером
        GlobalLogin.login("exp_disp2");

        String proNumber = NewLoad.expedite();
        String zipCode1 = "33133";
        String autoComplete1 = "Coconut Grove, FL 33133";
        String noteText1 = "check call note";
        String noteText2 = "alert check call note";
        String zipCode2 = "10002";
        String autoComplete2 = "New York, NY 10002";
        
        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        // Проверка наличия созданного груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_GLOBAL);

        // Обычный чек колл
        // Открытие модалки создания чек колла, заполнение полей
        $(".view_load.btn.btn-xs").shouldBe(visible, EXPECT_5).click();
        $("a.check_call_load.link-checkcallload-style").shouldBe(visible, EXPECT_5).click();
        $("#loadnotes-location").shouldBe(visible, EXPECT_5).setValue(zipCode1);
        $("#loadnotes-note").shouldBe(visible).setValue(noteText1);
        $("a.btn.btn-success.apply_gmail_data").shouldBe(visible, EXPECT_5).click();
        
        // Проверка автозаполнения локации
        $("#loadnotes-location").shouldHave(value(autoComplete1), EXPECT_5);

        // Кнопка Submit
        $("#check_call_load_send").click();

        // Проверка введенного текста при создании чек колла в Dispatch
        $("td.check_call_location", 0).shouldHave(text(autoComplete1), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldHave(text(noteText1));

        // Проверка введенного текста при создании чек колла на LoadBoard
        $(".close").click();
        $(".pull-left.text-blue").shouldHave(text(autoComplete1), EXPECT_GLOBAL);
        $(".small-txt.text-green", 0).shouldHave(text(noteText1));

        // Чек колл алерт
        // Открытие модалки создания чек колла, заполнение полей
        $(".view_load.btn.btn-xs").shouldBe(visible, EXPECT_5).click();
        $("a.check_call_load.link-checkcallload-style").shouldBe(visible, EXPECT_5).click();
        $("#loadnotes-location").shouldBe(visible, EXPECT_5).setValue(zipCode2);
        $("#loadnotes-note").shouldBe(visible).setValue(noteText2);
        $("a.btn.btn-success.apply_gmail_data").shouldBe(visible, EXPECT_5).click();
        $("input#loadnotes-alert").click();
        
        // Проверка автозаполнения локации
        $("#loadnotes-location").shouldHave(value(autoComplete2), EXPECT_5);

        // Кнопка Submit
        $("#check_call_load_send").click();

        // Проверка введенного текста при создании чек колла в Dispatch
        $("td.check_call_location", 0).shouldHave(text(autoComplete2), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldHave(text(noteText2));

        // Проверка введенного текста при создании чек колла на LoadBoard
        $(".close").click();
        $(".pull-left.text-blue").shouldHave(text(autoComplete2), EXPECT_GLOBAL);
        $(".small-txt.text-alert", 0).shouldHave(text(noteText2));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }

}
