package com.atp.fwfe.controller.account;

import com.atp.fwfe.dto.account.passwordreset.ForgotPasswordRequest;
import com.atp.fwfe.dto.account.passwordreset.ResetPasswordRequest;
import com.atp.fwfe.service.account.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest request){
        return passwordResetTokenService.sendResetPasswordLink(request.getEmail());
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request){
        return passwordResetTokenService.resetPassword(request.getToken(), request.getNewPassword());
    }
}
