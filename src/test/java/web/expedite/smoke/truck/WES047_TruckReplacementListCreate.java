package web.expedite.smoke.truck;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES047_TruckReplacementListCreate {

    // Click Up:
    // CRM EXPEDITE - Smoke - Trucks
    // 4. Trucks \ replacement list. 1. Add truck (Create)

    // Global data
    WCD001_Driver driverCreate = new WCD001_Driver();
    WCD002_Truck truckCreate = new WCD002_Truck();

    @Test
    public void replacementListCreate() {

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
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}