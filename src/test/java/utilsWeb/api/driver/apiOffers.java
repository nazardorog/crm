package utilsWeb.api.driver;
import static org.hamcrest.Matchers.notNullValue;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class apiOffers {

    public static String[] offers(String driver) {

        String accessToken = apiLogin.login(driver);

        Response offersResponse = given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + accessToken)
            .body("""
                {
                    "appVersionToken": 9999,
                    "page": 1,
                    "itemView": 20,
                    "status": [1]
                }
                """)
            .when()
            .post("/api/offers")
            .then()
            .statusCode(200)
            .body("data.offers", notNullValue())
            .extract()
            .response();
        
        String offerId = offersResponse.jsonPath().getString("data.offers[0].offer_id");
        String status = offersResponse.jsonPath().getString("data.offers[0].status");
        String puLocation = offersResponse.jsonPath().getString("data.offers[0].origins[0].location");
        String puDate = offersResponse.jsonPath().getString("data.offers[0].origins[0].date");
        String delLocation = offersResponse.jsonPath().getString("data.offers[0].destinations[0].location");
        String delDate = offersResponse.jsonPath().getString("data.offers[0].destinations[0].date");
        String truckNumber = offersResponse.jsonPath().getString("data.offers[0].truck_number");
        String miles = offersResponse.jsonPath().getString("data.offers[0].miles");
        String weight = offersResponse.jsonPath().getString("data.offers[0].weight");
        String pallets = offersResponse.jsonPath().getString("data.offers[0].pallets");
        String dims = offersResponse.jsonPath().getString("data.offers[0].dimensions");
        String lastMessage = offersResponse.jsonPath().getString("data.offers[0].lastMessageData.messages");
        
        return new String[] {
            offerId, status, puLocation, puDate, delLocation, delDate,
            truckNumber, miles, weight, pallets, dims, lastMessage
        };
    }
}
