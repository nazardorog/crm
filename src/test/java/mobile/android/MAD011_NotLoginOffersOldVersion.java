package mobile.android;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utilsMobile.configMobile.GlobalLoginMob;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static utilsMobile.configMobile.GlobalLoginMob.getPassword;
import static utilsMobile.configMobile.GlobalLoginMob.getUsername;

public class MAD011_NotLoginOffersOldVersion {

    GlobalLoginMob openApp = new GlobalLoginMob();
    private static final Dotenv dotenv = Dotenv.load();

    @Test
    public void statusChange() {

        // Отримання токен
        String url = dotenv.get("WEB_SITE");
        RestAssured.baseURI = url.substring(0, url.lastIndexOf("/"));

        // Дані для авторизації
        GlobalLoginMob.credentials();
        String login = getUsername("driver_mob1");
        String password = getPassword("driver_mob1");

        String mobileOs = "1";
        String imei = "1111";
        String appVersionToken = "615";

        // Запит на сервер авторизації
        Response loginResponse = given()
                .contentType(ContentType.JSON)
                .body("""
                {
                    "login": "%s",
                    "password": "%s",
                    "mobile_os": "%s",
                    "imei": "%s",
                    "appVersionToken": "%s"
                }
                """.formatted(login, password, mobileOs, imei, appVersionToken))
                .when()
                .post("/api/driver/login")
                .then()
                .statusCode(200)
                .body("data.access_token", notNullValue())
                .extract()
                .response();

        // Отримує токен
        String accessToken = loginResponse.jsonPath().getString("data.access_token");

        loginOffers(accessToken);
    }

    public void loginOffers(String accessToken) {

        Response response = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body("""
                    {
                      "page": 1,
                      "itemView": 20,
                      "appVersionToken": 383,
                      "status": [1, 2]
                    }
                    """)
                .when()
                .post("/api/offers");

        // Перевірка статусу
        response.then().statusCode(405);

        // Вивід відповіді
        response.prettyPrint();
        String message = response.jsonPath().getString("message");
        assertThat("Текст повідомлення не збігається!", message, equalTo("You need to update the app"));
        System.out.println("Тест. Необхідно оновити додаток. Текст відповіді сервера: " + message);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (openApp.driverAndroid != null) {
            openApp.driverAndroid.quit();
        }
    }
}
