package web.expedite.smoke.driver;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import utilsWeb.createDataExp.WCD001_Driver;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WES056_DriverDell {

    // Click Up:
    // CRM EXPEDITE - Smoke - Drivers
    // 3. Удаление driver

    // Global data
    WCD001_Driver driverCreate = new WCD001_Driver();

    @Test
    public void dell() {

        //Create Driver
        WCD001_Driver newDriver = driverCreate.create();

        // Login
        GlobalLogin.login("exp_hr");

        // Data for dell a Driver
        final String atFirstName = newDriver.atFirstName;

        // [Sidebar] Go to Main Drivers
        $(".drivers-user").hover();
        $(".drivers-user").click();
        $("body").click();

        // [Main Drivers] Table. Select driver "Without Unit"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("Without Unit");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("Without Unit"));
        SelenideElement rowTable = $$("table.table-striped tbody tr").get(0).shouldHave(text(atFirstName), EXPECT_GLOBAL);

        // [Main Drivers] Table. Choose Delete driver
        rowTable.shouldHave(text(atFirstName));
        rowTable.$("button.dropdown-toggle").shouldBe(clickable, EXPECT_GLOBAL).click();
        rowTable.$(".btn-group").shouldHave(cssClass("open"),EXPECT_GLOBAL);
        ElementsCollection dropDown = rowTable.$$(".dropdown-menu-right li");
        dropDown.findBy(exactText("Delete")).click();

        // [Alert] message delete
        String popapText = switchTo().alert().getText();
        assertThat(popapText).isEqualTo("Are you sure you want to delete this item?");
        switchTo().alert().accept();

        // [Main Drivers] Table. Select Drivers "All"
        $("input[name='DriversSearch[name]']").shouldBe(visible).setValue(atFirstName).pressEnter();
        $("#driverssearch-is_driver_has_truck").selectOption("All");
        $("#driverssearch-is_driver_has_truck").getSelectedOption().shouldHave(text("All"));
        SelenideElement rowTableAfterDell = $$("table.table-striped tbody tr").get(0);

        // [Main Drivers] Table. Check delete Driver
        rowTableAfterDell.shouldHave(text("No results found."));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
