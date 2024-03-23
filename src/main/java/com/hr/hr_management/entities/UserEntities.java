package com.hr.hr_management.entities;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;

import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class UserEntities {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;

    @CreationTimestamp
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private UserRoleType roleType;

    @Column(nullable = true)
    private int companyID;

    private boolean employeApproved = false;

    @Column(columnDefinition = "tinyint(1) default 0")
    private int totalLeaveBalance;

    @Column(columnDefinition = "tinyint(1) default 0")
    private int totalLeaveApproved;

    @Column(columnDefinition = "tinyint(1) default 0")
    private int totalLeavePending;

    @Column(columnDefinition = "tinyint(1) default 0")
    private int totalLeaveCancelled;

    @Column(nullable = true)
    private String accountSuspendReason;

    public UserEntities() {
    }

    public UserEntities(String userName, String password, String fullName, UserRoleType roleType) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.roleType = roleType;
    }

}
