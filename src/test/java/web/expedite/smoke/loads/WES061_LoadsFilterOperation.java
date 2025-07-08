package web.expedite.smoke.loads;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.*;
import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class WES061_LoadsFilterOperation {

    // Click Up:
    // CRM EXPEDITE - Smoke - Loads
    // 1. Работа фильтра

    @Test
    void create() {

        // Login
        GlobalLogin.login("exp_disp1");

        // Data for check a loads
        final String atLoadNumber = "35610";
        final String atCustomer = "at_Broker61";
        final String atZipShippersPick = "89419";
        final String atShippersPickName = "Lovelock, NV 89419";
        final String atZipShippersDrop = "89815";
        final String atZipShippersDropName = "Spring Creek, NV 89815";
        final String atTruck = "at_Truck61";
        final String atDriver = "at_Driver61";
        final String atRate = "799.99";

        // [Sidebar] Go to Main Loads
        $(".loads-user").hover();
        $(".loads-user").click();
        $("body").click();

        // [Loads] Table. Filter Pro
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atLoadNumber).pressEnter();
        SelenideElement rowLoads = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoads.shouldHave(text(atLoadNumber));
        rowLoads.shouldHave(text(atTruck));
        rowLoads.shouldHave(text(atDriver));
        rowLoads.shouldHave(text(atCustomer));
        rowLoads.shouldHave(text(atShippersPickName));
        rowLoads.shouldHave(text(atZipShippersDropName));
        rowLoads.shouldHave(text(atRate));
        rowLoads.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[our_pro_number]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Truck
        $("input[name='LoadsSearch[truck_number]']").shouldBe(visible).setValue(atTruck).pressEnter();
        SelenideElement rowLoadsTruck = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsTruck.shouldHave(text(atLoadNumber));
        rowLoadsTruck.shouldHave(text(atTruck));
        rowLoadsTruck.shouldHave(text(atDriver));
        rowLoadsTruck.shouldHave(text(atCustomer));
        rowLoadsTruck.shouldHave(text(atShippersPickName));
        rowLoadsTruck.shouldHave(text(atZipShippersDropName));
        rowLoadsTruck.shouldHave(text(atRate));
        rowLoadsTruck.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[truck_number]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Driver
        $("input[name='LoadsSearch[driver_name]']").shouldBe(visible).setValue(atDriver).pressEnter();
        SelenideElement rowLoadsDriver = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsDriver.shouldHave(text(atLoadNumber));
        rowLoadsDriver.shouldHave(text(atTruck));
        rowLoadsDriver.shouldHave(text(atDriver));
        rowLoadsDriver.shouldHave(text(atCustomer));
        rowLoadsDriver.shouldHave(text(atShippersPickName));
        rowLoadsDriver.shouldHave(text(atZipShippersDropName));
        rowLoadsDriver.shouldHave(text(atRate));
        rowLoadsDriver.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[driver_name]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Customer
        $("input[name='LoadsSearch[customer]']").shouldBe(visible).setValue(atCustomer).pressEnter();
        SelenideElement rowLoadsCustomer = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsCustomer.shouldHave(text(atLoadNumber));
        rowLoadsCustomer.shouldHave(text(atTruck));
        rowLoadsCustomer.shouldHave(text(atDriver));
        rowLoadsCustomer.shouldHave(text(atCustomer));
        rowLoadsCustomer.shouldHave(text(atShippersPickName));
        rowLoadsCustomer.shouldHave(text(atZipShippersDropName));
        rowLoadsCustomer.shouldHave(text(atRate));
        rowLoadsCustomer.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[customer]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Pick
        $("input[name='LoadsSearch[city_state_day_from]']").shouldBe(visible).setValue(atZipShippersPick).pressEnter();
        SelenideElement rowLoadsPick = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsPick.shouldHave(text(atLoadNumber));
        rowLoadsPick.shouldHave(text(atTruck));
        rowLoadsPick.shouldHave(text(atDriver));
        rowLoadsPick.shouldHave(text(atCustomer));
        rowLoadsPick.shouldHave(text(atShippersPickName));
        rowLoadsPick.shouldHave(text(atZipShippersDropName));
        rowLoadsPick.shouldHave(text(atRate));
        rowLoadsPick.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[city_state_day_from]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Drop
        $("input[name='LoadsSearch[city_state_day_to]']").shouldBe(visible).setValue(atZipShippersDrop).pressEnter();
        SelenideElement rowLoadsDrop = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsDrop.shouldHave(text(atLoadNumber));
        rowLoadsDrop.shouldHave(text(atTruck));
        rowLoadsDrop.shouldHave(text(atDriver));
        rowLoadsDrop.shouldHave(text(atCustomer));
        rowLoadsDrop.shouldHave(text(atShippersPickName));
        rowLoadsDrop.shouldHave(text(atZipShippersDropName));
        rowLoadsDrop.shouldHave(text(atRate));
        rowLoadsDrop.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[city_state_day_to]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Rate
        $("input[name='LoadsSearch[rate]']").shouldBe(visible).setValue(atRate).pressEnter();
        SelenideElement rowLoadsRate = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsRate.shouldHave(text(atLoadNumber));
        rowLoadsRate.shouldHave(text(atTruck));
        rowLoadsRate.shouldHave(text(atDriver));
        rowLoadsRate.shouldHave(text(atCustomer));
        rowLoadsRate.shouldHave(text(atShippersPickName));
        rowLoadsRate.shouldHave(text(atZipShippersDropName));
        rowLoadsRate.shouldHave(text(atRate));
        rowLoadsRate.shouldHave(text("En Route"));
        $("input[name='LoadsSearch[rate]']").clear();
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Status New
        $("select[name='LoadsSearch[status]']").selectOption("New");
        SelenideElement rowLoadsStatusNew = $$("table.table-striped tbody tr").get(0);
        rowLoadsStatusNew.shouldHave(text("New"));

        // [Loads] Table. Filter Status En Route
        $("select[name='LoadsSearch[status]']").selectOption("En Route");
        SelenideElement rowLoadsStatusEnRoute = $$("table.table-striped tbody tr").get(0);
        rowLoadsStatusEnRoute.shouldHave(text("En Route"));

        // [Loads] Table. Filter Status Delivered
        $("select[name='LoadsSearch[status]']").selectOption("Delivered");
        SelenideElement rowLoadsStatusDelivered = $$("table.table-striped tbody tr").get(0);
        rowLoadsStatusDelivered.shouldHave(text("Delivered"));

        // [Loads] Table. Filter Status Invoiced
        $("select[name='LoadsSearch[status]']").selectOption("Invoiced");
        SelenideElement rowLoadsStatusInvoiced = $$("table.table-striped tbody tr").get(0);
        rowLoadsStatusInvoiced.shouldHave(text("Invoiced"));

        // [Loads] Table. Filter Status Paid
        $("select[name='LoadsSearch[status]']").selectOption("Paid");
        SelenideElement rowLoadsStatusPaid = $$("table.table-striped tbody tr").get(0);
        rowLoadsStatusPaid.shouldHave(text("Paid"));
        $("select[name='LoadsSearch[status]']").selectOption("");
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter Canceled load Yes
        $("select[name='LoadsSearch[canceled_load]']").selectOption("Yes");
        SelenideElement rowLoadsCanceledLoad = $$("table.table-striped tbody tr").get(0);
        rowLoadsCanceledLoad.shouldHave(text("Yes"));

        // [Loads] Table. Filter Canceled load No
        $("select[name='LoadsSearch[canceled_load]']").selectOption("No");
        SelenideElement rowLoadsCanceledLoadNo = $$("table.table-striped tbody tr").get(0);
        rowLoadsCanceledLoadNo.shouldNotHave(text("Yes"));
        $("select[name='LoadsSearch[canceled_load]']").selectOption("");
        $$("tbody tr").shouldHave(CollectionCondition.sizeGreaterThan(1));

        // [Loads] Table. Filter All
        $("input[name='LoadsSearch[our_pro_number]']").shouldBe(visible).setValue(atLoadNumber).pressEnter();
        $("input[name='LoadsSearch[truck_number]']").shouldBe(visible).setValue(atTruck).pressEnter();
        $("input[name='LoadsSearch[driver_name]']").shouldBe(visible).setValue(atDriver).pressEnter();
        $("input[name='LoadsSearch[customer]']").shouldBe(visible).setValue(atCustomer).pressEnter();
        $("input[name='LoadsSearch[city_state_day_from]']").shouldBe(visible).setValue(atZipShippersPick).pressEnter();
        $("input[name='LoadsSearch[city_state_day_to]']").shouldBe(visible).setValue(atZipShippersDrop).pressEnter();
        $("input[name='LoadsSearch[rate]']").shouldBe(visible).setValue(atRate).pressEnter();
        $("select[name='LoadsSearch[status]']").selectOption("En Route");
        $("select[name='LoadsSearch[canceled_load]']").selectOption("No");

        // [Loads] Table. Filter All check data
        SelenideElement rowLoadsAllFilter = $$("table.table-striped tbody tr").get(0);
        $$("tbody tr").shouldHave(size(1));
        rowLoadsAllFilter.shouldHave(text(atLoadNumber));
        rowLoadsAllFilter.shouldHave(text(atTruck));
        rowLoadsAllFilter.shouldHave(text(atDriver));
        rowLoadsAllFilter.shouldHave(text(atCustomer));
        rowLoadsAllFilter.shouldHave(text(atShippersPickName));
        rowLoadsAllFilter.shouldHave(text(atZipShippersDropName));
        rowLoadsAllFilter.shouldHave(text(atRate));
        rowLoadsAllFilter.shouldHave(text("En Route"));
    }

    @AfterMethod(alwaysRun = true)
    void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
