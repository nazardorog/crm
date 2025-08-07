package mobile.android;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.Location;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.Message;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.*;

public class MAD016_UpdateLocation {
    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void updateLocation() throws IOException {

        final String crmDriver = "[sys]028_driver Driver";

        // Кейс 1. Попередньо змінює Локацію на CRM. Далі змінює локацію з додатку. Перевіряє коректну зміну локації на CRM
        // CRM. [Sidebar] Go to Expedite Fleet
        GlobalLogin.login("exp_hr");
        $(".logo-mini-icon").click();
        $(".expedite-fleet-user").hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        // CRM. [Expedite Fleet] Table. Choose Pencil truck
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet.shouldHave(text(crmDriver));
        rowExpediteFleet.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // CRM. Підготовка даних для тесту. Змінює статус на Available та локацію
        $("#update_truck").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks-status").selectOption("Available");
        $("#trucks-last_zip").setValue("30030");
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value("Decatur"), EXPECT_10);
        $("#trucks-last_state").shouldHave(value("GA"), EXPECT_10);
        $("#update_truck_send").click();
        $("#update_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        // CRM. Toast
        Message.checkToast("Truck sucessfully updated");

        // CRM. Перевіряє що підготовка даних, зміна локації коректна
        SelenideElement rowDataBeforeTest = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowDataBeforeTest.$(".city-state-zip").shouldHave(text("Decatur, GA 30030"));

        // Mob. Open App
        openApp.login("driver_mob9");
        AndroidDriver driver = openApp.driverAndroid;

        // Mob. Оновлює локацію з My Profile
        driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Update location")).click();
        driver.findElement(AppiumBy.accessibilityId("Sending..."));
        driver.findElement(AppiumBy.accessibilityId("Locations successfully updated."));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();

        // CRM. Перевіряє що оновлена локація через додаток коректно змінилась на CRM
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowDataAfterTest = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowDataAfterTest.$(".city-state-zip").shouldHave(text("North Las Vegas, NV 89032"));

        // Кейс 2. Not available, локація не буде оновлена якщо водій в такому статусі
        // CRM. [Expedite Fleet] Table. Choose Pencil truck
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleetCase2 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleetCase2.shouldHave(text(crmDriver));
        rowExpediteFleetCase2.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // CRM. Підготовка даних для тесту. Змінює статус на Not Available
        $("#update_truck").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks-status").selectOption("Not Available");
        $("#update_truck_send").click();
        $("#update_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        // CRM. Toast
        Message.checkToast("Truck sucessfully updated");

        // CRM. Перевіряє зміну статусу на Not Available
        SelenideElement rowBeforeCase2 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowBeforeCase2.shouldHave(text(crmDriver));
        rowBeforeCase2.$(".truck-status-td").shouldHave(text("Not Available"));

        // Перевідкриває додаток для оновлення статусу
        ((InteractsWithApps) driver).terminateApp("com.empirenational.gl.driver");
        ((InteractsWithApps) driver).activateApp("com.empirenational.gl.driver");

        // Mob. Перевіряє що локацію не можливо змінити в статусі Not Available
        driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Update location")).click();
        driver.findElement(AppiumBy.accessibilityId("Can't update location in current status."));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();

        // Кейс 3. Out Of Service, локація не буде оновлена якщо водій в такому статусі
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleetCase3 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleetCase3.shouldHave(text(crmDriver));
        rowExpediteFleetCase3.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // CRM. Підготовка даних для тесту. Змінює статус на Out Of Service
        $("#update_truck").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks-status").selectOption("Out Of Service");
        $("#trucks-out_of_service_type").selectOption("Health issues");
        $("#update_truck_send").click();
        $("#update_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        // CRM. Toast
        Message.checkToast("Truck sucessfully updated");

        // CRM. Перевіряє зміну статусу на Out Of Service
        SelenideElement rowBeforeCase3 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowBeforeCase3.shouldHave(text(crmDriver));
        rowBeforeCase3.$(".truck-status-td").shouldHave(text("Out Of Service"));

        // Перевідкриває додаток для оновлення статусу Out Of Service
        ((InteractsWithApps) driver).terminateApp("com.empirenational.gl.driver");
        ((InteractsWithApps) driver).activateApp("com.empirenational.gl.driver");

        // Mob. Перевіряє що локацію не можливо змінити в статусі Out Of Service
        driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]")).click();
        driver.findElement(AppiumBy.accessibilityId("Update location")).click();
        driver.findElement(AppiumBy.accessibilityId("Can't update location in current status."));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        CloseWebDriver.tearDown();

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
