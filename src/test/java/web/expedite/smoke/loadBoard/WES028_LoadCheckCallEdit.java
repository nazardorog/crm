package web.expedite.smoke.loadBoard;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.refresh;

import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.NewLoad;
import utilsWeb.configWeb.GlobalLogin;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES028_LoadCheckCallEdit {
    // https://app.clickup.com/t/86991f1wg
    // Чек Колы - Редактирование

    @Test
    public void editCheckCall() {

        // Логин диспетчером
        GlobalLogin.login("exp_disp2");

        String proNumber = NewLoad.expedite();
        String zipCode = "33133";
        String autoComplete = "Coconut Grove, FL 33133";
        String noteText = "check call note";
        String editedNoteText = "edited check call note";
        String editedZipCode = "10002";
        String editedAutoComplete = "New York, NY 10002";
        
        $(".logo-mini-icon").shouldBe(visible, EXPECT_GLOBAL);

        // Проверка наличия созданного груза
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible, EXPECT_GLOBAL).setValue(proNumber).pressEnter();
        $("td.our_pro_number").shouldHave(text(proNumber), EXPECT_GLOBAL);

        // Открытие модалки создания чек колла, заполнение полей
        $(".view_load.btn.btn-xs").shouldBe(visible, EXPECT_5).click();
        $("a.check_call_load.link-checkcallload-style").shouldBe(visible, EXPECT_5).click();
        $("#loadnotes-location").shouldBe(visible, EXPECT_5).setValue(zipCode);
        $("#loadnotes-note").shouldBe(visible).setValue(noteText);
        $("a.btn.btn-success.apply_gmail_data").shouldBe(visible, EXPECT_5).click();
        
        // Проверка автозаполнения локации
        $("#loadnotes-location").shouldHave(value(autoComplete), EXPECT_5);

        // Кнопка Submit
        $("#check_call_load_send").click();

        // Проверка введенного текста при создании чек колла в Dispatch
        $("td.check_call_location", 0).shouldHave(text(autoComplete), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldHave(text(noteText));

        // Чек колл алерт
        // Редактирование чек колла
        $("td.check_call_btns a.update_load_note", 0).shouldBe(visible, EXPECT_GLOBAL).click();
        $("#loadnotes-location").shouldBe(visible, EXPECT_5).setValue(editedZipCode);
        $("#loadnotes-note").shouldBe(visible).setValue(editedNoteText);
        $("a.btn.btn-success.apply_gmail_data").shouldBe(visible, EXPECT_5).click();
        $("input#loadnotes-alert").click();

        // Проверка автозаполнения локации
        $("#loadnotes-location").shouldHave(value(editedAutoComplete), EXPECT_5);

        // Кнопка Submit
        $("#update_load_note_send").click();

        // Проверка введенного текста при редактировании чек колла в Dispatch
        $("td.check_call_location", 0).shouldHave(text(editedAutoComplete), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldHave(text(editedNoteText));
        
        // Проверка введенного текста при редактировании чек колла на LoadBoard
        refresh();
        $(".pull-left.text-blue").shouldHave(text(editedAutoComplete), EXPECT_GLOBAL);
        $(".small-txt.text-alert", 0).shouldHave(text(editedNoteText));

        // Обычный чек колл
        // Редактирование чек колла
        $(".view_load.btn.btn-xs").shouldBe(visible).click();
        $("td.check_call_btns a.update_load_note", 0).shouldBe(visible, EXPECT_GLOBAL).click();
        $("#loadnotes-location").shouldBe(visible, EXPECT_5).setValue(zipCode);
        $("#loadnotes-note").shouldBe(visible).setValue(noteText);
        $("a.btn.btn-success.apply_gmail_data").shouldBe(visible, EXPECT_5).click();
        $("input#loadnotes-alert").click();

        // Проверка автозаполнения локации
        $("#loadnotes-location").shouldHave(value(autoComplete), EXPECT_5);

        // Кнопка Submit
        $("#update_load_note_send").click();

        // Проверка введенного текста при редактировании чек колла в Dispatch
        $("td.check_call_location", 0).shouldHave(text(autoComplete), EXPECT_GLOBAL);
        $("td.check_call_note", 0).shouldHave(text(noteText));
        
        // Проверка введенного текста при редактировании чек колла на LoadBoard
        refresh();
        $(".pull-left.text-blue").shouldHave(text(autoComplete), EXPECT_GLOBAL);
        $(".small-txt.text-green", 0).shouldHave(text(noteText));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }

}
