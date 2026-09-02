package iotstar.vn.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;

public class OtpUtil {

    private static final SecureRandom RANDOM = new SecureRandom();

    /** Số phút OTP còn hiệu lực. */
    public static final int OTP_VALID_MINUTES = 5;

    /** Sinh mã OTP gồm 6 chữ số, ví dụ "038291". */
    public static String generateOtp() {
        int number = RANDOM.nextInt(1_000_000); // 0 -> 999999
        return String.format("%06d", number);
    }

    /** Thời điểm hết hạn cho OTP vừa sinh. */
    public static LocalDateTime newExpiry() {
        return LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES);
    }

    /** Kiểm tra OTP còn hợp lệ hay không (đúng mã + chưa hết hạn). */
    public static boolean isValid(String inputOtp, String storedOtp, LocalDateTime expiry) {
        if (inputOtp == null || storedOtp == null || expiry == null) return false;
        if (!storedOtp.equals(inputOtp)) return false;
        return LocalDateTime.now().isBefore(expiry);
    }
}
