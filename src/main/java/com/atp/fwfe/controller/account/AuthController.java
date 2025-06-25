package com.atp.fwfe.controller.account;

import com.atp.fwfe.dto.account.login.LoginRequest;
import com.atp.fwfe.dto.account.login.LoginResponse;
import com.atp.fwfe.dto.account.register.RegisterRequest;
import com.atp.fwfe.service.account.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request){
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
