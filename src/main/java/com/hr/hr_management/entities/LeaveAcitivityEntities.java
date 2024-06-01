package com.hr.hr_management.entities;

import java.sql.Date;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.hr.hr_management.utils.enums.LeaveStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class LeaveAcitivityEntities {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int userID;

    @Column(nullable = false)
    private int companyID;

    @Column(nullable = false)
    private int approvalTo;

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

}
