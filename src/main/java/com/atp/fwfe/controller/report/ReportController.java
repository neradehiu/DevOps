package com.atp.fwfe.controller.report;

import com.atp.fwfe.dto.account.reportRequest.ReportRequest;
import com.atp.fwfe.model.report.Report;
import com.atp.fwfe.service.report.ReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> reportUser(
            @RequestHeader("X-Username") String reporterUsername,
            @RequestBody @Valid ReportRequest request
    ) {
        reportService.createReport(reporterUsername, request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/unresolved")
    public List<Report> getUnresolvedReports(){
        return reportService.findByResolvedFalse();
    }
}
