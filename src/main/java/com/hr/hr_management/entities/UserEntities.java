package com.hr.hr_management.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private UUID id;

    @Column(unique = true, nullable = false)
    private String userName;

    @JsonIgnore
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

    @JsonIgnore
    @Column(nullable = true)
    private String accountSuspendReason;

    @JsonIgnore
    private Boolean firstTimeLogin = Boolean.TRUE;

    @Column(nullable = true)
    @JsonIgnore
    private UUID createdBy;

    private Integer totalPaidLeave= 0;

    private Integer totalSickLeave = 0;

    private Integer totalCasualLeave = 0;

    private Integer totalWorkFromHome = 0;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntities company;

    @JsonIgnore
    @OneToMany
    @JoinTable(name = "user_leave", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "leave_id"))
    private List<LeaveAcitivityEntities> userLeaves = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserActivityEntities> activities = new ArrayList<>();

}
