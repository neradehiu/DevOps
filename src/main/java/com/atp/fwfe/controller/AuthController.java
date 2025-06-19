package com.atp.fwfe.controller;

import com.atp.fwfe.dto.RegisterRequest;
import com.atp.fwfe.model.Account;
import com.atp.fwfe.repository.AccRepository;
import com.atp.fwfe.service.AuthService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
       return authService.register(request);
    }

}
