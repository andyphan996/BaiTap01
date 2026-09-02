package iotstar.vn.util;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Gửi email qua SMTP (mặc định cấu hình sẵn cho Gmail SMTP).
 * Cấu hình được đọc từ file classpath:META-INF/mail.properties (không commit lên git,
 * xem file mail.properties.example để biết cách tạo).
 */
public class MailUtil {

    private static final Properties CONFIG = new Properties();
    private static boolean loaded = false;

    private static synchronized void loadConfig() {
        if (loaded) return;
        try (InputStream in = MailUtil.class.getClassLoader()
                .getResourceAsStream("mail.properties")) {
            if (in == null) {
                throw new RuntimeException(
                    "Không tìm thấy file mail.properties trong classpath (src/main/resources). " +
                    "Hãy copy mail.properties.example thành mail.properties và điền thông tin SMTP.");
            }
            CONFIG.load(in);
            loaded = true;
        } catch (IOException e) {
            throw new RuntimeException("Lỗi đọc mail.properties: " + e.getMessage(), e);
        }
    }

    private static Session buildSession() {
        loadConfig();

        String host = CONFIG.getProperty("mail.smtp.host", "smtp.gmail.com");
        String port = CONFIG.getProperty("mail.smtp.port", "587");
        String username = CONFIG.getProperty("mail.smtp.username");
        String password = CONFIG.getProperty("mail.smtp.password");

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        return Session.getInstance(props, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Gửi email dạng text thuần.
     */
    public static void send(String toEmail, String subject, String body) {
        try {
            loadConfig();
            String from = CONFIG.getProperty("mail.smtp.username");
            String fromName = CONFIG.getProperty("mail.from.name", "IoTStar");

            Session session = buildSession();
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, fromName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject, "UTF-8");
            message.setText(body, "UTF-8");

            Transport.send(message);
        } catch (MessagingException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Gửi email thất bại: " + e.getMessage(), e);
        }
    }

    /** Gửi email OTP kích hoạt tài khoản. */
    public static void sendActivationOtp(String toEmail, String otp) {
        String subject = "Xác thực tài khoản - Mã OTP của bạn";
        String body = "Chào bạn,\n\n"
                + "Mã OTP để kích hoạt tài khoản của bạn là: " + otp + "\n"
                + "Mã có hiệu lực trong " + OtpUtil.OTP_VALID_MINUTES + " phút.\n\n"
                + "Nếu bạn không thực hiện đăng ký này, vui lòng bỏ qua email.";
        send(toEmail, subject, body);
    }

    /** Gửi email OTP quên mật khẩu. */
    public static void sendResetPasswordOtp(String toEmail, String otp) {
        String subject = "Đặt lại mật khẩu - Mã OTP của bạn";
        String body = "Chào bạn,\n\n"
                + "Mã OTP để đặt lại mật khẩu của bạn là: " + otp + "\n"
                + "Mã có hiệu lực trong " + OtpUtil.OTP_VALID_MINUTES + " phút.\n\n"
                + "Nếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.";
        send(toEmail, subject, body);
    }
}
