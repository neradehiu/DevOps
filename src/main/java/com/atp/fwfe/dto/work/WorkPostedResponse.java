package com.atp.fwfe.dto.work;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class WorkPostedResponse {

    private Long id;
    private String position;
    private String descriptionWork;
    private BigDecimal salary;
    private Integer maxReceiver;
    private int acceptedCount;
    private int maxAccepted;
    private boolean isNotified;
    private LocalDateTime updatedAt;

    private Long companyId;
    private String companyName;

    private Long createdById;
    private String createdByUsername;
}

