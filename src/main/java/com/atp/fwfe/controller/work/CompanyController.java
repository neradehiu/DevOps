package com.atp.fwfe.controller.work;

import com.atp.fwfe.dto.work.CompanyResponse;
import com.atp.fwfe.service.work.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<CompanyResponse> create(
            @RequestBody @Valid com.atp.fwfe.dto.work.CreateCompanyDto dto,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(companyService.create(dto, username));
    }

    @GetMapping("/my")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<List<CompanyResponse>> getByOwner(
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(companyService.findByOwner(username));
    }

    @GetMapping("/{id}/public")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<CompanyResponse> getPublicInfo(@PathVariable Long id) {
        return ResponseEntity.ok(companyService.getSanitizedCompany(id));
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<List<CompanyResponse>> getAll(
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        return ResponseEntity.ok(companyService.getAll(username, role));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<CompanyResponse> getOne(
            @PathVariable Long id,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        return ResponseEntity.ok(companyService.getOne(id, username, role));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<CompanyResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid com.atp.fwfe.dto.work.CreateCompanyDto dto,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        return ResponseEntity.ok(companyService.update(id, dto, username, role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        companyService.delete(id, username, role);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<List<CompanyResponse>> search(
            @RequestParam String keyword) {
        return ResponseEntity.ok(companyService.search(keyword));
    }
}

