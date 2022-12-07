import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {

    public String email;
    public String password;

    public UserCredentials() {}

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.email, user.password);
    }

    public UserCredentials setEmail (String email){
        this.email = email;
        return this;
    }

    public UserCredentials setPassword(String password) {
        this.password = password;
        return this;
    }

    public static UserCredentials getEmail() {
        final String email = (RandomStringUtils.randomAlphabetic(8) + "@ya.ru").toLowerCase();
        return new UserCredentials().setEmail(email);
    }

    public static UserCredentials getPassword() {
        final String password = RandomStringUtils.randomAlphabetic(8).toLowerCase();
        return new UserCredentials().setPassword(password);
    }
}