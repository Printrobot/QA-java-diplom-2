import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CreateOrderTest {
    private User user;
    private UserClient userClient;
    private Ingredients ingredients;
    public Order order;
    String token;

    @Before
    public void setUp() {
        user = User.getNewUser();
        userClient = new UserClient();
        ingredients = Ingredients.getBurger();
        order = new Order();
    }

    @Test
    @DisplayName("Create Order Registered User")
    @Description("/orders :: statusCode(200)")
    public void checkCreateOrderRegisteredUser (){
        ValidatableResponse userResponse = userClient.create(user);
        token = userResponse.extract().path("accessToken");
        ValidatableResponse orderResponse = order.create(token, ingredients);

        int orderNumber = orderResponse.extract().path("order.number");
        orderResponse.assertThat().statusCode(200);
        orderResponse.assertThat().body("success", equalTo(true));
        assertThat("The order number is missing", orderNumber, notNullValue());
    }

    @Test
    @DisplayName("Create Order Unregistered User")
    @Description("/orders :: statusCode(200)")
    public void checkCreateOrderUnregisteredUser (){
        token = "";
        ValidatableResponse orderResponse = order.create(token, ingredients);

        int orderNumber = orderResponse.extract().path("order.number");
        orderResponse.assertThat().statusCode(200);
        orderResponse.assertThat().body("success", equalTo(true));
        assertThat("The order number is missing", orderNumber, notNullValue());
    }

    @Test
    @DisplayName ("Create Order Without Ingredients")
    @Description("Ingredient ids must be provided :: /orders :: statusCode(400)")
    public void checkCreateOrderWithoutIngredients (){
        ValidatableResponse userResponse = userClient.create(user);
        token = userResponse.extract().path("accessToken");

        ValidatableResponse orderResponse = order.create(token, Ingredients.getWithoutIngredients());
        orderResponse.assertThat().statusCode(400);
        orderResponse.assertThat().body("success", equalTo(false));
        orderResponse.assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName ("Create Order With Incorrect Ingredients")
    @Description("/orders :: statusCode(500)")
    public void checkCreateOrderWithIncorrectIngredients (){
        ValidatableResponse userResponse = userClient.create(user);
        token = userResponse.extract().path("accessToken");

        ValidatableResponse orderResponse = order.create(token, Ingredients.getIncorrectIngredients());
        orderResponse.assertThat().statusCode(500);
    }
}