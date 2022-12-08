package site.nomoreparties.stellarburgers;

public class Token {
    public static String accessToken;
    public static String refreshToken;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        Token.accessToken = accessToken;
    }

    public static String getRefreshToken() {
        return refreshToken;
    }

    public static void setRefreshToken(String refreshToken) {
        Token.refreshToken = refreshToken;
    }
}