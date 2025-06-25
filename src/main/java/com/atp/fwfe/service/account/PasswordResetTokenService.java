package com.atp.fwfe.service.account;

import com.atp.fwfe.model.Account;
import com.atp.fwfe.model.PasswordResetToken;
import com.atp.fwfe.repository.AccRepository;
import com.atp.fwfe.repository.PasswordResetTokenRepository;
import com.atp.fwfe.service.mailer.MailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenService {

    @Autowired
    private AccRepository accRepository;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> sendResetPasswordLink(String email) throws MessagingException{
        Optional<Account> optional = accRepository.findByEmail(email);
        if(optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email không tồn tại hoặc chưa đăng ký tài khoản:((");
        }

        Account account = optional.get();

        passwordResetTokenRepository.deleteByAccount(account);

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, account, Duration.ofMinutes(15));
        passwordResetTokenRepository.save(resetToken);

        String resetLink = "https://my-frontend.com/reset-password?token=" + token;
        String subject = "Yêu cầu đặt lại mật khẩu";
        String html = """
                        <div style="font-family:Arial; color:#333;">
                          <h2>Yêu cầu đặt lại mật khẩu</h2>
                          <p>Click vào nút bên dưới để đặt lại mật khẩu (hết hạn sau 15 phút):</p>
                          <a href="%s" style="padding:10px 20px; background-color:#2d8cf0; color:white; text-decoration:none;">Đặt lại mật khẩu</a>
                        </div>
                      """.formatted(resetLink);

        mailService.sendHtml(email, subject, html);

        return ResponseEntity.ok("Link đặt lại mật khẩu đã được gửi về email người dùng!");
    }


    public ResponseEntity<?> resetPassword(String token, String newPassword){
        Optional<PasswordResetToken> optionalToken = passwordResetTokenRepository.findByToken(token);
        if(optionalToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token không hợp lệ!");
        }

        PasswordResetToken resetToken = optionalToken.get();

        if(resetToken.isExpired()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Token đã hết hạn!\n Vui lòng nhấp 'Quên mật khẩu' lần nữa để được cấp token mới!");
        }

        Account account = resetToken.getAccount();
        account.setPassword(passwordEncoder.encode(newPassword));
        accRepository.save(account);

        passwordResetTokenRepository.delete(resetToken);

        return ResponseEntity.ok("Đã cập nhật mật khẩu mới!");
    }


}
