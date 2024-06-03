package com.hr.hr_management.dao.req;

import java.sql.Time;
import java.util.UUID;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserActivityReq {

    @Nullable
    private UUID activityID;

    @NotNull(message = "userID can't be null")
    private UUID userID;

    @NotNull(message = "Company can't be null")
    private UUID companyID;

    @Pattern(regexp = "^(IN|BREAKIN|BREAKOUT|OUT)$", message = "Invalid user role type. Allowed values are IN|BREAKIN|BREAKOUT|OUT.")
    @NotNull(message = "Actvity Type can't be null")
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
