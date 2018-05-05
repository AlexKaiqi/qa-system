package com.alex.qasystem.util;

public class ValidationUtil {

    public static boolean isValidEmail(String email) {
        return email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^[^\r\n]{5,45}$");
    }

    public static boolean isValidProfileName(String profileName) {
        return profileName.matches("^[\\p{L} \\.'\\-]{3,45}$");
    }

    public static boolean isValidQuestionTagTitle(String tagTitle) {
        return tagTitle.matches("[\\w -]{3,15}");
    }

    public static boolean isValidQuestionTitle(String title) {
        return title.matches(".{10,150}");
    }


}
