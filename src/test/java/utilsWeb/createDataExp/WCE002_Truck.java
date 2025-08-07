package utilsWeb.createDataExp;

import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WCE002_Truck {

    // Global data
    public static final String globalNumberSeventeen = "1234567890" + GlobalGenerateName.globalNumberSeven();
    public static final String globalName = GlobalGenerateName.globalName();

    public String atTruckNumber, atVinNumber, atModel, atColor, atPlateNumber, atYear, atTypeTruck,
            atMake, atPayload, atOwner, atDimensionsL, atDimensionsW, atDimensionsH,atCity,
            atState, atZip, atStatus;

    @Test
    public void runTest() {
        create();
    }

    public WCE002_Truck create() {

        // Data for creating a Truck
        this.atTruckNumber = globalName + "Truck Number";
        this.atVinNumber = globalNumberSeventeen;
        this.atModel = globalName + "Model";
        this.atColor = globalName + "Color";
        this.atPlateNumber = globalName + "Plate Number";
        this.atYear = "2025";
        this.atTypeTruck = "Sprinter Van";
        this.atMake = globalName + "Make";
        this.atPayload = "10000";
        this.atOwner = "Autotest 4 Owner";
        this.atDimensionsL = "100";
        this.atDimensionsW = "100";
        this.atDimensionsH = "100";
        this.atCity = "Kansas City";
        this.atState = "KS";
        this.atZip = "66105";
        this.atStatus = "Available On";

//        WCD001_Driver driverCreate = new WCD001_Driver();
//        $("#trucks-truck_number").setValue(this.atTruckNumber);

        // Login
        GlobalLogin.login("exp_hr");

        // [Sidebar] Go to Main Trucks
        $(".trucks-user").hover();
        $(".trucks-user").click();
        $("body").click();

        // [Main Trucks] Button New Trucks
        $("#new_truck").click();
        $("#add_truck").shouldBe(visible);

        // [Add Trucks] Tab General. Input data
        $("#trucks-truck_number").setValue(this.atTruckNumber);
        $("#trucks-model").setValue(this.atModel);
        $("#trucks-vin_number").setValue(this.atVinNumber);
        $("#trucks-color").setValue(this.atColor);
        $("#trucks-plate_number").setValue(this.atPlateNumber);
        $("#trucks-year").setValue(this.atYear);
        $("#trucks-type_truck").selectOption(this.atTypeTruck);
        $("#trucks-make").setValue(this.atMake);
        $("#trucks-payload").setValue(this.atPayload);
        $("#trucks-status").selectOption(this.atStatus);

        // [Add Trucks] Tab General. Owner input this
        $("#select2-owner_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$("li.select2-results__option").findBy(text(this.atOwner)).click();

        // [Add Trucks] Tab General. Dimensions input this
        $("#trucks-length").setValue(this.atDimensionsL);
        $("#trucks-width").setValue(this.atDimensionsW);
        $("#trucks-height").setValue(this.atDimensionsH);

        // [Add Trucks] Tab General. Zip input this
        $("#trucks-last_zip").setValue(this.atZip);
        $("#zipFillBtn").click();
        $("#zipFillBtn").shouldBe(enabled, EXPECT_GLOBAL);
        $("#trucks-last_city").shouldHave(value(this.atCity));
        $("#trucks-last_state").shouldHave(value(this.atState));

        // [Add Trucks] Tab General. Calendar Registration Expiration
        $("#trucks-registration_expiration-kvdate .kv-date-picker").click();
        Calendar.setDate(1);

        // [Add Trucks] Button Submit
        $("#add_truck_send").shouldBe(clickable).click();
        $("#add_truck").shouldNotBe(visible, EXPECT_GLOBAL);

        // Delete toast message
        Message.dellToast();

        // Close web driver
        CloseWebDriver.tearDown();

        return this;
    }
}
