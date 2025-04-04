package web.LoadBord;

import web.Login;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TestCase14LoadBoard extends Login {

    @Test
    public void newShippers() throws InterruptedException {

        System.out.println("TestCase14LoadBoard - Start");

        //прибрає віджет чат
        executeJavaScript("document.querySelector('.chat-widget').style.display='none'");

        //відкриває New Load
        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));
        $("#new_load").shouldBe(enabled).click();

    //створює Origin Shippers

        //готовить дані для створення Original Shippers
        Random random = new Random();
        String nameShipperOrigin = "Auto test shipper" + String.format("%3d", random.nextInt(1000)) ;
        String street1ShipperOrigin = "W 57th St";
        String countryShipperOrigin = "US";
        String zipCodeShipperOrigin = "64113";
        String mailShipperOrigin = "autotest" + String.format("%03d", random.nextInt(1000))+ "@mail.com";
        String contactPersonNameShipperOrigin = "Auto test contact person" + String.format("%3d", random.nextInt(1000)) ;
        String contactPersonNumberShipperOrigin = "(057) 333-" + String.format("%04d", random.nextInt(10000));

        //створює Origin Shippers
        $("[data-type='origin']").shouldBe(visible, Duration.ofSeconds(20)).click();
        $("#shippersreceivers-name").setValue(nameShipperOrigin);
        $("#shippersreceivers-street1").setValue(street1ShipperOrigin);
        $("#country_shippers_receivers").selectOption(countryShipperOrigin);
        $("#shippersreceivers-location").setValue(zipCodeShipperOrigin);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(visible).click();
        $("#shippersreceivers-email").setValue(mailShipperOrigin);
        $("#shippersreceivers-contact_person_name").setValue(contactPersonNameShipperOrigin);
        $("#contact_person_phone_number-create").setValue(contactPersonNumberShipperOrigin);
        $("#add_shippers-receiver_send").click();

        //перевіряє додавання створеного Original Shipper на фреймі New Load
        Thread.sleep(4000);
        $(".shipper-locations-name").shouldBe(visible).shouldHave(text(nameShipperOrigin));
        $(".shipper-locations-street").shouldHave(text(street1ShipperOrigin));
        $(".shipper-locations-location").shouldHave(text("Kansas City, MO " + zipCodeShipperOrigin));

    //редагує Original Shipper
        $(".shipper_buttons .update_shippers-receiver").click();
        $(".modal-update-shippers-receiver").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Update shipper"));

        //генерує дані для редагування Original Shipper
        String editNameShipperOrigin = "Auto test shipper" + String.format("%3d", random.nextInt(1000)) ;
        String editStreet1ShipperOrigin = "W 60th St";
        String editCountryOrigin = "US";
        String editZipCodeShipperOrigin = "64110";
        String editMail = "autotest" + String.format("%03d", random.nextInt(1000))+ "@mail.com";
        String editContactPersonName = "Auto test contact person" + String.format("%3d", random.nextInt(1000)) ;
        String editContactPersonNumber = "(057) 333-" + String.format("%04d", random.nextInt(10000));

        //вводить відредаговані дані Original Shipper
        $("#shippersreceivers-name").setValue(editNameShipperOrigin);
        $("#shippersreceivers-street1").setValue(editStreet1ShipperOrigin);
        $("#country_shippers_receivers").selectOption(editCountryOrigin);
        $("#shippersreceivers-location").setValue(editZipCodeShipperOrigin);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(visible).click();
        $("#shippersreceivers-email").setValue(editMail);
        $("#shippersreceivers-contact_person_name").setValue(editContactPersonName);
        $("#contact_person_phone_number-update").setValue(editContactPersonNumber);
        $("#update_shippers-receiver_send").click();

        //перевіряє відредаговані дані Original Shipper на фреймі New Load
        Thread.sleep(6000);
        $(".shipper-locations-name").shouldHave(text(editNameShipperOrigin));
        $(".shipper-locations-street").shouldHave(text(editStreet1ShipperOrigin));
        $(".shipper-locations-location").shouldHave(text("Kansas City, MO " + editZipCodeShipperOrigin));

    //створює та редагує Destination Shippers

        //готовить дані для створення Shippers
        String nameShipperDest = "Auto test shipper" + String.format("%3d", random.nextInt(1000)) ;
        String street1ShipperDest = "W 150th St";
        String countryShipperDest = "US";
        String zipCodeShipperDest = "02295";
        String mailShipperDest = "autotest" + String.format("%03d", random.nextInt(1000))+ "@mail.com";
        String contactPersonNameShipperDest = "Auto test contact person" + String.format("%3d", random.nextInt(1000)) ;
        String contactPersonNumberShipperDest = "(058) 333-" + String.format("%04d", random.nextInt(10000));

        //створює Destination Shippers
        $("[data-type='destination']").shouldBe(visible).click();
        $("#shippersreceivers-name").setValue(nameShipperDest);
        $("#shippersreceivers-street1").setValue(street1ShipperDest);
        $("#country_shippers_receivers").selectOption(countryShipperDest);
        $("#shippersreceivers-location").setValue(zipCodeShipperDest);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(visible).click();
        $("#shippersreceivers-email").setValue(mailShipperDest);
        $("#shippersreceivers-contact_person_name").setValue(contactPersonNameShipperDest);
        $("#contact_person_phone_number-create").setValue(contactPersonNumberShipperDest);
        $("#add_shippers-receiver_send").click();

        //перевіряє додавання свтореного Destination Shipper на фреймі New Load
        Thread.sleep(4000);
        $("#shippers-destination-sortable .shipper-locations-name").shouldHave(text(nameShipperDest));
        $("#shippers-destination-sortable .shipper-locations-street").shouldHave(text(street1ShipperDest));
        $("#shippers-destination-sortable .shipper-locations-location").shouldHave(text("Boston, MA " + zipCodeShipperDest));

    //редагує Destination Shipper
        $("#shippers-destination-sortable .update_shippers-receiver").click();
        $(".modal-update-shippers-receiver").shouldBe(visible, Duration.ofSeconds(20)).shouldHave(text("Update shipper"));

        //генеруєдані для редагування Destination Shipper
        String editNameShipperDest = "Auto test shipper" + String.format("%3d", random.nextInt(1000)) ;
        String editStreet1ShipperDest = "700 Galapago St";
        String editCountryShipperDest = "US";
        String editZipCodeShipperDest = "80204";
        String editMailShipperDest = "autotest" + String.format("%03d", random.nextInt(1000))+ "@mail.com";
        String editContactPersonNameShipperDest = "Auto test contact person" + String.format("%3d", random.nextInt(1000)) ;
        String editContactPersonNumberShipperDest = "(058) 333-" + String.format("%04d", random.nextInt(10000));

        //вводить відредаговані дані Destination Shipper
        $("#shippersreceivers-name").setValue(editNameShipperDest);
        $("#shippersreceivers-street1").setValue(editStreet1ShipperDest);
        $("#country_shippers_receivers").selectOption(editCountryShipperDest);
        $("#shippersreceivers-location").setValue(editZipCodeShipperDest);
        $("#autocomplete-results-shippersreceivers-location li").shouldBe(visible).click();
        $("#shippersreceivers-email").setValue(editMailShipperDest);
        $("#shippersreceivers-contact_person_name").setValue(editContactPersonNameShipperDest);
        $("#contact_person_phone_number-update").setValue(editContactPersonNumberShipperDest);
        $("#update_shippers-receiver_send").click();

        //перевіряє відредаговані дані Destination Shipper на фреймі New Load
        Thread.sleep(4000);
        $("#shippers-destination-sortable .shipper-locations-name").shouldHave(text(editNameShipperDest));
        $("#shippers-destination-sortable .shipper-locations-street").shouldHave(text(editStreet1ShipperDest));
        $("#shippers-destination-sortable .shipper-locations-location").shouldHave(text("Denver, CO " + editZipCodeShipperDest));

        //повертаємось на Load bord далі вкладка Shippers/receivers
        $("#add_load .close").click();
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();

        $(".shippers-receivers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        //перевіряє роректність координат Original Shippers
        $("[name='ShippersReceiversSearch[name]']").shouldBe(enabled).setValue(editNameShipperOrigin).pressEnter();
        Thread.sleep(4000);
        $("#w0").shouldHave(text(editNameShipperOrigin));
        $("#w0").shouldHave(text("Kansas City, MO " + editZipCodeShipperOrigin));
        $("#w0").shouldHave(text(editStreet1ShipperOrigin));
        $("#w0").shouldHave(text("39.03"));
        $("#w0").shouldHave(text("-94.57"));

        //перевіряє коректність координат Destination Shippers
        $("[name='ShippersReceiversSearch[name]']").clear();
        $("[name='ShippersReceiversSearch[name]']").shouldBe(enabled).setValue(editNameShipperDest).pressEnter();
        $("#w0").shouldBe(visible).shouldHave(text(editNameShipperDest));
        $("#w0").shouldHave(text("Denver, CO " + editZipCodeShipperDest));
        $("#w0").shouldHave(text(editStreet1ShipperDest));
        $("#w0").shouldHave(text("39.73"));
        $("#w0").shouldHave(text("-105.02"));

        $(".logo-mini-icon").shouldBe(visible, Duration.ofSeconds(30));

        System.out.println("TestCase14LoadBoard - Test Pass");
    }

    public void scrollDown(SelenideElement modal, SelenideElement target) {
        while (!target.isDisplayed()) {
            executeJavaScript("arguments[0].scrollTop += 100;", modal); // Прокрутка вниз на 100 пікселів
            sleep(500);
        }
    }
}
