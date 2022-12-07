import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends BaseAPI {
    private static final String ENDPOINT_USER = "auth";

    @Step("User create")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpecification())
                .body(user)
                .when()
                .post(ENDPOINT_USER + "/register")
                .then();
    }

    @Step("User authorisation")
    public ValidatableResponse login (UserCredentials credentials) {
        return given()
                .spec(getSpecification())
                .body(credentials)
                .when()
                .post(ENDPOINT_USER + "/login")
                .then()
                .log().body();
    }

    @Step ("User delete")
    public void delete() {
        if (Token.getAccessToken() == null) {
            return;
        }
        given()
                .spec(getSpecification())
                .auth().oauth2(Token.getAccessToken())
                .when()
                .delete(ENDPOINT_USER)
                .then()
                .statusCode(202);
    }

    @Step ("Info about user")
    public ValidatableResponse userInfo(String token) {
        return given()
                .spec(getSpecification())
                .auth().oauth2(token.replace("Bearer ", ""))
                .when()
                .get(ENDPOINT_USER + "/user")
                .then();
    }

    @Step ("Change user info")
    public ValidatableResponse userChangeInfo(String token, UserCredentials userCredentials) {
        return given()
                .spec(getSpecification())
                .auth().oauth2(token.replace("Bearer ", ""))
                .body(userCredentials)
                .when()
                .patch(ENDPOINT_USER + "/user")
                .then();
    }
}