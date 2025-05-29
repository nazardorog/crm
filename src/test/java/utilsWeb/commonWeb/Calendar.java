package utilsWeb.commonWeb;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class Calendar {

    public static void setDateTime (int introductionDay){
        LocalDateTime now = LocalDateTime.now();
        int currentDay = now.getDayOfMonth();
        int hour = now.getHour();
        int minute = (now.getMinute() / 5) * 5;

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        // календар що зараз відкритий
        SelenideElement activeCalendar = $$(".datetimepicker").filter(Condition.visible).get(0); // перший видимий

        // Перемикає місяць
        if (switchMonth) {
            activeCalendar.$(".datetimepicker-days .next").click();
        }

        // Вибирає дату
        activeCalendar.$$(".datetimepicker-days .day:not(.old):not(.new)")
                .findBy(exactText(String.valueOf(targetDay)))
                .click();

        // Вибирає час
        activeCalendar.$$(".datetimepicker-hours .hour")
                .findBy(exactText(hour + ":00"))
                .click();
        // Вибирає хвилину
        activeCalendar.$$(".datetimepicker-minutes .minute")
                .findBy(exactText(String.format("%d:%02d", hour, minute)))
                .click();
    }

    public static void setDate(int introductionDay){

        LocalDateTime now = LocalDateTime.now();
        int currentDay = now.getDayOfMonth();

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        // календар що зараз відкритий
        SelenideElement activeCalendar = $$(".datepicker").filter(Condition.visible).get(0); // перший видимий

        // Перемикає місяць
        if (switchMonth) {
            activeCalendar.$(".datepicker-days .next").click();
        }

        // Вибирає дату
        activeCalendar.$$(".datepicker-days .day:not(.old):not(.new)").findBy(exactText(String.valueOf(targetDay))).click();

        $("body").click();
    }

    public static void setDateTimeMexico (int introductionDay){
        //поточний час по Мексиці
        LocalDateTime now = LocalDateTime.now(ZoneId.of("America/Mexico_City"));
        int currentDay = now.getDayOfMonth();
        int hourNotRounded = now.getHour();
        int hour = ((hourNotRounded + 1) / 2) * 2;
        int minute = (now.getMinute() / 5) * 5;
        LocalDateTime statusDateDriver = now.plusDays(3).withHour(hour).withMinute(minute);
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        String dateToShippersDestination = statusDateDriver.format(formatDate);

        int daysInMonth = YearMonth.of(now.getYear(), now.getMonth()).lengthOfMonth(); // к-сть днів у поточному місяці
        int targetDay = currentDay + introductionDay;//день що потрібно ввести
        boolean switchMonth = false;

        //якщо день введення більше ніж кількість днів в місяця, перемикає календар на наступний місяць
        if (targetDay > daysInMonth) {
            targetDay -= daysInMonth; // якщо виходимо за межі місяця, віднімаємо дні
            switchMonth = true;
        }

        // календар що зараз відкритий
        SelenideElement activeCalendar = $$(".datetimepicker").filter(Condition.visible).get(0); // перший видимий

        // Перемикає місяць ТІЛЬКИ в цьому календарі
        if (switchMonth) {
            activeCalendar.$(".datetimepicker-days .next").click();
        }

        // Клікає дату в цьому календарі
        activeCalendar.$$(".datetimepicker-days .day:not(.old):not(.new)")
                .findBy(exactText(String.valueOf(targetDay)))
                .click();

        // Вибирає час (тільки в активному календарі)
        activeCalendar.$$(".datetimepicker-hours .hour")
                .findBy(exactText(hour + ":00"))
                .click();

        activeCalendar.$$(".datetimepicker-minutes .minute")
                .findBy(exactText(String.format("%d:%02d", hour, minute)))
                .click();
    }
}
