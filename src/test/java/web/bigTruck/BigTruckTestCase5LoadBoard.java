package web.bigTruck;

import org.testng.annotations.Test;
import web.LoginUser2;

import java.time.LocalDateTime;

public class BigTruckTestCase5LoadBoard extends LoginUser2 {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 4. Перевод груза с еn routе в dеlivеrеd

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void dеlivеredCargoToInvoiced () throws InterruptedException {}

}
