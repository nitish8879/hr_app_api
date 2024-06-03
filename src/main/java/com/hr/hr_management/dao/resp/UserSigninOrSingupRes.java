package com.hr.hr_management.dao.resp;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserSigninOrSingupRes {

    private UUID userID;

    private UUID companyID;

    private UUID adminID;

    private String username;

    private String fullName;

    private Date createdAt;

    private String roleType;

    private Integer totalLeaveBalance;

    private Integer totalLeaveApproved;

    private Integer totalLeavePending;

    private Integer totalLeaveCancelled;

    private String companyName;

    private List<String> wrokingDays;

    private Time inTime;

    private Time outTime;

    private boolean employeeApproved;

    private String rejectedReason;
}
