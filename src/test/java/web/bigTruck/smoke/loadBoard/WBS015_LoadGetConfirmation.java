package web.bigTruck.smoke.loadBoard;

import utilsWeb.commonWeb.*;
import utilsWeb.configWeb.*;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utilsWeb.configWeb.GlobalTimePeriods.EXPECT_GLOBAL;

public class WBS015_LoadGetConfirmation {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 15. Actions - Get Loads Confirmation

    String agent = "Auto test agent";

    @Test
    public void getConfirmation() throws InterruptedException, IOException {

        // Login
        GlobalLogin.login("bt_disp1");

        // Створює новий вантаж
        $("#new_load").shouldBe(enabled, EXPECT_GLOBAL).click();

        // Broker
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

        // Input other data
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

        // Load file
        $(".load_documents_counter-flex").click();
        $("#select2-loaddocuments-0-type-container").click();
        $$(".select2-results__option").findBy(text("Rate confirmation")).click();

        String filePath;
        if (new File("/.dockerenv").exists()) {
            filePath = "/app/Empire/1pdf.pdf";  // для Docker
        } else {
            filePath = Configuration.downloadsFolder + "1pdf.pdf";  // для локально
        }
        File file = new File(filePath);

        $("#loaddocuments-0-file").uploadFile(file);
        $("#load_documents_modal_pseudo_submit").click();

        // Вкладка Origin & Destination
        $("#origin-destination-tab").click();

        // Origin Shippers
        $("#select2-shippers-receiver-origin-container").shouldBe(enabled).click();
        $(".select2-search__field").setValue("Auto test shipper 1");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 1"))
                .click();

        // Destination Shippers
        $("#select2-shippers-receiver-destination-container").shouldBe(enabled).click();
        $(".select2-search__field").setValue("Auto test shipper 2");
        $$("li.select2-results__option")
                .findBy(text("Auto test shipper 2"))
                .click();

        // Calendar Origin Shippers Date from
        $("#loadspickuplocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(1);

        // Calendar Origin Shippers Date to
        $("#loadspickuplocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(2);

        // Calendar Destination Shippers Date from
        $("#loadsdeliverylocations-0-date_from-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(3);

        // Calendar Destination Shippers Date to
        $("#loadsdeliverylocations-0-date_to-datetime .kv-datetime-picker").click();
        Calendar.setDateTime(4);

        // Pallets Origin Shippers
        $("#loadspickuplocations-0-pallets").setValue("1");
        $("#loadspickuplocations-0-weight").setValue("1");
        $("#loadspickuplocations-0-pcs").setValue("1");

        // Pallets Destination Shippers
        $("#loadsdeliverylocations-0-pallets").setValue("1");
        $("#loadsdeliverylocations-0-weight").setValue("1");
        $("#loadsdeliverylocations-0-pcs").setValue("1");

        // Click по кнопці "Submit & Dispatch" на фрейм New Load
        $("#add_load_send_dispatch").click();

        // Dispatch board
        $("#view_load").shouldBe(visible, EXPECT_GLOBAL);
        $("#view_load").shouldBe(text("Dispatch #"));

        // Отримує номер вантажу
        String loadNumber = $("#view_load .check_call_pro").getText();

        // Click add Driver
        $("a[title='Add Driver'] .glyphicon.icon-plus-load").click();

        // Вибирає Carrier
        $("#select2-carrierId-container").click();
        $$(".select2-results__option").findBy(text("AutoTestOwner1 INC")).click();
        $("#select2-carrierId-container").shouldHave(text("AutoTestOwner1 INC"));

        // Вибирає Truck
        $("#select2-trucks-template-container").click();
        $(".select2-search__field").setValue("0305");
        $$(".select2-results__option").findBy(text("0305 (AutoTestOwner1 INC)")).click();
        $("#select2-trucks-template-container").shouldHave(text("0305 (AutoTestOwner1 INC)"));

        // Вибирає Driver
        $("#select2-load_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver3 Big Truck")).click();
        $("#select2-load_driver_id-container").shouldHave(text("Auto Test Driver3 Big Truck"));

        // Вибирає Team Driver
        $("#select2-load_team_driver_id-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("Auto Test Driver4 Big Truck")).click();
        $("#select2-load_team_driver_id-container").shouldHave(text("Auto Test Driver4 Big Truck"));

        // Вибирає Trailer
        $("#select2-trailer_id-create-container").click();
        $(".select2-search__field").setValue("Auto");
        $$(".select2-results__option").findBy(text("AutoTest Trailer1")).click();
        $("#select2-trailer_id-create-container").shouldHave(text("AutoTest Trailer1"));

        // Вибирає Location From вводить Location To
        $("#loadexpenses-location").selectOption("Kansas City, MO 64110");
        $("#loadexpenses-location_to").setValue("New York, NY 10002");

        // Вибирає Start Date
        $(".kv-datetime-picker").click();
        Calendar.setDateTime(0);

        // Click по Submit фрейм Add driver
        $("#update_load_driver_send").click();
        $("#add_driver").shouldNotBe(visible, Duration.ofSeconds(20));

        // Закриває модальне вікно Dispatch Load
        $("#toast-container").shouldNotBe(visible, Duration.ofSeconds(20));
        $(".load-info-modal-dialog .close").shouldBe(enabled, Duration.ofSeconds(10)).click();

        // Перевіряє що вантаж відображається на Loads en Route
        $(".logo-mini-icon").shouldBe(enabled, Duration.ofSeconds(30));
        $$("#loadTabs .updated-tabs-name-link").findBy(text("Loads en Route")).click();
        $("input[name='LoadsSearch[our_pro_number]']").setValue(loadNumber).pressEnter();
        $("a.view_load").shouldBe(text(loadNumber));

        // Відкриває меню і вибирає Get load confirmation
        Thread.sleep(1000);
        $("#trucks_en_route .dropdown-toggle").shouldBe(enabled).click();
        $$(".dropdown-menu-right li").findBy(text("Get load confirmation")).shouldBe(enabled, Duration.ofSeconds(10)).click();

        // Перевіряє прев"ю
        $(".modal-view-pdf .bootstrap-dialog-title").shouldHave(text("Load confirmation Trip#" + loadNumber));
        $(".Sheet").shouldHave(text("Empire National Inc 4600 Hendersonville Rd St. D_1 FLETCHER, NC 28732_1"));

        // Чекає завантаження файлу 10 секунд
        downloadFile();
    }

    private static void downloadFile() throws IOException {

        // Папка завантаження
        String downFolder = Configuration.downloadsFolder;
        String downFolderAfter = Configuration.downloadsFolder + "test-download/";
        String fileNameTo = "dompdf_out.pdf";
        String fileNameAfter = "WBS015.pdf";

        // Перевіряє перед завантаженням наявність файлу, якщо є видаляє його
        Path filePath = Paths.get(downFolderAfter + fileNameAfter);
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Завантажує файл
        $("#get_pdf").shouldBe(visible,enabled).click();

        File downloadedPdf = null;
        int attempts = 0;
        int maxAttempts = 20; // 20 * 500мс = 10 секунд
        while (attempts < maxAttempts) {
            try {
                Optional<Path> found = Files.walk(Paths.get(downFolder)) // Пошук завантаженого файлу
                        .filter(p -> p.toString().endsWith(fileNameTo))
                        .findFirst();

                if (found.isPresent()) {
                    Path source = found.get(); // Шлях файлу
                    Path tempFolder = source.getParent(); // Шлях тимчасової папки
                    Path toDir = Paths.get(downFolderAfter); // Шлях папки для переміщення
                    Path file = toDir.resolve(fileNameAfter); // Нове ім"я файлу

                    // Створюємо папку, якщо не існує
                    Files.createDirectories(toDir);

                    // Переміщуємо файл
                    Files.move(source, file, StandardCopyOption.REPLACE_EXISTING);

                    // Видаляє тимчасову папку
                    Files.delete(tempFolder);

                    downloadedPdf = file.toFile();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            Selenide.sleep(500); // чекаємо 0.5 секунди
            attempts++;
        }

        if (downloadedPdf == null) {
            throw new FileNotFoundException("Файл Load confirmation не був завантажений за 10 секунд");
        }
        else {
            assertThat(downloadedPdf.length()).isGreaterThan(0);
            System.out.println("Файл Load confirmation успішно завантажений");
        }
    }

    @AfterMethod(alwaysRun = true)
    public void closeWebDriver() {
        CloseWebDriver.tearDown();
    }
}
