package utilsWeb.commonWeb;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Time {

    public static String mexicoTime() {
        // Поточний час Мехіко
        LocalDateTime nowMexico = LocalDateTime.now(ZoneId.of("America/Mexico_City"));

        // Приводимо до формату "MM/dd/yyyy HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");

        return nowMexico.format(formatter);
    }
}
