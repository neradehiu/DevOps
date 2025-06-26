package com.atp.fwfe.repository;

import com.atp.fwfe.model.work.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByResolvedFalse();
}
