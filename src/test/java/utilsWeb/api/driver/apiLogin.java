package utilsWeb.api.driver;
import static org.hamcrest.Matchers.notNullValue;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class apiLogin {

    private static final Dotenv dotenv = Dotenv.load();

    public static String login(String driver) {

        String url = dotenv.get("WEB_SITE");
        RestAssured.baseURI = url.substring(0, url.lastIndexOf("/")); // чтобы избежать хардкода url
        
        // Данные для логина
        String login = driverCredentials.getDriverLogin(driver);
        String password = driverCredentials.getDriverPassword(driver);
        String mobileOs = "2";
        String imei = "1111";
        String appVersionToken = "999";
        
        // Запрос - логин
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
            
        // Получаем access_token из ответа
        String accessToken = loginResponse.jsonPath().getString("data.access_token");

        return accessToken;
    }
}
