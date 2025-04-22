package web.bigTruck.loadBoard;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Optional;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static web.expedite.loadBoard.TestCase17LoadBoard.clearDownloadFolder;

public class BigTruckTestCase16LoadBoard {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 16. Actions - Get BOL

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;
    String loadNumber;
    String agent = "Auto test agent";

    @Test
    public void getBol () throws InterruptedException, IOException {

        System.out.println("BigTruckTestCase15LoadBoard - Start");

        //старт браузер і авторизація
        web.config.WebDriverConfig.setup();
        web.config.LoginBigTruck.loginWeb();

        //створює новий вантаж
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $("#new_load").shouldBe(enabled).click();

        //brocker
        $("#loads-form-create").shouldBe(visible, Duration.ofSeconds(10));
        $("#select2-broker_search-container").shouldBe(visible).click();
        $(".select2-search__field").shouldBe(visible).setValue("Auto test broker");
        $$(".select2-results__options")
                .findBy(text("Auto test broker"))
                .click();
        $("#select2-broker-agent-load-select-container").click();
        $(".select2-search__field").setValue(agent);
        $$(".select2-results__options")
                .findBy(text("Auto test agent"))
                .click();

        //input other data
        Random random = new Random();
        String reference = String.format("%3d", random.nextInt(10000000));
        String commodity = String.format("%3d", random.nextInt(10000000));
        $("#loads-reference").setValue(reference);
        $("#loads-rate-disp").setValue("100000").pressEnter();
        $("#loads-commodity_big_trucks_note").setValue(commodity);
        $("#select2-booked_with-container").shouldHave(text("Auto 2Test BT"));
        $("#loads-commodity").setValue("Text Commodity Notes");
        $$("div#loads-check_full_load label").findBy(text("FTL")).click();
        $$("#loads-local_type label").findBy(text("Local")).click();
        $$("#loads-load_type label").findBy(text("Board")).click();

        //load file
        $(".load_documents_counter-flex").click();
        $("#select2-loaddocuments-0-type-container").click();
        $$(".select2-results__option").findBy(text("Rate confirmation")).click();

        File file = new File("C:/Empire/pdf1.pdf");
        $("#loaddocuments-0-file").uploadFile(file);
        $("#load_documents_modal_pseudo_submit").click();

        //вкладка Origin & Destination
        $("#origin-destination-tab").click();

        //Origin Shippers
        $("#select2-shippers-receiver-origin-container").shouldBe(enabled).click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        //Destination Shippers
        $("#select2-shippers-receiver-destination-container").shouldBe(enabled).click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        //calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(1, 0);

        //calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(2, 1);

        //calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        inputCalendar(3, 2);

        //calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        inputCalendar(4, 3);

        //pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        //pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        //відкриває фрейм Dispatch board
        $("#add_load_send_dispatch").click();
        $("#view_load").shouldBe(visible).shouldHave(text("Dispatch #"));

        //отримує номер вантажу
        loadNumber = $("#view_load .check_call_pro").getText();
        System.out.println("BigTruckTestCase15LoadBoard. Номер вантажу:" + loadNumber);

        //клік add Driver
        $("a[title='Add Driver'] .glyphicon.icon-plus-load").click();

        //вибирає Carrier
        $("#select2-carrierId-container").click();
        $$(".select2-results__option").findBy(text("AutoTestOwner1 INC")).click();
        $("#select2-carrierId-container").shouldHave(text("AutoTestOwner1 INC"));

        //вибирає Truck
        $("#select2-trucks-template-container").click();
        $(".select2-search__field").setValue("0305");
        $$(".select2-results__option").findBy(text("0305 (AutoTestOwner1 INC)")).click();
        $("#select2-trucks-template-container").shouldHave(text("0305 (AutoTestOwner1 INC)"));

        //вибирає Driver
        $("#select2-load_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver3 Big Truck")).click();
        $("#select2-load_driver_id-container").shouldHave(text("Auto Test Driver3 Big Truck"));

        //вибирає Team Driver
        $("#select2-load_team_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver4 Big Truck")).click();
        $("#select2-load_team_driver_id-container").shouldHave(text("Auto Test Driver4 Big Truck"));

        //вибирає Trailer
        $("#select2-trailer_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("AutoTest Trailer1")).click();
        $("#select2-trailer_id-create-container").shouldHave(text("AutoTest Trailer1"));

        //вибирає Location From вводить Location To
        $("#loadexpenses-location").selectOption("Kansas City, MO 64110");
        $("#loadexpenses-location_to").setValue("New York, NY 10002");

        //вибирає Start Date
        $(".kv-datetime-picker").click();
        inputCalendar(0, 0);

        //клік по Submit фрейм Add driver
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, Duration.ofSeconds(20));

        //закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        //перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30)).click();
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

        //очищає папку перед завантаженням
        String folderPath = Configuration.downloadsFolder;
        clearDownloadFolder(folderPath);

        //*** Відкриває меню і вибирає Get load confirmation ***
        Thread.sleep(1000);
        $("#trucks_en_route .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Get BOL")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        //Перевіряє прев"ю
        $(".name-bill-lading").shouldHave(text("BILL OF LADING  "));
        $("input[name ='BILL_OF_LADING_NUMBER']").shouldHave(value(loadNumber));
        $("input[name ='PickUpLocation[name]']").shouldHave(value("Auto test shipper 1"));
        $("input[name ='PickUpLocation[street1]']").shouldHave(value("Moornings Way"));
        $("input[name ='PickUpLocation[city_state_zip]']").shouldHave(value("Kansas City, MO 64110"));

        $("input[name ='DeliveryLocation[name]']").shouldHave(value("Auto test shipper 2"));
        $("input[name ='DeliveryLocation[street1]']").shouldHave(value("Cherry st"));
        $("input[name ='DeliveryLocation[city_state_zip]']").shouldHave(value("New York, NY 10002"));

        //завантажує файл
        $("#get_pdf").shouldBe(visible,enabled).click();

        // чекає завантаження файлу 10 секунд
        File downloadedPdf = null;
        int attempts = 0;
        int maxAttempts = 20; // 20 * 500мс = 10 секунд
        while (attempts < maxAttempts) {
            try {
                Optional<Path> found = Files.walk(Paths.get(folderPath))
                        .filter(p -> p.getFileName().toString().equals("dompdf_out.pdf"))
                        .findFirst();

                if (found.isPresent()) {
                    downloadedPdf = found.get().toFile();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Selenide.sleep(500); // чекаємо 0.5 секунди
            attempts++;
        }

        if (downloadedPdf == null) {
            throw new FileNotFoundException("Файл Bol не був завантажений за 10 секунд");
        }
        else {
            assertThat(downloadedPdf.length()).isGreaterThan(0);
            System.out.println("Файл Bol успішно завантажений");
        }


        System.out.println("bigTruckTestCase16LoadBoard - Test Pass");
    }

    public void inputCalendar(int introductionDay, int numberCalendar){

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        if (switchMonth) {
            Selenide.executeJavaScript("arguments[0].click();", $$(".datetimepicker-days .next").get(numberCalendar));
        }

        ElementsCollection dateElement = $$(".datetimepicker-days .day:not(.old):not(.new)");
        dateElement.findBy(exactText(String.valueOf(targetDay))).click();

        $$(".datetimepicker-hours .hour").findBy(exactText(hour + ":00")).click(); // Вибираємо годину
        $$(".datetimepicker-minutes .minute").findBy(exactText(String.format("%d:%02d", hour, minute))).click(); // Вибираємо хвилини
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        System.out.println("Tear down - close WebDriver");
        web.config.CloseWebDriver.tearDown();
    }
}
