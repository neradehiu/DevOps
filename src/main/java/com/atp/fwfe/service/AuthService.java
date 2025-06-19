package com.atp.fwfe.service;

import com.atp.fwfe.dto.AdminCreateUserRequest;
import com.atp.fwfe.dto.RegisterRequest;
import com.atp.fwfe.model.Account;
import com.atp.fwfe.repository.AccRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AccRepository accRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AccRepository accRepository, PasswordEncoder passwordEncoder){
        this.accRepository = accRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> register(RegisterRequest request) {
        if (accRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username đã tồn tại!");
        }

        if (accRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng!");
        }

        Account account = new Account(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                "ROLE_USER"
        );

        accRepository.save(account);
        return ResponseEntity.ok("Đã đăng ký thành công tài khoản cho: " + request.getUsername());

    }


    public ResponseEntity<String> createUser(AdminCreateUserRequest request) {
        if (accRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username đã tồn tại!");
        }

        if (accRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email đã được sử dụng!");
        }

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setRole(request.getRole());

        accRepository.save(account);
        return ResponseEntity.ok("Đã đăng ký thành công tài khoản cho: " + request.getUsername());
    }




}
