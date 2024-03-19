package com.hr.hr_management.services;

import java.time.LocalDate;

import com.hr.hr_management.dao.req.UserActivityReq;
import com.hr.hr_management.utils.models.AppResponse;

public interface UserActivitiesService {
    public AppResponse dailyInOut(UserActivityReq req);

    public AppResponse getdailyInOutByIDAndDate(int userID, int compnayID, LocalDate date);

    public AppResponse getActivityList(Integer id, Integer compnayID, LocalDate date);

}
