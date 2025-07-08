package utilsWeb.api.driver;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;


public class apiAcceptOffer {

    public static void acceptOffer(String driver, String offerTruckId) {

        String accessToken = apiLogin.login(driver);

        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + accessToken)
            .body("""
                {
                    "appVersionToken": 9999,
                    "offerTruckId": %s,
                    "price": 1000,
                    "pu": "Some PU",
                    "note": "Some Note"
                }
                """.formatted(offerTruckId))
            .when()
            .post("/api/offers/accepting-offer")
            .then()
            .statusCode(200);
    }
}
