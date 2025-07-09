package com.atp.fwfe.controller.account;

import com.atp.fwfe.dto.account.passwordreset.ForgotPasswordRequest;
import com.atp.fwfe.dto.account.passwordreset.ResetPasswordRequest;
import com.atp.fwfe.service.account.PasswordResetTokenService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
public class ForgotPasswordController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request) throws MessagingException {
        return passwordResetTokenService.sendResetPasswordLink(request.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        return passwordResetTokenService.resetPassword(request.getToken(), request.getNewPassword());
    }
}
