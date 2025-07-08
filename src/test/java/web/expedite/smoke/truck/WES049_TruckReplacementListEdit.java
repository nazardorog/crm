package web.expedite.smoke.truck;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.*;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES049_TruckReplacementListEdit {

    // Click Up:
    // CRM EXPEDITE - Smoke - Trucks
    // 4. Trucks \ replacement list. 3. Update

    // Global data
    WCD001_Driver driverCreate = new WCD001_Driver();
    WCD002_Truck truckCreate = new WCD002_Truck();
    String globalName = GlobalGenerateName.globalName();

    @Test
    public void replacementListEdit() {

        //Create Truck. Create Driver
        WCD001_Driver newDriver = driverCreate.create();
        WCD002_Truck newTruck = truckCreate.create();

        // Login
        GlobalLogin.login("exp_hr");

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Table. Choose new Drivers
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(newDriver.atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("All");
        SelenideElement rowTableDrivers = $$("table.table-striped tbody tr").get(0);
        rowTableDrivers.shouldHave(text(newDriver.atFirstName));
        rowTableDrivers.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Update driver] Tab General. Input Truck to the Driver
        $("#update_driver").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-trucks_id-update-container").click();
        $(".select2-search--dropdown > input").setValue(newTruck.atTruckNumber);
        $$("li.select2-results__option").findBy(text(newTruck.atTruckNumber)).click();
        $("#select2-trucks_id-update-container").shouldHave(text(newTruck.atTruckNumber));

        // [Update driver] Button Submit
        $("#update_driver_send").shouldBe(clickable).click();
        $("#update_driver").shouldNotBe(visible, EXPECT_GLOBAL);

        // Check toast message
        Message.checkToast("Driver sucessfully updated");

        // [Main Drivers] Table. Check new Drivers
        SelenideElement rowTableDriversAfterEdit = $$("table.table-striped tbody tr").get(0);
        rowTableDriversAfterEdit.shouldHave(text(newDriver.atFirstName));

        // [Main Drivers] Table. Right menu choose Profile
        rowTableDriversAfterEdit.$("button.dropdown-toggle").shouldBe(clickable).click();
        rowTableDriversAfterEdit.$(".btn-group").shouldHave(cssClass("open"), EXPECT_GLOBAL);
        rowTableDriversAfterEdit.$$(".dropdown-menu-right li").findBy(text("Profile")).shouldBe(enabled).click();

        // [Drivers Profile]. Button Left
        $(".profile-personal-user-name").shouldBe(visible, EXPECT_GLOBAL);
        $(".profile-personal-user-name").shouldHave(text(newDriver.atFirstName));
        $("#driver_left").click();

        // [Driver's leaving]. Input Is Fired
        $("#driver_leaving").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks_contract_termination_type_driver").click();

        // [Alert] message move to blacklist
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Attention. Selected drivers will move to blacklist");
        switchTo().alert().accept();

        // [Driver's leaving]. Input Is Fired. Button Submit
        $("#drivers-blacklist_reason").setValue("Firing reason");
        $("#driver_leaving_submit").click();
        $("#driver_leaving").shouldNotBe(visible, EXPECT_GLOBAL);

        // Check toast message
        Message.checkToast("Driver successfully left");

        // [Sidebar] Go to Main Trucks
        $(".trucks-user").hover();
        $(".trucks-user").click();
        $("body").click();

        // [Update driver] Button Replacement List
        $(byText("Replacement List")).click();

        // [Replacement List] Table.
        $("input[name='TrucksSearch[truck_number]']").shouldBe(visible).setValue(newTruck.atTruckNumber).pressEnter();
        SelenideElement rowTableReplacementList = $$("table.table-striped tbody tr").get(0).shouldHave(text(newTruck.atTruckNumber), EXPECT_GLOBAL);
        rowTableReplacementList.shouldHave(text(newTruck.atTruckNumber));

        // [Update truck] Edit Trucks Replacement List
        rowTableReplacementList.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();
        $("#update_truck").shouldBe(visible, EXPECT_GLOBAL);

        // Data for edit a Trucks
        final String atEditModel = globalName + "Model2";
        final String atEditColor = globalName + "Color2";
        final String atEditPlateNumber = globalName + "Plate Number2";
        final String atEditYear = "2026";
        final String atEditTypeTruck = "Cargo Van";
        final String atEditMake = globalName + "Make2";
        final String atEditPayload = "20000";
        final String atEditOwner = "Autotest 5 Owner";
        final String atEditDimensionsL = "200";
        final String atEditDimensionsW = "200";
        final String atEditDimensionsH = "200";
        final String atEditCity = "San Antonio";
        final String atEditState = "TX";
        final String atEditZip = "78229";
        final String atEditStatus = "Not Available";
        final String atEditNote = globalName + "Note2";
        final String atEditFile = "jpeg2.jpg";
        final String atEditDescription = "Description2";

        // [Update Trucks] Tab General. Input data
        $("#trucks-model").setValue(atEditModel);
        $("#trucks-color").setValue(atEditColor);
        $("#trucks-plate_number").setValue(atEditPlateNumber);
        $("#trucks-year").setValue(atEditYear);
        $("#trucks-type_truck").selectOption(atEditTypeTruck);
        $("#trucks-make").setValue(atEditMake);
        $("#trucks-payload").setValue(atEditPayload);
        $("#trucks-status").selectOption(atEditStatus);
        $("#trucks-note").setValue(atEditNote);

        // [Update Trucks] Tab General. Owner input data
        $("#select2-owner_id-update-container .select2-selection__clear").click();
        $("#select2-owner_id-update-container").click();
        $(".select2-search__field").setValue("Auto");
        $$("li.select2-results__option").findBy(text(atEditOwner)).click();

        // [Update Trucks] Tab General. Dimensions input data
        $("#trucks-length").setValue(atEditDimensionsL);
        $("#trucks-width").setValue(atEditDimensionsW);
        $("#trucks-height").setValue(atEditDimensionsH);

        // [Update Trucks] Tab General. Zip input data
        $("#trucks-last_zip").setValue(atEditZip);
        $("#zipFillBtn").click();
        $("#zipFillBtn").shouldBe(enabled, EXPECT_GLOBAL);
        $("#trucks-last_city").shouldHave(value(atEditCity));
        $("#trucks-last_state").shouldHave(value(atEditState));

        // [Update Trucks] Tab General. Calendar Date When Will Be There
        $("#trucks-date_when_will_be_there-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // [Update Trucks] Tab General. Calendar Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(2);

        // [Update Trucks] Tab Documents. Download file.
        File editFile = new File(downloadsFolder + atEditFile);
        $$(".nav-tabs li").findBy(text("Documents")).click();
        $("button.add-document").click();
        $(".file-preview ").shouldBe(visible, EXPECT_GLOBAL);
        $("#truckdocuments-0-file").uploadFile(editFile);
        $("#truckdocuments-0-description").setValue(atEditDescription);

        // [Update Trucks] Button Submit
        $("#update_truck_send").shouldBe(clickable).click();
        $("#update_truck").shouldNotBe(visible, EXPECT_GLOBAL);
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }

}
