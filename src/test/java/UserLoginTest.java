import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserLoginTest {
    private UserClient userClient;
    User user = User.getNewUser();
    public String refreshToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void tearDown() {
        userClient.delete();
        user = null;
    }

    @Test
    @DisplayName("Check User Can Login")
    @Description("/auth/login :: success = true :: statusCode(200)")
    public void checkUserCanLogin(){
        userClient.create(user);
        ValidatableResponse validatableResponse = userClient.login(UserCredentials.from(user));
        refreshToken = validatableResponse.extract().path("refreshToken");

        assertThat("Courier ID incorrect", refreshToken, notNullValue());
        validatableResponse.assertThat().statusCode(200);
        validatableResponse.assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Check user can not login without email")
    @Description("/auth/login :: success = false :: message = email or password are incorrect :: statusCode(401)")
    public void checkUserLoginWithoutEmail(){
        userClient.create(user);
        ValidatableResponse validatableResponse = userClient.login(new UserCredentials(null, user.password));

        validatableResponse.assertThat().statusCode(401);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Check user can not login without password")
    @Description("/auth/login :: success = false :: message = email or password are incorrect :: statusCode(401)")
    public void checkUserLoginWithoutPassword(){
        userClient.create(user);
        ValidatableResponse validatableResponse = userClient.login(new UserCredentials (user.email,null));

        validatableResponse.assertThat().statusCode(401);
        validatableResponse.assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Check user can not login with incorrect email")
    @Description("/auth/login :: success = false :: message = email or password are incorrect :: statusCode(401)")
    public void checkLoginWithIncorrectEmail(){
        userClient.create(user);
        String randomEmail = (RandomStringUtils.randomAlphabetic(8) + "@ya.ru").toLowerCase();
        ValidatableResponse validatableResponse = userClient.login(new UserCredentials(randomEmail, user.password));

        validatableResponse.assertThat().statusCode(401);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("email or password are incorrect"));
    }

    @Test
    @DisplayName("Check user can not login with incorrect password")
    @Description("Тест /api/auth/login")
    public void checkLoginWithIncorrectPassword(){
        userClient.create(user);
        String randomPassword = RandomStringUtils.randomAlphabetic(8);
        ValidatableResponse validatableResponse = userClient.login(new UserCredentials (user.email, randomPassword));

        validatableResponse.assertThat().statusCode(401);
        validatableResponse.assertThat().body("message", equalTo("email or password are incorrect"));
    }
}