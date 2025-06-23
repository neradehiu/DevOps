package com.atp.fwfe.service;

import com.atp.fwfe.dto.adrequest.AdminCreateUserRequest;
import com.atp.fwfe.dto.LoginRequest;
import com.atp.fwfe.dto.LoginResponse;
import com.atp.fwfe.dto.RegisterRequest;
import com.atp.fwfe.model.Account;
import com.atp.fwfe.repository.AccRepository;
import com.atp.fwfe.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final AccRepository accRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;


    public AuthService(AccRepository accRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager, TokenBlacklistService tokenBlacklistService){
        this.accRepository = accRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;

    }

//AUTH chung chung
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

    public ResponseEntity<LoginResponse> login(LoginRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            Account account = accRepository.findByUsername(request.getUsername()).get();
            String token = jwtUtil.generateToken(account.getUsername(), account.getRole());
            return ResponseEntity.ok(new LoginResponse(token, "Đăng nhập thành công!"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Tên đăng nhập hoặc mật khẩu không đúng!"));
        }
    }


    public ResponseEntity<String> logout(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        tokenBlacklistService.blacklistToken(token);
        return ResponseEntity.ok("Bạn đã đăng xuất tài khoản!");
    }

    public ResponseEntity<String> validateToken(String token){
        token = token.replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        if (username == null || role == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token không hợp lệ!");
        }
        return ResponseEntity.ok("Token hợp lệ. Role: " + role);
    }

//ADMIN
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

    public ResponseEntity<?> lockUser(Long id) {
        Optional<Account> optional = accRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy người dùng.");
        }

        Account account = optional.get();
        if(account.isLocked()) {
            return ResponseEntity.badRequest().body("Tài khoản đã bị khóa!");
        }

        account.setLocked(true);
        accRepository.save(account);

        return ResponseEntity.ok("Đã khóa tài khoản thành công!");
    }

    public ResponseEntity<?> unlockUser(Long id){
        Optional<Account> optional = accRepository.findById(id);

        if(optional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy tài khoản.");
        }

        Account account = optional.get();
        if(!account.isLocked()){
            return ResponseEntity.badRequest().body("Tài khoản đang mở!");
        }

        account.setLocked(false);
        accRepository.save(account);

        return ResponseEntity.ok("Đã mở khóa tài khoản thành công!");
    }



}



