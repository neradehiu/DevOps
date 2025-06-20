package com.atp.fwfe.controller;

import com.atp.fwfe.dto.AuthRequest;
import com.atp.fwfe.dto.AuthResponse;
import com.atp.fwfe.dto.RegisterRequest;
import com.atp.fwfe.model.Account;
import com.atp.fwfe.repository.AccRepository;
import com.atp.fwfe.service.AuthService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/user/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request){
        return authService.login(request);
    }

    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }

    @GetMapping("/user/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        return authService.validateToken(token);
    }

}
