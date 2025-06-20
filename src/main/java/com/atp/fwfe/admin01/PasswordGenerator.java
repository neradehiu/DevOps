package com.atp.fwfe.admin01;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
    public static void main(String[] args) {
        String rawPassword = "Duc1234@";
        String encodedPassword = new BCryptPasswordEncoder().encode(rawPassword);
        System.out.println("Mật khẩu mã hóa: " + encodedPassword);
    }
}
