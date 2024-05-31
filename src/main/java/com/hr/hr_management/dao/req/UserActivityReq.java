package com.hr.hr_management.dao.req;

import java.sql.Time;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserActivityReq {

    @Nullable
    private int activityID;

    private int userID;

    private int companyID;

    @Pattern(regexp = "^(IN|BREAKIN|BREAKOUT|OUT)$", message = "Invalid user role type. Allowed values are IN|BREAKIN|BREAKOUT|OUT.")
    private String activityType;

    @Nullable
    private Time inTime;

    @Nullable
    private Time outTime;

    @Nullable
    private Time breakInTime;

    @Nullable
    private Time breakOutTime;

}
