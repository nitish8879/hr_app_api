package com.hr.hr_management.entities;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.enums.LeaveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LeaveAcitivityEntities {
    public LeaveAcitivityEntities(UserEntities user, UUID approvalTo) {
        this.user = user;
        this.approvalTo = approvalTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @CreationTimestamp
    private LocalDate applyDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeaveStatus leaveStatus;

    @Column(nullable = false)
    private Date fromdate;

    @Column(nullable = false)
    private Date todate;

    @Column(nullable = false, updatable = false)
    private String leaveReason;

    @Column(nullable = true)
    private String rejectedReason;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntities user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntities company;

    // @ManyToOne
    // @JsonIgnore
    // @JoinColumn(name = "user_id", nullable = false)
    private UUID approvalTo;

}
