package com.atp.fwfe.repository.work;

import com.atp.fwfe.model.work.WorkAcceptance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WorkAcceptanceRepository extends JpaRepository<WorkAcceptance, Long> {
    List<WorkAcceptance> findByWorkPostedId(Long workPostedId);
    List<WorkAcceptance> findByAccountId(Long accountId);
    List<WorkAcceptance> findByAccountIdAndStatus(Long accountId, com.atp.fwfe.model.work.WorkAcceptance.WorkStatus status);


}
