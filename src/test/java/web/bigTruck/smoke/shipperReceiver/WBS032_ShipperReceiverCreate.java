package web.bigTruck.smoke.shipperReceiver;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class WBS032_ShipperReceiverCreate {

    // Click Up:
    // CRM SEMI Truck
    // Shippers-receivers
    // 1. Создание Shippers Receivers

    // Global data
    String globalName = GlobalGenerateName.globalName();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void create() throws InterruptedException {

        // Login
        GlobalLogin.login("bt_disp1");

        //переходить до списку Shippers Receivers
        $(".shippers-receivers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        //New Shippers Receivers
        $("#new_shippers-receiver").shouldBe(visible, Duration.ofSeconds(10)).click();

        //фрейм Add shippers receivers
        $("#add_shippers-receiver").shouldBe(visible, Duration.ofSeconds(10));

        String atShippersName = globalName + "Shipper Receiver Name";
        String atStreet1 = "Street 11";
        String atStreet2 = "Street 21";
        String atCountry = "US";
        String atLocation = "New York, NY 10001";
        String atEmail = globalMail;
        String atContactPersonName = "Contact Person Name1";
        String atContactPersonPhoneNumber = "010888-1111";
        String atContactCellNumber = "010888-1112";
        String atNote = "Note1";
        String atLat = "40.7537";
        String atLng = "-73.9992";

        //вводить дані фрейм Add shippers receivers
        $("#shippersreceivers-name").setValue(atShippersName);
        $("#shippersreceivers-street1").setValue(atStreet1);
        $("#shippersreceivers-street2").setValue(atStreet2);
        $("#country_shippers_receivers").selectOption(atCountry);

        $("#shippersreceivers-location").setValue("New York");
        $("#autocomplete-results-shippersreceivers-location").shouldBe(visible, Duration.ofSeconds(5000));
        $$("#autocomplete-results-shippersreceivers-location li").findBy(exactText(atLocation)).click();
        $("#shippersreceivers-location").shouldHave(value(atLocation), Duration.ofSeconds(5000));

        $("#shippersreceivers-email").setValue(atEmail);
        $("#shippersreceivers-contact_person_name").setValue(atContactPersonName);
        $("#contact_person_phone_number-create").setValue(atContactPersonPhoneNumber);
        $("#shippersreceivers-contact_person_cell_number").setValue(atContactCellNumber);
        $("#shippersreceiversdetail-note").setValue(atNote);

        //клік по кнопці Submit фрейму Add shippers receivers
        $("#add_shippers-receiver .button-modal-submit").shouldBe(clickable).click();

        //тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(20));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(20)).shouldHave(text("Shippers Receivers created successfully"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        //перевіряє створений shippers receivers в списку
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atShippersName).pressEnter();

        SelenideElement rowShippers = $$("table.table-striped tbody tr")
                .get(0)
                .shouldHave(text(atShippersName));

        rowShippers.shouldHave(text(atShippersName));
        rowShippers.shouldHave(text(atLocation));
        rowShippers.shouldHave(text(atStreet1));
        rowShippers.shouldHave(text(atStreet2));
        rowShippers.shouldHave(text(atLat));
        rowShippers.shouldHave(text(atLng));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}