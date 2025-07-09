package com.atp.fwfe.controller.work;

import com.atp.fwfe.dto.work.WorkPostedResponse;
import com.atp.fwfe.service.work.WorkPostedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works-posted")
@CrossOrigin(origins = {
        "http://10.0.2.2:8000",
        "http://127.0.0.1:8000"
}, allowCredentials = "true")
@RequiredArgsConstructor
public class WorkPostedController {
    private final WorkPostedService postService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<WorkPostedResponse> create(
            @RequestBody @Valid com.atp.fwfe.dto.work.CreateWorkPostedRequest dto,
            @RequestHeader("X-Username") String username) {
        return ResponseEntity.ok(postService.create(dto, username));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<List<WorkPostedResponse>> getAll(
            @RequestHeader("X-Role") String role) {
        return ResponseEntity.ok(postService.getAll(role));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    public ResponseEntity<WorkPostedResponse> getOne(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getOne(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<WorkPostedResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid com.atp.fwfe.dto.work.CreateWorkPostedRequest dto,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        return ResponseEntity.ok(postService.update(id, dto, username, role));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestHeader("X-Username") String username,
            @RequestHeader("X-Role") String role) {
        postService.delete(id, username, role);
        return ResponseEntity.noContent().build();
    }
}

