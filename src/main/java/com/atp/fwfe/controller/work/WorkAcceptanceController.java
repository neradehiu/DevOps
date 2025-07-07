package com.atp.fwfe.controller.work;

import com.atp.fwfe.dto.work.CreateWorkAcceptanceRequest;
import com.atp.fwfe.dto.work.WorkAcceptanceResponse;
import com.atp.fwfe.service.work.WorkAcceptanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/works/{workId}/acceptances")
@RequiredArgsConstructor
public class WorkAcceptanceController {
    private final WorkAcceptanceService acceptanceService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<WorkAcceptanceResponse> accept(
            @PathVariable Long workId,
            @RequestBody @Valid CreateWorkAcceptanceRequest dto,
            @RequestHeader("X-Username") String username) {
        dto.setWorkPostedId(workId);
        WorkAcceptanceResponse resp = acceptanceService.accept(dto, username);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_WORK','ROLE_USER')")
    public ResponseEntity<List<WorkAcceptanceResponse>> getByPost(@PathVariable Long workId) {
        return ResponseEntity.ok(acceptanceService.getByPost(workId));
    }
}
