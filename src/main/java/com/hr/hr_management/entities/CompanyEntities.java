package com.hr.hr_management.entities;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hr.hr_management.utils.enums.WrokingDays;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class CompanyEntities {

    public CompanyEntities() {
    }

    public CompanyEntities(
            String companyName,
            UserEntities admin,
            Time inTime,
            Time outTime,
            List<String> workingDays,
            Integer perMonthPL,
            Integer perMonthSLCL,
            Integer perMonthWFH) {
        this.companyName = companyName;
        this.admin = admin;
        this.inTime = inTime;
        this.outTime = outTime;
        this.workingDays = workingDays;
        this.perMonthPL = perMonthPL;
        this.perMonthSLCL = perMonthSLCL;
        this.perMonthWFH = perMonthWFH;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "company_id")
    private UUID id;

    @Column(nullable = false)
    private String companyName;

    private Integer perMonthPL;

    private Integer perMonthSLCL;

    private Integer perMonthWFH;

    @Column(nullable = false)
    private Time inTime;

    @Column(nullable = false)
    private Time outTime;

    @Convert(converter = WrokingDays.class)
    private List<String> workingDays;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntities admin;

    @JsonIgnore
    @OneToMany(mappedBy = "company" ,fetch = FetchType.EAGER)
    private List<UserEntities> users = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<HolidayEntity> holidays = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<TeamsEntities> teams = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<LeaveAcitivityEntities> allLeaves = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "company")
    private List<UserActivityEntities> userActivities = new ArrayList<>();
}