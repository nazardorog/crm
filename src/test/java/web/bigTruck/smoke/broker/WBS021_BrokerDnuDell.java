package web.bigTruck.smoke.broker;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_5;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS021_BrokerDnuDell {

    // Click Up:
    // CRM SEMI Truck
    // Brockers
    // 4. Remove from DNU

    // Global data
    String globalMC = GlobalGenerateName.globalMC();
    String globalName = GlobalGenerateName.globalName();
    String globalPhoneNumber = GlobalGenerateName.globalPhoneNumber();
    String globalMail = GlobalGenerateName.globalMail();

    @Test
    public void dnuDell() {

        // Login
        GlobalLogin.login("bt_disp1");

        // Data for creating a Broker
        final String mcNumber = globalMC;
        final String dbaName = globalName + "DBA Name";
        final String legalName = globalName + "Legal Name";
        final String entityType = "BROKER";
        final String address = "Mountain";
        final String city = "Colorado";
        final String setCreditLimit = globalMC;
        final String note = globalName + "Note";
        final String mainName = globalName + "Main Name";
        final String mail = globalMail;
        final String mainPhoneNumber = globalPhoneNumber;
        final String ffName = globalName + "2nd MC/FF Name";
        final String agentName = globalName + "Agent Name";
        final String agentLastName = globalName + "Agent Last Name";
        final String agentMail = "AgentMail_" + globalMail;
        final String agentPhoneNumber = globalPhoneNumber + "01";
        final String agentCellPhone = globalPhoneNumber + "02";

        // Create new load
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        // Відкриває створення брокера, клац по "+" біля поля Broker
        $("#new_broker").shouldBe(visible, Duration.ofSeconds(30)).click();

        // Фрейм Add Broker вкладка General
        $("#brokers-mc_number")
                .shouldBe(visible, Duration.ofSeconds(30))
                .shouldBe(enabled)
                .setValue(mcNumber);
        $("#brokers-dba_name").setValue(dbaName);
        $("#brokers-name").setValue(legalName);
        $("#brokers-entity_type").shouldBe(visible).selectOption(entityType);
        $("#brokers-second_mc_number").setValue(ffName);
        $("#brokers-address").setValue(address);
        $("#brokers-city").setValue(city);
        $("#brokers-custom_limit").setValue(setCreditLimit);
        $("#brokers-note").setValue(note);

        // Фрейм Add Broker вкладка Contact
        $(byText("Contact")).click();
        $("#brokers-main_name").shouldBe(visible).setValue(mainName);
        $("#brokers-main_phone_number").setValue(mainPhoneNumber);
        $("#brokers-main_email").setValue(mail);

        // Фрейм Add Broker вкладка Agents
        $(byText("Agents")).click();
        $("#agents-0-name").setValue(agentName);
        $("#agents-0-last_name").setValue(agentLastName);
        $("#agents-0-email").setValue(agentMail);
        $("#agents-0-phone_number").setValue(agentPhoneNumber);
        $("#agent-cell_phone-update0").setValue(agentCellPhone);

        // Фрейм Add Broker приховуємо warning блок
        SelenideElement warningBlock = $(".has-success .warning-block-wrapper");
        executeJavaScript("arguments[0].style.display='none';", warningBlock);

        // Фрейм Add Broker кнопка Submit, закриття фрейму Add broker
        $("#add_broker_send").shouldBe(clickable).click();
        $("#add_broker").shouldNotBe(visible, EXPECT_GLOBAL);

        // Перевіряє створеного брокера в полі Broker
        $("#add_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#select2-broker_search-container").shouldBe(text(legalName + " | " + dbaName));

        // Фрейм New load вибирає Агента в полі Select Agent
        $("#select2-broker-agent-load-select-container").shouldBe(visible).click();
        $(".select2-search__field").setValue(agentName);
        $$(".select2-results__options")
                .findBy(text(agentName))
                .click();

        // Закриває фрейм New load
        $(".modal-new-load-bigtrucks .close").click();
        $(".modal-new-load-bigtrucks").shouldNotBe(visible, Duration.ofSeconds(10));

        // Переходить на список брокерів
        $(".brokers-user").shouldBe(visible, Duration.ofSeconds(10)).hover();
        $(".brokers-user").click();
        $("body").click();

        // Шукає брокера по MC
        $("#brokerssearch-mc_number").setValue(mcNumber).pressEnter();

        // Перевіряє що брокера знайдено
        SelenideElement rowBroker = $$("table.table-brokers-style-profile tbody tr")
                .findBy(text(mcNumber))
                .shouldBe(visible);

        // Перевіряє що DNU не встановлено для брокера
        rowBroker.$("td", 8).shouldHave(text("Active"));

        // Праве меню вибирає Add to DNU
        rowBroker.$(".btn-action-more").click();
        $$(".dropdown-menu-right a").findBy(text("Add to DNU")).shouldBe(visible, enabled).click();

        // Фрейм DNU встановлює DNU для брокера
        $("#broker_dnu_modal .bootstrap-dialog-title").shouldBe(visible);
        $("#category-dropdown").selectOption("4 - Dry Van Only");
        $("#category-dropdown").getSelectedOption().shouldHave(Condition.text("4 - Dry Van Only"), Duration.ofSeconds(10));
        $("#brokers-comment").shouldBe(visible, EXPECT_5).hover();
        $("#brokers-comment").setValue("DNU reason massage").shouldHave(enabled, Duration.ofSeconds(10)).pressEnter();
        $("#brokers-comment").shouldHave(value("DNU reason massage"), Duration.ofSeconds(10));

        // Закриває фрейм DNU
        $("#broker_from_dnu_send").click();
        $("#brokers-blacklist-form").shouldNotBe(visible, EXPECT_GLOBAL);

        // Перевіряє що DNU встановлено для брокера
        rowBroker.$("td", 8).shouldHave(text("DNU"));

        // Праве меню вибирає Remove from DNU
        rowBroker.$(".btn-action-more").click();
        $(".remove_broker_dnu").shouldBe(visible, enabled).click();

        // Фрейм DNU вводить Reason
        $("#brokers-blacklist-form").shouldBe(visible, Duration.ofSeconds(30));
        $("#note").setValue("DNU delete reason massage");
        $("#broker_from_dnu_send").click();
        $("#brokers-blacklist-form").shouldNotBe(visible);

        // Перевіряє що DNU не встановлено для брокера
        rowBroker.$("td", 8).shouldHave(text("Active"));
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
