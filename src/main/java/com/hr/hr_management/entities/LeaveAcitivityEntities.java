package com.hr.hr_management.entities;

import java.time.LocalDate;
import java.util.Date;
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
    public LeaveAcitivityEntities() {
    }

    public LeaveAcitivityEntities(LeaveStatus leaveStatus,
            Date fromdate, Date toDate, String leaveReason, CompanyEntities company, UserEntities user,
            UserEntities approvalTo) {
        this.leaveStatus = leaveStatus;
        this.fromdate = fromdate;
        this.todate = toDate;
        this.leaveReason = leaveReason;
        this.company = company;
        this.user = user;
        this.approvalTo = approvalTo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "leave_id")
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

    @ManyToOne
    @JoinColumn(name = "user_id_approval", nullable = false)
    private UserEntities approvalTo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntities company;

    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntities user;

}
