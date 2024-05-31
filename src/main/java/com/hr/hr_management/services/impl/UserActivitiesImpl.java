package com.hr.hr_management.services.impl;

import java.sql.Time;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.UserActivityReq;
import com.hr.hr_management.entities.UserActivityEntities;
import com.hr.hr_management.repo.CompanyRepo;
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
    private CompanyRepo companyRepo;

    @SuppressWarnings("null")
    @Override
    public AppResponse dailyInOut(UserActivityReq req) {
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(req.getUserID());

        if (userExit != null && userExit.isPresent()) {

            if (userExit.get().isEmployeApproved()) {

                var compnayExit = companyRepo.findById(req.getCompanyID());

                if (compnayExit != null && compnayExit.isPresent()) {

                    if (compnayExit.get().getAllEmployesID().contains(req.getUserID())) {
                        if (req.getActivityID() == 0) {
                            var saveUserEntities = userActivitiesRepo.save(new UserActivityEntities(
                                    req.getUserID(),
                                    req.getCompanyID(),
                                    req.getInTime()));
                            response.setStatus(true);
                            response.setData(saveUserEntities);
                        } else {
                            var recordExit = userActivitiesRepo.findById(req.getActivityID());
                            if (recordExit != null && recordExit.isPresent()) {
                                if (recordExit.get().getUserID() != req.getUserID()) {
                                    response.setStatus(false);
                                    response.setErrorMsg("You are not the right person to update this data");
                                } else if (req.getActivityType() == UserActivitiesType.IN.name()) {
                                    recordExit.get().setInTime(req.getInTime());
                                } else if ((req.getActivityType().equals(UserActivitiesType.BREAKIN.name()))) {
                                    recordExit.get().getBreakInTimes().add(req.getBreakInTime());
                                } else if ((req.getActivityType().equals(UserActivitiesType.BREAKOUT.name()))) {
                                    recordExit.get().getBreakOutTimes().add(req.getBreakInTime());
                                } else {
                                    recordExit.get().setOutTime(req.getOutTime());
                                }
                                response.setData(userActivitiesRepo.save(recordExit.get()));
                                response.setStatus(true);
                            } else {
                                response.setErrorMsg("Fail to upload Data");

                            }
                        }
                    } else {
                        response.setErrorMsg("User is not from this compnay");
                    }
                } else {
                    response.setErrorMsg("Company not exit exit in db");
                }
            } else {
                response.setErrorMsg("Your account is not active");
            }
        } else {
            response.setErrorMsg("UserNot exit in db");
        }

        return response;
    }

    @Override
    public AppResponse getdailyInOutByIDAndDate(int userID, int compnayID, LocalDate date) {
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(userID);
        if (userExit != null && userExit.isPresent()) {
            if (userExit.get().isEmployeApproved()) {
                var compnayExit = companyRepo.findById(compnayID);
                if (compnayExit != null && compnayExit.isPresent()) {
                    if (compnayExit.get().getAllEmployesID().contains(userID)) {
                        var foundData = userActivitiesRepo.findByUserIDAndCompanyIDAndCreatedAt(userID, compnayID,
                                date);
                        if (foundData != null && foundData.isPresent()) {
                            response.setStatus(true);
                            response.setData(foundData.get());
                        } else {
                            response.setErrorMsg("Data not found.");
                        }
                    } else {
                        response.setErrorMsg("User is not from this compnay");
                    }
                } else {
                    response.setErrorMsg("Company not exit exit in db");
                }
            } else {
                response.setErrorMsg("Your account is not active");
            }
        } else {
            response.setErrorMsg("User not exit in db");
        }
        return response;
    }

    @Override
    public AppResponse getActivityList(Integer id, Integer compnayID, LocalDate date) {
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(id);
        if (userExit != null && userExit.isPresent()) {
            if (userExit.get().isEmployeApproved()) {
                var compnayExit = companyRepo.findById(compnayID);
                if (compnayExit != null && compnayExit.isPresent()) {
                    if (compnayExit.get().getAllEmployesID().contains(id)) {
                        var foundData = userActivitiesRepo.findByUserIDAndCompanyIDAndCreatedAt(id, compnayID, date);
                        if (foundData != null && foundData.isPresent()) {
                            response.setStatus(true);
                            Map<String, Object> data = new HashMap<>();
                            data.put("activityID", foundData.get().getId());
                            data.put("date", foundData.get().getCreatedAt());
                            if (foundData.get().getInTime() != null) {
                                HashMap<String, Object> checkIn = new HashMap<>();
                                checkIn.put("inTime", foundData.get().getInTime());
                                if (foundData.get().getInTime().after(compnayExit.get().getInTime())) {
                                    checkIn.put("msg", "Late ");
                                } else {
                                    checkIn.put("msg", "On Time");
                                }
                                data.put("checkIn", checkIn);
                            }
                            if (!foundData.get().getBreakInTimes().isEmpty()) {
                                HashMap<String, Object> breakInTime = new HashMap<>();
                                for (int i = 0; i < foundData.get().getBreakInTimes().size(); i++) {
                                    breakInTime.put("breakInTime " + (i + 1), foundData.get().getBreakInTimes().get(i));
                                }
                                data.put("breakInTime", breakInTime);
                            }

                            if (!foundData.get().getBreakOutTimes().isEmpty()) {
                                HashMap<String, Object> breakOutTime = new HashMap<>();
                                for (int i = 0; i < foundData.get().getBreakOutTimes().size(); i++) {
                                    breakOutTime.put("breakOutTime " + (i + 1), foundData.get().getBreakOutTimes().get(i));
                                }
                                data.put("breakOutTime", breakOutTime);
                            }

                            if (foundData.get().getOutTime() != null) {
                                HashMap<String, Object> outTime = new HashMap<>();
                                outTime.put("outTime", foundData.get().getOutTime());
                                if (foundData.get().getOutTime().after(compnayExit.get().getOutTime())) {
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
                    } else {
                        response.setErrorMsg("User is not from this compnay");
                    }
                } else {
                    response.setErrorMsg("Company not exit exit in db");
                }
            } else {
                response.setErrorMsg("Your account is not active");
            }
        } else {
            response.setErrorMsg("User not exit in db");
        }
        return response;
    }

}
