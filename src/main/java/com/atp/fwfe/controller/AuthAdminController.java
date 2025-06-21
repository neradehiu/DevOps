package com.atp.fwfe.controller;

import com.atp.fwfe.dto.adrequest.AdminCreateUserRequest;
import com.atp.fwfe.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AuthAdminController {

  private final AuthService authService;

  @Autowired
  public AuthAdminController(AuthService authService) {
      this.authService = authService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody AdminCreateUserRequest request) {
        return authService.createUser(request);
    }


}
