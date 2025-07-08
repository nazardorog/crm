package web.expedite.smoke.shipperReceiver;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.WCD003_ShipperReceiver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES059_SREdit {

    // Click Up:
    // CRM EXPEDITE - Smoke - Shippers-receivers
    // 3. Редактирование Shippers Receivers

    // Global data
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();
    WCD003_ShipperReceiver shipperReceiver = new WCD003_ShipperReceiver();

    @Test
    public void edit() {

        //Create Shipper Receiver
        WCD003_ShipperReceiver newShipperReceiver = shipperReceiver.create();

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for edit a Shipper Receiver
        final String atName = newShipperReceiver.atName;
        final String atEditStreet1 = "Street 12";
        final String atEditStreet2 = "Street 22";
        final String atEditCountry = "US";
        final String atEditZip = "78229";
        final String atEditCity = "San Antonio";
        final String atEditState = "TX";
        final String atEditLocation = "San Antonio, TX 78229";
        final String atEditEmail = globalMail;
        final String atEditContactPersonName = "Contact Person Name2";
        final String atEditContactPersonPhoneNumber = globalPhoneNumber;
        final String atEditContactCellNumber = globalPhoneNumber;
        final String atEditNote = "Note2";
        final String atEditLat = "29";
        final String atEditLng = "-98";

        // [Sidebar] Go to Main Shipper Receiver
        $(".shippers-receivers-user").hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        // [Main Shipper Receiver] Table
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atName).pressEnter();
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(atName), EXPECT_GLOBAL);

        // [Main Shipper Receiver] Table. Choose Pencil
        rowTable.shouldHave(text(atName));
        rowTable.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Update Shipper Receiver] Input data
        $("#update_shippers-receiver").shouldBe(visible);
        $("#shippersreceivers-name").setValue(atName);
        $("#shippersreceivers-street1").setValue(atEditStreet1);
        $("#shippersreceivers-street2").setValue(atEditStreet2);
        $("#country_shippers_receivers").selectOption(atEditCountry);
        $("#shippersreceivers-location").setValue(atEditZip);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(text(atEditLocation)).click();
        $("#shippersreceivers-location").shouldHave(value(atEditLocation));
        $("#shippersreceivers-email").setValue(atEditEmail);
        $("#shippersreceivers-contact_person_name").setValue(atEditContactPersonName);
        $("#contact_person_phone_number-update").setValue(atEditContactPersonPhoneNumber);
        $("#shippersreceivers-contact_person_cell_number").setValue(atEditContactCellNumber);
        $("#shippersreceiversdetail-note").setValue(atEditNote);

        // [Update Shipper Receiver] Button Submit
        $("#update_shippers-receiver_send").click();
        $("#update_shippers-receiver").shouldNotBe(visible);

        // [Toast] Check message
        Message.checkToast("Shippers Receivers updated successfully");

        // [Main Shipper Receiver] Table
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atName).pressEnter();
        SelenideElement rowTableAfterEdit = $$("table.table-striped tbody tr").get(0).shouldHave(text(atName), EXPECT_GLOBAL);

        // [Main Shipper Receiver] Table. Check data
        rowTableAfterEdit.shouldHave(text(atName));
        rowTableAfterEdit.shouldHave(text(atEditLocation));
        rowTableAfterEdit.shouldHave(text(atEditStreet1));
        rowTableAfterEdit.shouldHave(text(atEditStreet2));
        rowTableAfterEdit.shouldHave(matchText(".*\\b" + atEditLat + "\\..*"));
        rowTableAfterEdit.shouldHave(matchText(".*\\" + atEditLng + "\\..*"));

        // [Shipper Receiver] View check data after update
        rowTableAfterEdit.$(".glyphicon-eye-open").click();
        $("#view_shippers-receiver").shouldBe(visible, EXPECT_GLOBAL);
        $$("table#w0 tr").findBy(text("Name"))                          .$$("td").first().shouldHave(text(atName));
        $$("table#w0 tr").findBy(text("Street 1"))                      .$$("td").first().shouldHave(text(atEditStreet1));
        $$("table#w0 tr").findBy(text("Street 2"))                      .$$("td").first().shouldHave(text(atEditStreet2));
        $$("table#w0 tr").findBy(text("City"))                          .$$("td").first().shouldHave(text(atEditCity));
        $$("table#w0 tr").findBy(text("State"))                         .$$("td").first().shouldHave(text(atEditState));
        $$("table#w0 tr").findBy(text("Zip"))                           .$$("td").first().shouldHave(text(atEditZip));
        $$("table#w0 tr").findBy(text("Location"))                      .$$("td").first().shouldHave(text(atEditLocation));
        $$("table#w0 tr").findBy(text("Lat"))                           .$$("td").first().shouldHave(matchText(".*\\b" + atEditLat + "\\..*"));
        $$("table#w0 tr").findBy(text("Lng"))                           .$$("td").first().shouldHave(matchText(".*\\" + atEditLng + "\\..*"));
        $$("table#w0 tr").findBy(text("Contact Person Name"))           .$$("td").first().shouldHave(text(atEditContactPersonName));
        $$("table#w0 tr").findBy(text("Contact Person Phone Number"))   .$$("td").first().shouldHave(text(atEditContactPersonPhoneNumber));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
