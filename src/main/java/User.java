import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    public final String email;
    public final String password;
    public final String name;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    @Step("Create correct user")
    public static User getNewUser() {

        final String email = (RandomStringUtils.randomAlphabetic(10) + "@ya.ru").toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(10).toLowerCase();

        return new User(email, password, name);
    }

    @Step("Create user without name")
    public static User getUserWithoutName() {

        final String email = RandomStringUtils.randomAlphabetic(10) + "@ya.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);

        return new User(email, password, null);
    }

    @Step("Create user without password")
    public static User getUserWithoutPassword() {

        final String email = RandomStringUtils.randomAlphabetic(10) + "@ya.ru";
        final String name = RandomStringUtils.randomAlphabetic(10);

        return new User(email, null, name);
    }

    @Step("Create user without email")
    public static User getUserWithoutEmail() {

        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);

        return new User(null, password, name);
    }
}