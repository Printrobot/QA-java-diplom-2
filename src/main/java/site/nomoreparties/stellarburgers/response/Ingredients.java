package site.nomoreparties.stellarburgers.response;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class Ingredients extends BaseAPI {
    private static final String ENDPOINT_INGREDIENTS = "ingredients";
    public ArrayList<Object> ingredients;

    public Ingredients(ArrayList<Object> ingredients){
        this.ingredients = ingredients;
    }
    @Step("Create burger with ingredients")
    public static Ingredients getBurger(){

        ValidatableResponse response = given()
                .spec(getSpecification())
                .when()
                .get(ENDPOINT_INGREDIENTS)
                .then()
                .statusCode(200);

        ArrayList<Object> ingredients = new ArrayList<>();
        int bunIndex = nextInt(0,2);
        int mainIndex = nextInt(0,5);
        int sauceIndex = nextInt(0,4);

        List<Object> bunIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'bun'}._id");
        List<Object> mainIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'main'}._id");
        List<Object> sauceIngredients = response.extract().jsonPath().getList("data.findAll{it.type == 'sauce'}._id");

        ingredients.add(bunIngredients.get(bunIndex));
        ingredients.add(mainIngredients.get(mainIndex));
        ingredients.add(sauceIngredients.get(sauceIndex));
        return new Ingredients (ingredients);
    }

    @Step("Create burger without ingredients")
    public static Ingredients getWithoutIngredients() {
        ArrayList<Object> ingredients = new ArrayList<>();
        return new Ingredients(ingredients);
    }

    @Step("Create burger with incorrect ingredients")
    public static Ingredients getIncorrectIngredients() {
        ArrayList<Object> ingredients = new ArrayList<>();
        String randomIngredient = (RandomStringUtils.randomAlphabetic(3));
        ingredients.add(randomIngredient);
        return new Ingredients(ingredients);
    }
}