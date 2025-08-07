package web.expedite.smoke.shipperReceiver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES060_SRDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Shippers-receivers
    // 4. Удаление Shippers Receivers

    // Global data
    WCE003_ShipperReceiver shipperReceiver = new WCE003_ShipperReceiver();

    @Test
    public void dell() {

        //Create Shipper Receiver
        WCE003_ShipperReceiver newShipperReceiver = shipperReceiver.create();

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for edit a Shipper Receiver
        final String atName = newShipperReceiver.atName;

        // [Sidebar] Go to Main Shipper Receiver
        $(".shippers-receivers-user").hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        // [Main Shipper Receiver] Table
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atName).pressEnter();
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(atName), EXPECT_GLOBAL);

        // [Main Shipper Receiver] Table. Choose remove
        rowTable.shouldHave(text(atName));
        rowTable.$(".glyphicon-remove").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Alert] Accept message
        Message.acceptAlert("Are you sure you want to delete this shipper receiver?");

        // [Toast] Check message
        Message.checkToast("Shipper Receivers deleted successfully");

        // [Main Shipper Receiver] Table. Check delete Shipper Receiver
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atName).pressEnter();
        SelenideElement rowTableAfterDell = $$("table.table-striped tbody tr").get(0);
        rowTableAfterDell.shouldHave(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
