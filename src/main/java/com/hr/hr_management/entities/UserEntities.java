package com.hr.hr_management.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.enums.UserRoleType;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UserEntities {

    @Id
    @GeneratedValue
    private Integer id;

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

    @Column(nullable = false, updatable = false)
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

    private Boolean firstTimeLogin = Boolean.TRUE;

    public UserEntities() {
    }

    @Column(nullable = true)
    private Integer createdBy;

    public UserEntities(String userName, String password, String fullName, UserRoleType roleType) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.roleType = roleType;
    }

    @ManyToMany(mappedBy = "members")
    private List<TeamsEntities> teams;

}
