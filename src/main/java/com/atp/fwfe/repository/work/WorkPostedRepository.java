package com.atp.fwfe.repository.work;

import com.atp.fwfe.model.work.WorkPosted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkPostedRepository extends JpaRepository<WorkPosted, Long> {

    }
