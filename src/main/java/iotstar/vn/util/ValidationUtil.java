package iotstar.vn.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private static final Pattern EMAIL = Pattern.compile("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    private static final Pattern OTP = Pattern.compile("^\\d{6}$");
    private static final Pattern PHONE = Pattern.compile("^\\+?[0-9]{10,15}$");

    private ValidationUtil() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidEmail(String value) {
        return value != null && EMAIL.matcher(value.trim()).matches();
    }

    public static boolean isValidPassword(String value) {
        return value != null && value.length() >= 6 && value.length() <= 100;
    }

    public static boolean isValidOtp(String value) {
        return value != null && OTP.matcher(value.trim()).matches();
    }

    public static boolean isValidPhone(String value) {
        return isBlank(value) || PHONE.matcher(value.trim()).matches();
    }
}