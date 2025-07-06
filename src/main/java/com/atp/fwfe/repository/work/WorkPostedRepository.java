package com.atp.fwfe.repository.work;

import com.atp.fwfe.model.work.WorkPosted;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WorkPostedRepository extends JpaRepository<WorkPosted, Long> {

    List<WorkPosted> findByCreatedById(Long userId);

    List<WorkPosted> findByIsNotifiedFalse();

    @Query("SELECT n FROM NoticeNewWork n LEFT JOIN FETCH n.createdBy LEFT JOIN FETCH n.acceptedBy")
    List<WorkPosted> findAllWithRelations();

    @Query("SELECT n FROM NoticeNewWork n LEFT JOIN FETCH n.createdBy LEFT JOIN FETCH n.acceptedBy WHERE n.id = :id")
    java.util.Optional<WorkPosted> findByIdWithRelations(Long id);

    @Query("SELECT n FROM NoticeNewWork n JOIN n.acceptedBy a WHERE a.id = :userId")
    List<WorkPosted> findByAcceptedById(Long userId);
}
