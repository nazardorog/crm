package utilsWeb.commonWeb;

import java.time.Duration;

import com.codeborne.selenide.Condition;
import java.io.File;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;
import static com.codeborne.selenide.Configuration.*;

public class NewLoad {

    public static String expedite() {

        String atTruck = "0303";
        String atDriver = "Auto Test";
        String atTeamDriver = "Auto Test2";
        
        
        $("#new_load").shouldBe(enabled).click();

        // Remove chat widget
        boolean chatWidget = $(".chat-widget").isDisplayed();
        if (chatWidget){
            executeJavaScript("document.querySelector('.chat-widget').style.display='none'");
        }
        
        // Broker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").setValue("Auto test broker");
        $$(".select2-results__options").findBy(text("Auto test broker")).click();
        $$("select#loads-agent_id option").findBy(text("Auto test agent ")).click();
        
        // Origin
        $("#select2-shippers-receiver-origin-container").click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $(".select2-results").shouldHave(text("Auto test shipper 1")).click();

        // Destination
        $("#select2-shippers-receiver-destination-container").click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option").findBy(text("Auto test shipper 2")).click();

        // Origin Destination datetime
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(Condition.text("Today")).click();
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(Condition.text("Today")).click();
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(Condition.text("Today")).click();
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").shouldBe(enabled).click();
        $$("div.datetimepicker-days tfoot tr th").findBy(Condition.text("Today")).click();

        // Load info
        $("#loads-reference").setValue("auto");
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-carrier_rate-disp").setValue("80000").pressEnter();

        // Shippers info
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Destination info
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");
        
        // Load file
        $("#add_load").find(".modal-footer-button .fa-files-o").click();
        $("#load_documents_modal").shouldBe(visible, EXPECT_GLOBAL);
        File file = new File(downloadsFolder + "/1pdf.pdf");
        $("#loaddocuments-0-file").uploadFile(file);
        $("#loaddocuments-0-type").selectOption("BOL");

        // Scrolling form Load file
        if (!$("#load_documents_modal_pseudo_submit").isDisplayed()){
            Scrolling.scrollDown($("#add_load"), $("#load_documents_modal_pseudo_submit"));
        }
        $("#load_documents_modal_pseudo_submit").click();

        // New load Submit
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#add_load_send_old").click();

        // Dispatch load
        $("#select2-load_truck_id-0-container").shouldBe(visible, EXPECT_GLOBAL).click();
        String pro_number = $("#load_dispatch > div > div > div.modal-header > h4").text().split("#")[1];
        $(".select2-search__field").shouldBe(enabled).setValue(atTruck);
        $(".select2-results__option--highlighted").shouldHave(text(atTruck)).click();
        $("#select2-load_driver_id-0-container").shouldHave(Condition.text(atDriver));
        $("#select2-load_team_driver_id-0-container").shouldHave(Condition.text(atTeamDriver));
        $("#select2-load_truck_id-0-container").shouldHave(Condition.text(atTruck));

        // Remove help block
        boolean helpBlock = $(".help-block").isDisplayed();
        if (helpBlock){
            executeJavaScript("arguments[0].style.display='none';", $(".help-block"));
        }

        $$("#loads-load_type label").findBy(Condition.text("Board")).click();

        // Close Dispatch board
        $("#dispatch_load_send").click();
        $("#load_dispatch").shouldNotBe(visible,EXPECT_GLOBAL);

        return pro_number;
    }
}
