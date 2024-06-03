package com.hr.hr_management.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class UserEntities {

    public UserEntities() {
    }

    public UserEntities(String userName, String password, String fullName, UserRoleType roleType) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.roleType = roleType;
    }

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private UUID id;

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

    @JsonIgnore
    private boolean employeApproved = false;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Integer totalLeaveBalance;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Integer totalLeaveApproved;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Integer totalLeavePending;

    @Column(columnDefinition = "tinyint(1) default 0")
    private Integer totalLeaveCancelled;

    @Column(nullable = true)
    private String accountSuspendReason;

    @JsonIgnore
    private Boolean firstTimeLogin = Boolean.TRUE;

    @Column(nullable = true)
    private UUID createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntities company;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "user_team", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    private List<TeamsEntities> teams = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<UserActivityEntities> activities = new ArrayList<>();;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LeaveAcitivityEntities> leaves = new ArrayList<>();
}
