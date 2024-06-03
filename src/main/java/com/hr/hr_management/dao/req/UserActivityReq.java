package com.hr.hr_management.dao.req;

import java.sql.Time;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserActivityReq {

    @Nullable
    private UUID activityID;

    private UUID userID;

    private UUID companyID;

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
