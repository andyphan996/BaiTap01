package iotstar.vn.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false)
    private String password; // đã hash

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @Column(name = "image_path", length = 255)
    private String imagePath;

    @Column(length = 20)
    private String role = "USER"; // USER / ADMIN

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    // Tài khoản chỉ dùng được sau khi xác thực OTP qua email
    @Column(nullable = false)
    private boolean enabled = false;

    // Mã OTP dùng chung cho kích hoạt tài khoản và quên mật khẩu
    @Column(name = "otp_code", length = 6)
    private String otpCode;

    // Thời điểm hết hạn của otpCode
    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

}