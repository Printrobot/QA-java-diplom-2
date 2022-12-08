package site.nomoreparties.stellarburgers;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import site.nomoreparties.stellarburgers.response.UserClient;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTest {
    private UserClient userClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @Test
    @DisplayName("check site.nomoreparties.stellarburgers.User Registration")
    @Description("/auth/register :: success = true :: statusCode(200)")
    public void checkUserRegistration(){
        User user = User.getNewUser();

        ValidatableResponse validatableResponse = userClient.create(user);

        validatableResponse.assertThat().statusCode(SC_OK);
        validatableResponse.assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Check user can not registered twice")
    @Description("/auth/register :: success = false :: message = User already exists :: statusCode(403)")
    public void checkUserRegistrationTwice(){
        User user = User.getNewUser();

        userClient.create(user);
        ValidatableResponse validatableResponse = userClient.create(user);

        validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("User already exists"));
    }
    @Test
    @DisplayName("Check user can not registered  without password")
    @Description("/auth/register :: success = false :: message = Email, password and name are required fields :: 403")
    public void checkUserRegistrationWithoutPassword() {
        User user = User.getUserWithoutPassword();

        ValidatableResponse validatableResponse = userClient.create(user);

        validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("check user can not registered without name")
    @Description("/auth/register :: success = false :: message = Email, password and name are required fields :: 403")
    public void checkUserRegistrationWithoutName() {
        User user = User.getUserWithoutName();

        ValidatableResponse validatableResponse = userClient.create(user);

        validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("check user can not registered without email")
    @Description("/auth/register :: success = false :: message = Email, password and name are required fields :: 403")
    public void checkUserRegistrationWithoutEmail() {
        User user = User.getUserWithoutEmail();

        ValidatableResponse validatableResponse = userClient.create(user);

        validatableResponse.assertThat().statusCode(SC_FORBIDDEN);
        validatableResponse.assertThat().body("success", equalTo(false));
        validatableResponse.assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}