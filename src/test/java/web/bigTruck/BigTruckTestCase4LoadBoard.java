package web.bigTruck;

import org.testng.annotations.Test;

import java.time.LocalDateTime;

public class BigTruckTestCase4LoadBoard {

    // Click Up:
    // CRM SEMI Truck
    // Load board
    // 3. Перевод груза с available load в еn routе

    LocalDateTime now = LocalDateTime.now();
    int currentDay = now.getDayOfMonth();
    int hour = now.getHour();
    int minute = (now.getMinute() / 5) * 5;

    @Test
    public void availableCargoToEnRout () throws InterruptedException {}
}
