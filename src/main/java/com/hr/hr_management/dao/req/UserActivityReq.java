package com.hr.hr_management.dao.req;

import java.sql.Time;
import java.util.UUID;

import com.hr.hr_management.utils.enums.UserActivitiesType;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserActivityReq {

    @Nullable
    private UUID activityID;

    @NotNull(message = "userID can't be null")
    private UUID userID;

    @NotNull(message = "Company can't be null")
    private UUID companyID;

    private UserActivitiesType activityType;

    @Nullable
    private Time inTime;

    @Nullable
    private Time outTime;

    @Nullable
    private Time breakInTime;

    @Nullable
    private Time breakOutTime;

}
