import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;

public class UserCredentialsTest {

    private static User user;
    private UserClient userClient;
    String token;

    @Before
    public void setUp() {
        user = User.getNewUser();
        userClient = new UserClient();
    }

    @Test
    @DisplayName("Authorized user can change password")
    @Description("success = true :: statusCode(200)")
    public void checkUserPasswordChangedTest_Positive() {
        userClient.create(user);
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        token = login.extract().path("accessToken");
        ValidatableResponse info = userClient.userChangeInfo(token, UserCredentials.getPassword());

        info.assertThat().statusCode(200);
        info.assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Authorized user can change email")
    @Description("success = true :: statusCode(200)")
    public void checkUserEmailChangedTest_Positive() {
        userClient.create(user);
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        token = login.extract().path("accessToken");
        ValidatableResponse info = userClient.userChangeInfo(token, UserCredentials.getEmail());

        info.assertThat().statusCode(200);
        info.assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Unauthorized user can not change password")
    @Description("success = false :: statusCode(401)")
    public void checkUnauthorizedUserPasswordChangedTest_Negative() {
        token = "";
        ValidatableResponse info = userClient.userChangeInfo(token, UserCredentials.getPassword());

        info.assertThat().statusCode(401);
        info.assertThat().body("success", equalTo(false));
        info.assertThat().body("message", equalTo("You should be authorised"));
    }

    @Test
    @DisplayName("Unauthorized user can not change email")
    @Description("success = false :: statusCode(401)")
    public void checkUnauthorizedUserEmailChangedTest_Negative() {
        token = "";
        ValidatableResponse info = userClient.userChangeInfo(token, UserCredentials.getEmail());

        info.assertThat().statusCode(401);
        info.assertThat().body("success", equalTo(false));
        info.assertThat().body("message", equalTo("You should be authorised"));
    }
}