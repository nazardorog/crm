package mobile.android;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;
import utilsWeb.commonWeb.CloseWebDriver;
import utilsWeb.commonWeb.Message;
import utilsWeb.configWeb.GlobalLogin;

import java.io.IOException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_10;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class MAD013_DriverStatusChange {

    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void statusChange() throws IOException {

        // CRM. Підготовка даних
        // CRM. Login
        GlobalLogin.login("exp_hr");

        final String crmDriver = "024_driver@tbd.com";

        // CRM. [Sidebar] Go to Expedite Fleet
        $(".logo-mini-icon").click();
        $(".expedite-fleet-user").hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        // CRM. [Expedite Fleet] Table. Choose Pencil
        $("input[name='TrucksSearch[filter_cell_phone]").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet.shouldHave(text(crmDriver));
        rowExpediteFleet.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // CRM. Update status/location
        $("#update_truck").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks-status").selectOption("Not Available");
        $("#trucks-last_zip").setValue("30033");
        $("#zipFillBtn").click();
        $("#trucks-last_city").shouldHave(value("Decatur"), EXPECT_10);
        $("#update_truck_send").click();
        $("#update_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        Message.checkToast("Truck sucessfully updated");

        SelenideElement rowExpediteFleet1 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet1.$(".city-state-zip").shouldHave(text("Decatur, GA 30033"));

        // APP. Open App
        openApp.login("driver_mob4");

        AndroidDriver driver = openApp.driverAndroid;

        // APP. Tab My profile
        WebElement moreElement = driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(moreElement.isDisplayed(), "Елемент My profile не відображається");
        moreElement.click();

        // APP. Відкриває дроп даун Status Driver. Перевіряє наявність пунктів Available, Available On, Not Available
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Not Available')]")).click();
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"Available\"]"));
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"Available On\"]"));
        driver.findElement(By.xpath("//android.view.View[@content-desc=\"Not Available\"]"));

        // APP. Кейс 1. Встановлює статус водія в Available
        // APP. Вибирає з дроп даун Available. Підтверджує вибір
        driver.findElement(AppiumBy.accessibilityId("Available")).click();
        sleep(2000);
        driver.findElement(AppiumBy.accessibilityId("Status"));
        driver.findElement(AppiumBy.accessibilityId("Status Updated"));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();
        driver.findElement(AppiumBy.accessibilityId("Available"));

        // CRM. Перевіряє статус Available
        $("input[name='TrucksSearch[filter_cell_phone]").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet2 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet2.$(".truck-status-td").shouldHave(text("Available"));

        // Кейс 2. Встановлює статус водія Available On
        // Відкриває дроп даун Status Driver
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Available')]")).click();

        // Вибирає статус
        driver.findElement(AppiumBy.accessibilityId("Available On")).click();
        driver.findElement(AppiumBy.accessibilityId("Set your status"));
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"30033\")")).click();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().text(\"30033\")")).clear();
        driver.findElement(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\")")).sendKeys("30084");
        driver.hideKeyboard();
        driver.findElement(AppiumBy.accessibilityId("Set")).click();
        sleep(2000);
        driver.findElement(AppiumBy.accessibilityId("Status"));
        driver.findElement(AppiumBy.accessibilityId("Status Updated"));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Available On')]"));

        // CRM. Перевіряє статус Available
        $("input[name='TrucksSearch[filter_cell_phone]").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet3 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet3.$(".truck-status-td").shouldHave(text("Available On"));
        rowExpediteFleet3.$(".city-state-zip").shouldHave(text("Tucker, GA 30084"));
        rowExpediteFleet2.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();
        $("#update_truck .close").click();

        // Кейс 3. Встановлює статус водія Not Available
        // Відкриває дроп даун Status Driver
        driver.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Available On')]")).click();

        //вибирає статус
        driver.findElement(AppiumBy.accessibilityId("Not Available")).click();
        driver.findElement(AppiumBy.accessibilityId("Status"));
        driver.findElement(AppiumBy.accessibilityId("Status Updated"));
        driver.findElement(AppiumBy.accessibilityId("Ok")).click();
        driver.findElement(AppiumBy.accessibilityId("Not Available")).click();

        // CRM. Перевіряє статус Available
        $("input[name='TrucksSearch[filter_cell_phone]").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet4 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet4.$(".truck-status-td").shouldHave(text("Not Available"));
        rowExpediteFleet2.$(".glyphicon-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();
        $("#update_truck .close").click();

        // Кейс 4. Перевіряє водія з активним вантажем не дає змінювати статус
        openApp.login("driver_mob5");
        AndroidDriver driverUser25 = openApp.driverAndroid;

        // APP. Tab My profile
        WebElement moreElement2 = driverUser25.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(moreElement2.isDisplayed(), "Елемент My profile не відображається");
        moreElement2.click();

        // APP. Відкриває дроп даун Status Driver. Перевіряє наявність пунктів Available, Available On, Not Available
        driverUser25.findElement(By.xpath("//android.widget.ImageView[contains(@content-desc, 'Available On')]")).click();

        // APP. Вибирає з дроп даун Available. Підтверджує вибір
        driverUser25.findElement(AppiumBy.accessibilityId("Available")).click();
        driverUser25.findElement(AppiumBy.accessibilityId("Status"));
        driverUser25.findElement(AppiumBy.accessibilityId("Failed to change status. You are currently assigned a load. When you deliver it, you can change the status."));
        driverUser25.findElement(AppiumBy.accessibilityId("Ok")).click();
        driverUser25.findElement(AppiumBy.accessibilityId("Available On"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        CloseWebDriver.tearDown();

        CloseWebDriver.tearDown();
        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
