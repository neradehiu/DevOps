package com.atp.fwfe.controller.account;

import com.atp.fwfe.dto.account.adrequest.AdminCreateUserRequest;
import com.atp.fwfe.model.work.Report;
import com.atp.fwfe.service.account.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> unlockUser(@PathVariable Long id) {
        return authService.unlockUser(id);
    }

    @GetMapping("/reports/unresolved")
    public List<Report> getUnresolvedReports(){
      return authService.findByResolvedFalse();
    }

}
