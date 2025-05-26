package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.DownloadDocument;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES032_LoadGetInvoice {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 21. Actions - Get Invoice

    @Test
    public void getInvoice() throws IOException {

        // Login. Create new load expedite
        GlobalLogin.login("exp_disp1");
        String loadNumber = NewLoadExpedite.loadExpedite();

        // Data for test
        final String atFileName = "dompdf_out.pdf";

        // По номеру вантажу в меню вибирає Get load confirmation
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(loadNumber).pressEnter();
        SelenideElement rowFind = $$("table.table-striped tbody tr").get(0).shouldHave(text(loadNumber));
        ElementsCollection dropdown = rowFind.$$(".dropdown-menu-right li");
        rowFind.$("td a.view_load").shouldHave(text(loadNumber), EXPECT_5);
        rowFind.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowFind.$(".btn-group").shouldHave(Condition.cssClass("open"),EXPECT_GLOBAL);
        dropdown.findBy(exactText("Get BOL")).click();

        // Перевіряє прев"ю
        $("#view_pdf").shouldBe(visible, EXPECT_GLOBAL);
        $(".name-bill-lading").shouldHave(text("BILL OF LADING  "));
        $("input[name ='BILL_OF_LADING_NUMBER']").shouldHave(value(loadNumber));
        $("input[name ='PickUpLocation[name]']").shouldHave(value("Auto test shipper 1"));
        $("input[name ='PickUpLocation[street1]']").shouldHave(value("Moornings Way"));
        $("input[name ='PickUpLocation[city_state_zip]']").shouldHave(value("Kansas City, MO 64110"));

        $("input[name ='DeliveryLocation[name]']").shouldHave(value("Auto test shipper 2"));
        $("input[name ='DeliveryLocation[street1]']").shouldHave(value("Cherry st"));
        $("input[name ='DeliveryLocation[city_state_zip]']").shouldHave(value("New York, NY 10002"));

        // Завантажує документ на пк чекає завантаження 10 секунд
        DownloadDocument.document(atFileName);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
