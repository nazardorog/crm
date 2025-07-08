package web.expedite.smoke.truck;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES044_TruckCreate {

    // Click Up:
    // CRM EXPEDITE - Smoke - Trucks
    // 1. Создание New truck

    // Global data
    String globalNumberSeventeen = "1234567890" + GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();

    @Test
    public void create() {

        // Login
        GlobalLogin.login("exp_hr");

        // Data for creating a Truck
        final String atTruckNumber = globalName + "Truck Number";
        final String atVinNumber = globalNumberSeventeen;
        final String atModel = globalName + "Model";
        final String atColor = globalName + "Color";
        final String atPlateNumber = globalName + "Plate Number";
        final String atYear = "2025";
        final String atTypeTruck = "Sprinter Van";
        final String atMake = globalName + "Make";
        final String atPayload = "10000";
        final String atOwner = "Autotest 3 Owner";
        final String atDimensionsL = "100";
        final String atDimensionsW = "100";
        final String atDimensionsH = "100";
        final String atCity = "Kansas City";
        final String atState = "KS";
        final String atZip = "66105";
        final String atStatus = "Available On";
        final String atNote = globalName + "Note";
        final String atFile = "jpeg1.jpg";
        final String atDescription = "Description";

        // [Sidebar] Go to Main Trucks
        $(".trucks-user").shouldBe(visible, EXPECT_GLOBAL).hover();
        $(".trucks-user").click();
        $("body").click();

        // [Main Trucks] Button New Trucks
        $("#new_truck").shouldBe(visible).click();
        $("#add_truck").shouldBe(visible, EXPECT_GLOBAL);

        // [Add Trucks] Tab General. Input data
        $("#trucks-truck_number").setValue(atTruckNumber);
        $("#trucks-model").setValue(atModel);
        $("#trucks-vin_number").setValue(atVinNumber);
        $("#trucks-color").setValue(atColor);
        $("#trucks-plate_number").setValue(atPlateNumber);
        $("#trucks-year").setValue(atYear);
        $("#trucks-type_truck").selectOption(atTypeTruck);
        $("#trucks-make").setValue(atMake);
        $("#trucks-payload").setValue(atPayload);
        $("#trucks-status").selectOption(atStatus);
        $("#trucks-note").setValue(atNote);

        // [Add Trucks] Tab General. Owner input data
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$("li.select2-results__option").findBy(text(atOwner)).click();

        // [Add Trucks] Tab General. Dimensions input data
        $("#trucks-length").setValue(atDimensionsL);
        $("#trucks-width").setValue(atDimensionsW);
        $("#trucks-height").setValue(atDimensionsH);

        // [Add Trucks] Tab General. Zip input data
        $("#trucks-last_zip").setValue(atZip);
        $("#zipFillBtn").click();
        $("#zipFillBtn").shouldBe(enabled, EXPECT_GLOBAL);
        $("#trucks-last_city").shouldHave(value(atCity));
        $("#trucks-last_state").shouldHave(value(atState));

        // [Add Trucks] Tab General. Calendar Date When Will Be There
        $("#trucks-date_when_will_be_there-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // [Add Trucks] Tab General. Calendar Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(1);

        // [Add Trucks] Tab Documents. Download file.
        File file = new File(downloadsFolder + atFile);
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.add-document").click();
        $(".file-preview ").shouldBe(visible, EXPECT_GLOBAL);
        $("#truckdocuments-0-file").uploadFile(file);
        $("#truckdocuments-0-description").setValue(atDescription);

        // [Add Trucks] Button Submit
        $("#add_truck_send").shouldBe(clickable).click();
        $("#add_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        // Toast massage
        $("#toast-container").shouldBe(visible, EXPECT_GLOBAL);
        $(".toast-message").shouldHave(visible, EXPECT_GLOBAL).shouldHave(text("Truck successfully added"));
        $("#toast-container").shouldNotHave(visible, EXPECT_GLOBAL);

        // [Main Trucks] Table. Input Vin Number. Select "Without Unit", "Person"
        $("input[name='TrucksSearch[vin_number]']").shouldBe(visible).setValue(atVinNumber);
        $("#truckssearch-truck_number_length").selectOption("All");
        $("#truckssearch-type_truck").selectOption(atTypeTruck);
        $("#truckssearch-truck_number_length").getSelectedOption().shouldHave(text("All"));
        $("#truckssearch-type_truck").getSelectedOption().shouldHave(text(atTypeTruck));
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0);

        // [Main Trucks] Table. Check new Trucks
        rowTable.shouldHave(text(atVinNumber));
        rowTable.shouldHave(text(atPlateNumber));
        rowTable.shouldHave(text(atMake));
        rowTable.shouldHave(text(atModel));
        rowTable.shouldHave(text(atStatus));
        rowTable.shouldHave(text(atTypeTruck));

        // [View truck] Check data New truck
        rowTable.$(".glyphicon-eye-open").click();
        $("#view_truck").shouldBe(visible, EXPECT_GLOBAL);
        $$("table#w0 tr").findBy(text("Vin Number"))        .$$("td").first().shouldHave(text(atVinNumber));
        $$("table#w0 tr").findBy(text("Plate Number"))      .$$("td").first().shouldHave(text(atPlateNumber));
        $$("table#w0 tr").findBy(text("Make"))              .$$("td").first().shouldHave(text(atMake));
        $$("table#w0 tr").findBy(text("Model"))             .$$("td").first().shouldHave(text(atModel));
        $$("table#w0 tr").findBy(text("Type Truck"))        .$$("td").first().shouldHave(text(atTypeTruck));
        $$("table#w0 tr").findBy(text("Year"))              .$$("td").first().shouldHave(text(atYear));
        $$("table#w0 tr").findBy(text("Updated By"))        .$$("td").first().shouldHave(text("System"));
        $$("table#w0 tr").findBy(text("Status Updated By")) .$$("td").first().shouldHave(text("Auto 2Test Hr"));
        $$("table#w0 tr").findBy(text("Note"))              .$$("td").first().shouldHave(text(atNote));

        // [View truck] Close View truck
        $("#view_truck button.close").click();
        $("#view_truck").shouldNotBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
