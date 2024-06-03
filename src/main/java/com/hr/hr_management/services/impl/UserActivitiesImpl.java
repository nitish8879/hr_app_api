package com.hr.hr_management.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.UserActivityReq;
import com.hr.hr_management.entities.UserActivityEntities;
import com.hr.hr_management.repo.UserActivitiesRepo;
import com.hr.hr_management.repo.UserRepo;
import com.hr.hr_management.services.UserActivitiesService;
import com.hr.hr_management.utils.enums.UserActivitiesType;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class UserActivitiesImpl implements UserActivitiesService {
    @Autowired
    private UserActivitiesRepo userActivitiesRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ValidationUserService validationUserService;

    @Override
    public AppResponse dailyInOut(UserActivityReq req) {
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(req.getUserID());

        if (req.getActivityID() == null) {
            var saveUserEntities = userActivitiesRepo.save(new UserActivityEntities(
                    userExit.get(),
                    req.getInTime()));
            response.setStatus(true);
            response.setData(saveUserEntities);
        } else {
            var recordExit = userActivitiesRepo.findById(req.getActivityID());
            if (recordExit != null && recordExit.isPresent()) {
                if (recordExit.get().getUser().getId() != req.getUserID()) {
                    response.setStatus(false);
                    response.setErrorMsg("You are not the right person to update this data");
                } else if (req.getActivityType() == UserActivitiesType.IN.name()) {
                    recordExit.get().setInTime(req.getInTime());
                } else if ((req.getActivityType().equals(UserActivitiesType.BREAKIN.name()))) {
                    if (recordExit.get().getBreakInTimes() == null) {
                        recordExit.get().setBreakInTimes(new ArrayList<>());
                    }
                    recordExit.get().getBreakInTimes().add(req.getBreakInTime());
                } else if ((req.getActivityType().equals(UserActivitiesType.BREAKOUT.name()))) {
                    if (recordExit.get().getBreakOutTimes() == null) {
                        recordExit.get().setBreakOutTimes(new ArrayList<>());
                    }
                    recordExit.get().getBreakOutTimes().add(req.getBreakOutTime());
                } else {
                    recordExit.get().setOutTime(req.getOutTime());
                }
                response.setData(userActivitiesRepo.save(recordExit.get()));
                response.setStatus(true);
            } else {
                response.setErrorMsg("Fail to upload Data");

            }
        }

        return response;
    }

    @Override
    public AppResponse getdailyInOutByIDAndDate(UUID userID, UUID compnayID, LocalDate date) {
        validationUserService.isUserValid(userID, compnayID);
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(userID);
        var foundData = userActivitiesRepo.findByUserAndCompanyAndCreatedAt(userExit.get(), userExit.get().getCompany(),
                date);
        if (foundData != null && foundData.isPresent()) {
            response.setStatus(true);
            response.setData(foundData.get());
        } else {
            response.setErrorMsg("Activities not found.");
        }
        return response;
    }

    @Override
    public AppResponse getActivityList(UUID id, UUID compnayID, LocalDate date) {
        validationUserService.isUserValid(id, compnayID);
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(id);
        var foundData = userActivitiesRepo.findByUserAndCompanyAndCreatedAt(userExit.get(), userExit.get().getCompany(),
                date);
        if (foundData != null && foundData.isPresent()) {
            response.setStatus(true);
            Map<String, Object> data = new HashMap<>();
            data.put("activityID", foundData.get().getId());
            data.put("date", foundData.get().getCreatedAt());
            if (foundData.get().getInTime() != null) {
                HashMap<String, Object> checkIn = new HashMap<>();
                checkIn.put("inTime", foundData.get().getInTime());
                if (foundData.get().getInTime().after(userExit.get().getCompany().getInTime())) {
                    checkIn.put("msg", "Late ");
                } else {
                    checkIn.put("msg", "On Time");
                }
                data.put("checkIn", checkIn);
            }
            if (foundData.get().getBreakInTimes() != null
                    && !foundData.get().getBreakInTimes().isEmpty()) {
                data.put("breakInTime", foundData.get().getBreakInTimes());
            }

            if (foundData.get().getBreakOutTimes() != null
                    && !foundData.get().getBreakOutTimes().isEmpty()) {
                data.put("breakOutTime", foundData.get().getBreakOutTimes());
            }

            if (foundData.get().getOutTime() != null) {
                HashMap<String, Object> outTime = new HashMap<>();
                outTime.put("outTime", foundData.get().getOutTime());
                if (foundData.get().getOutTime().after(userExit.get().getCompany().getOutTime())) {
                    outTime.put("msg", "Over Time ");
                } else {
                    outTime.put("msg", "On Time");
                }
                data.put("outTime", outTime);

            }

            response.setData(data);
        } else {
            response.setErrorMsg("Data not found.");
        }

        return response;
    }

}
