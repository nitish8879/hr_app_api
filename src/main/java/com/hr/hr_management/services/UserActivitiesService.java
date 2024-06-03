package com.hr.hr_management.services;

import java.time.LocalDate;
import java.util.UUID;

import com.hr.hr_management.dao.req.UserActivityReq;
import com.hr.hr_management.utils.models.AppResponse;

public interface UserActivitiesService {
    public AppResponse dailyInOut(UserActivityReq req);

    public AppResponse getdailyInOutByIDAndDate(UUID userID, UUID compnayID, LocalDate date);

    public AppResponse getActivityList(UUID id, UUID compnayID, LocalDate date);

}
