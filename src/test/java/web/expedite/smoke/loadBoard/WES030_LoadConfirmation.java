package web.expedite.smoke.loadBoard;

import com.codeborne.selenide.*;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.NewLoadExpedite;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import utilsWeb.commonWeb.*;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class WES030_LoadConfirmation {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loadboard
    // 19. Actions - Get Loads Confirmation

    @Test
    public void confirmation() throws IOException {

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
        dropdown.findBy(exactText("Get load confirmation")).click();

        // Перевіряє прев"ю
        $("#view_pdf").shouldBe(visible, EXPECT_GLOBAL);
        $(".modal-view-pdf .bootstrap-dialog-title").shouldHave(text("Load confirmation Trip#" + loadNumber));
        $(".logo-view-pdf-flex").shouldBe(visible, EXPECT_10);
        $(".Sheet .logo-view-pdf-flex").shouldHave(text("Empire National Inc 4600 Hendersonville Rd St. D_1 FLETCHER, NC 28732_1"));

        // Завантажує документ на пк чекає завантаження 10 секунд
        DownloadDocument.document(atFileName);
    }
}
