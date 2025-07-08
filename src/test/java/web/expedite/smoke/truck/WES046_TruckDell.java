package web.expedite.smoke.truck;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import utilsWeb.commonWeb.Calendar;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.configWeb.GlobalGenerateName;
import utilsWeb.configWeb.GlobalLogin;

import java.io.File;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Configuration.downloadsFolder;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.switchTo;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES046_TruckDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Trucks
    // 3. Удаление трака

    // Global data
    String globalNumberSeventeen = "1234567890" + GlobalGenerateName.globalNumberSeven();
    String globalName = GlobalGenerateName.globalName();

    @Test
    public void dell() {

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

        // [Main Trucks] Table. Delete Trucks
        rowTable.shouldHave(text(atVinNumber));
        rowTable.$("a[title='Delete']").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // [Alert] message delete
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to delete this item?");
        switchTo().alert().accept();

        // [Main Trucks] Table. Check Trucks after dell
        $("input[name='TrucksSearch[vin_number]']").shouldBe(visible).setValue(atVinNumber);
        $("#truckssearch-truck_number_length").selectOption("All");
        $("#truckssearch-type_truck").selectOption(atTypeTruck);
        $("#truckssearch-truck_number_length").getSelectedOption().shouldHave(text("All"));
        SelenideElement rowTableAfter = $$("table.table-striped tbody tr").get(0);
        rowTableAfter.shouldHave(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
