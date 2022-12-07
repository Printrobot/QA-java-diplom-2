import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class Order extends BaseAPI {
    private static final String ENDPOINT_ORDER = "orders";

    @Step("Order create")
    public ValidatableResponse create (String token, Ingredients ingredients){
        return given()
                .spec(getSpecification())
                .auth().oauth2(token.replace("Bearer ", ""))
                .body(ingredients)
                .when()
                .post(ENDPOINT_ORDER)
                .then();
    }

    @Step ("List of all orders")
    public ValidatableResponse orderInfo (){
        return given()
                .spec(getSpecification())
                .when()
                .get(ENDPOINT_ORDER + "/all")
                .then();
    }

    @Step ("User list of orders")
    public ValidatableResponse userOrderInfo (String token){
        return given()
                .spec(getSpecification())
                .auth().oauth2(token.replace("Bearer ", ""))
                .when()
                .get(ENDPOINT_ORDER)
                .then();
    }
}