package web.expedite.smoke.loadBoard;

import static org.assertj.core.api.Assertions.assertThat;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.switchTo;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES029_LoadCheckCallDelete {
    // https://app.clickup.com/t/86991f23f
    // Чек Колы - Удаление

    @Test
    public void deleteCheckCall() {

        // Логин диспетчером
        GlobalLogin.login("exp_disp2");

        String proNumber = NewLoad.expedite();
        String zipCode1 = "33133";
        String autoComplete1 = "Coconut Grove, FL 33133";
        String noteText1 = "check call note";
        
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

        // Удаление чек колла
        $("td.check_call_btns a.delete_load_note", 0).shouldBe(visible, EXPECT_GLOBAL).click();

        // Подтверждение удаления
        String popapText1 = switchTo().alert().getText();
        assertThat(popapText1).isEqualTo("Are you sure you want to delete this load note?");
        switchTo().alert().accept();

        // Проверка отсутствия чек колла
        $("td.check_call_location", 0).shouldNotHave(text(autoComplete1), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldNotHave(text(noteText1));
        refresh();
        $$("tbody tr").findBy(text("Drivers' dispatched")).shouldBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }

}
