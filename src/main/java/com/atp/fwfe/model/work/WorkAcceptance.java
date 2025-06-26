package com.atp.fwfe.model.work;

import com.atp.fwfe.model.account.Account;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "works_acceptance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkAcceptance     {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "work_posted_id", nullable = false)
    private WorkPosted workPosted;

    @Column
    @CreationTimestamp
    private LocalDateTime acceptedAt;

    @Enumerated(EnumType.STRING)
    private WorkStatus status;

    @Getter
    public enum WorkStatus {
        PENDING("Đang chờ"),
        COMPLETED("Đã hoàn thành"),
        CANCELLED("Đã hủy");

        private final String label;

        WorkStatus(String label){
            this.label = label;
        }

    }

    @PrePersist
    public void prePersist(){
        this.acceptedAt = LocalDateTime.now();
        this.status = WorkStatus.PENDING;
    }

}
