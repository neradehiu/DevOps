package com.atp.fwfe.controller.account;

import com.atp.fwfe.dto.adrequest.AdminCreateUserRequest;
import com.atp.fwfe.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private final AuthService authService;

  @Autowired
  public AdminController(AuthService authService) {
      this.authService = authService;
  }

  @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@Valid @RequestBody AdminCreateUserRequest request) {
        return authService.createUser(request);
    }

    @PutMapping("/users/{id}/lock")
    public ResponseEntity<?> lockUser(@PathVariable Long id) {
        return authService.lockUser(id);
    }

    @PutMapping("/users/{id}/unlock")
    public ResponseEntity<?> unlockUser(@PathVatiable Long id) {
        return authService.unlockUser(id);
    }


}
