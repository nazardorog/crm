package web.bigTruck.shippersReceivers;

import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class BigTruckTestCase2ShippersReceivers {

    // Click Up:
    // CRM SEMI Truck
    // Shippers-receivers
    // 1. Создание Shippers Receivers

    @Test (priority = 1)
    public void newShippersReceiversBigTruck() throws InterruptedException {

        System.out.println("BigTruckTestCase1ShippersReceivers - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //переходить до списку Shippers Receivers
        $(".shippers-receivers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".shippers-receivers-user").click();
        $("body").click();

        //New Shippers Receivers
        $("#new_shippers-receiver").shouldBe(visible, Duration.ofSeconds(10)).click();

        //фрейм Add shippers receivers
        $("#add_shippers-receiver").shouldBe(visible, Duration.ofSeconds(10));

        //дані для Shippers Receivers
        Random random = new Random();
        int randomNumber = random.nextInt(1000);

        String atShippersName = "Shippers Name auto test 1" + randomNumber;
        String atStreet1 = "Street auto test 11_" + randomNumber;
        String atStreet2 = "Street auto test 12_" + randomNumber;
        String atCountry = "US";
        String atLocation = "New York, NY 10001";
        String atEmail = "EmailShippers1" + randomNumber + "@mail.com";
        String atContactPersonName = "Contact Person Name auto test 1_" + randomNumber;
        String atContactPersonPhoneNumber = "010888-1111;";
        String atContactCellNumber = "010888-1112;";
        String atNote = "Note shippers auto test 1_" + randomNumber;
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

        // *** Редагування Truck вкладка General фрейму Add truck ***

        rowShippers.$(".glyphicon-pencil").click();

        //редагування дані
        String atStreet1Edit = "Street auto test 21_" + randomNumber;
        String atStreet2Edit = "Street auto test 22_" + randomNumber;
        String atCountryEdit = "MX";
        String atLocationEdit = "Mexico, NY 13114";
        String atEmailEdit = "EmailShippers2_" + randomNumber + "@mail.com";
        String atContactPersonNameEdit = "Contact Person Name auto test 2_" + randomNumber;
        String atContactPersonPhoneNumberEdit = "(910) 888-1111";
        String atContactCellNumberEdit = "910888-1112";
        String atNoteEdit = "Note shippers auto test 2_" + randomNumber;
        String atLatEdit = "43.46";
        String atLngEdit = "-76.23";

        //редагування вводить дані фрейм Update shippers receivers
        //вводить дані фрейм Add shippers receivers
        $("#shippersreceivers-street1").setValue(atStreet1Edit);
        $("#shippersreceivers-street2").setValue(atStreet2Edit);
        $("#country_shippers_receivers").selectOption(atCountryEdit);

        $("#shippersreceivers-location").setValue(atLocationEdit);
        $("#autocomplete-results-shippersreceivers-location").shouldBe(visible, Duration.ofSeconds(5000));
        $$("#autocomplete-results-shippersreceivers-location li").findBy(exactText(atLocationEdit)).click();
        $("#shippersreceivers-location").shouldHave(value(atLocationEdit), Duration.ofSeconds(5000));

        $("#shippersreceivers-email").setValue(atEmailEdit);
        $("#shippersreceivers-contact_person_name").setValue(atContactPersonNameEdit);
        $("#contact_person_phone_number-update").setValue(atContactPersonPhoneNumberEdit);
        $("#shippersreceivers-contact_person_cell_number").setValue(atContactCellNumberEdit);
        $("#shippersreceiversdetail-note").setValue(atNoteEdit);

        //редагування Submit фрейм Update shippers receivers
        $("#update_shippers-receiver_send").click();

        //редагування тост вспливайка
        $("#toast-container").shouldBe(visible, Duration.ofSeconds(40));
        $(".toast-message").shouldHave(visible, Duration.ofSeconds(10)).shouldHave(text("Shippers Receivers updated successfully"));
        $("#toast-container").shouldNotHave(visible, Duration.ofSeconds(20));

        // *** Перевіряє відредагований Shippers в списку ***
        $("input[name='ShippersReceiversSearch[name]']").shouldBe(visible).setValue(atShippersName).pressEnter();

        SelenideElement rowShippersEdit = $$("table.table-striped tbody tr")
                .get(0)
                .shouldHave(text(atShippersName));

        rowShippersEdit.shouldHave(text(atShippersName));
        rowShippersEdit.shouldHave(text(atLocationEdit));
        rowShippersEdit.shouldHave(text(atStreet1Edit));
        rowShippersEdit.shouldHave(text(atStreet2Edit));
        rowShippersEdit.shouldHave(text(atLatEdit));
        rowShippersEdit.shouldHave(text(atLngEdit));

        // *** Перевіряє відредаговані дані Shippers через Око ***

        //клік по кнопці око
        rowShippersEdit.$(".glyphicon-eye-open").click();
        $("#view_shippers-receiver").shouldBe(visible, Duration.ofSeconds(10));

        //перевіряє відредаговані дані Truck в View truck
        $("table#w0").$$("tr").findBy(text("Name"))
                .$$("td").first().shouldHave(text(atShippersName));

        $("table#w0").$$("tr").findBy(text("Street 1"))
                .$$("td").first().shouldHave(text(atStreet1Edit));

        $("table#w0").$$("tr").findBy(text("Street 2"))
                .$$("td").first().shouldHave(text(atStreet2Edit));

        $("table#w0").$$("tr").findBy(text("City"))
                .$$("td").first().shouldHave(text("Mexico"));

        $("table#w0").$$("tr").findBy(text("State"))
                .$$("td").first().shouldHave(text("NY"));

        $("table#w0").$$("tr").findBy(text("Zip"))
                .$$("td").first().shouldHave(text("13114"));

        $("table#w0").$$("tr").findBy(text("Location"))
                .$$("td").first().shouldHave(text(atLocationEdit));

        $("table#w0").$$("tr").findBy(text("Lat"))
                .$$("td").first().shouldHave(text(atLatEdit));

        $("table#w0").$$("tr").findBy(text("Lng"))
                .$$("td").first().shouldHave(text(atLngEdit));

        $("table#w0").$$("tr").findBy(text("Contact Person Name"))
                .$$("td").first().shouldHave(text(atContactPersonNameEdit));

        $("table#w0").$$("tr").findBy(text("Contact Person Phone Number"))
                .$$("td").first().shouldHave(text(atContactPersonPhoneNumberEdit));

        //закриває фрейм View shippers receivers
        $("#view_shippers-receiver button.close").click();
        $("#view_shippers-receiver").shouldNotBe(visible, Duration.ofSeconds(10));

        System.out.println("BigTruckTestCase2ShippersReceivers - Test Pass");
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
