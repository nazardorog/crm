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

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class MAD014_StatusHoos {
    GlobalLoginMob openApp = new GlobalLoginMob();

    @Test
    public void statusHoos() throws IOException {

        // CRM. Підготовка даних
        // CRM. Login
        GlobalLogin.login("exp_hr");

        final String crmDriver = "[sys]026_driver Driver";

        // CRM. [Sidebar] Go to Expedite Fleet
        $(".logo-mini-icon").click();
        $(".expedite-fleet-user").hover();
        $(".expedite-fleet-user").click();
        $("body").click();

        // CRM. [Expedite Fleet] Table. Choose Pencil truck
        $("input[name='TrucksSearch[filter_driver_name]']").shouldBe(visible).setValue(crmDriver).pressEnter();
        SelenideElement rowExpediteFleet = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet.shouldHave(text(crmDriver));
        rowExpediteFleet.$(".icon-update-truck-pencil").shouldBe(visible, EXPECT_GLOBAL).hover().click();

        // CRM. Змінює статус на Hidden OOS
        $("#update_trucks").shouldBe(visible, EXPECT_GLOBAL);
        $("#trucks-status").selectOption("Hidden OOS");
        $("#trucks-out_of_service_note").setValue("service reason note");
        $("#update_trucks_send").click();
        $("#update_trucks").shouldNotBe(visible, EXPECT_GLOBAL);

        // CRM. Toast
        Message.checkToast("Truck sucessfully updated");

        // CRM. Перевіряє вдалу зміну статусу
        SelenideElement rowExpediteFleet2 = $$("table.table-striped tbody tr").get(0).shouldHave(text(crmDriver), EXPECT_GLOBAL);
        rowExpediteFleet2.$(".truck-status-td").shouldHave(text("Hidden OOS"));

        // APP. Кейс 1. Авторизується під Driver, перевіряє що статус Hidden OOS не відображається
        openApp.login("driver_mob6");
        AndroidDriver driver = openApp.driverAndroid;

        // APP. Перевіряє відображення статусу Not Available, а не Hidden OOS
        WebElement myProfile = driver.findElement(By.xpath("//*[contains(@content-desc, 'My profile') and contains(@content-desc, 'Tab 1 of 3')]"));
        Assert.assertTrue(myProfile.isDisplayed(), "Елемент My profile не відображається");
        myProfile.click();
        driver.findElement(AppiumBy.accessibilityId("Not Available"));

        // APP. Кейс 2. Авторизується під Owner, перевіряє що статус Hidden OOS не відображається
        openApp.login("driver_mob7");
        AndroidDriver driver2 = openApp.driverAndroid;

        driver2.findElement(AppiumBy.accessibilityId("My Trucks")).click();

        // APP. Перевіряє відображення статусу Not Available
        driver2.findElement(By.xpath("//android.view.View[contains(@content-desc, '#0404') and contains(@content-desc, 'Not Available')]"));


        // APP. Перевіряє відображення водія та контакт номера
        driver2.findElement(By.xpath("//android.view.View[contains(@content-desc, '[sys]026_driver Driver') and contains(@content-desc, '(001) 370-4759')]"));
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
