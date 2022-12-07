import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Map;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserOrdersTest {
    private User user;
    private UserClient userClient;
    private Ingredients ingredients;
    public Order orderClient;
    String token;

    @Before
    public void setUp() {
        user = User.getNewUser();
        userClient = new UserClient();
        ingredients = Ingredients.getBurger();
        orderClient = new Order();
    }

    @Test
    @Description("Check list of orders authorized user")
    public void checkUserInfoAuthorizedUser (){
        userClient.create(user);
        ValidatableResponse login = userClient.login(UserCredentials.from(user));
        token = login.extract().path("accessToken");
        ValidatableResponse orderInfo = orderClient.userOrderInfo(token);
        List<Map<String, Object>> ordersList = orderInfo.extract().path("orders");

        orderInfo.assertThat().statusCode(200);
        orderInfo.assertThat().body("success", equalTo(true));
        assertThat("Orders list empty", ordersList, is(not(0)));
    }

    @Test
    @Description("Check list of orders unauthorized user")
    public void checkUserInfoUnauthorizedUser (){
        token = "";

        ValidatableResponse orderInfo = orderClient.userOrderInfo(token);

        orderInfo.assertThat().statusCode(401);
        orderInfo.assertThat().body("success", equalTo(false));
        orderInfo.assertThat().body("message", equalTo("You should be authorised"));
    }
}
