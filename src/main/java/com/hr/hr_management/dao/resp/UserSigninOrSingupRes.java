package com.hr.hr_management.dao.resp;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserSigninOrSingupRes {

    private int userID;

    private int companyID;

    private int adminID;

    private String username;

    private String fullName;

    private Date createdAt;

    private String roleType;

    private int totalLeaveBalance;

    private int totalLeaveApproved;

    private int totalLeavePending;

    private int totalLeaveCancelled;

    private String companyName;

    private List<String> wrokingDays;

    private Time inTime;

    private Time outTime;

    private boolean employeeApproved;

    private String rejectedReason;
}
