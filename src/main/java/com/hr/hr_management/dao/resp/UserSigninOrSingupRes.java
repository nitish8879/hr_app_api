package com.hr.hr_management.dao.resp;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

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

    private int totalSickLeavePending;

    private int totalPaidLeavePending;

    private int totalSickLeaveTaken;

    private int totalPaidLeaveTaken;

    private String companyName;

    private List<String> wrokingDays;

    private Time inTime;

    private Time outTime;

    private boolean employeeApproved;

    private String rejectedReason;
}
