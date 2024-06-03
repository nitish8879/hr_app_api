package com.hr.hr_management.controllers;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.UserActivityReq;
import com.hr.hr_management.services.UserActivitiesService;
import com.hr.hr_management.utils.models.AppResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/activity")
public class UserActivityController {
    @Autowired
    private UserActivitiesService service;

    @PostMapping("/dailyInOut")
    public AppResponse dailyInOut(@RequestBody @Valid UserActivityReq req) {
        return service.dailyInOut(req);
    }


    @GetMapping("/getDataByIDAndCompanyIdAndDate")
    public AppResponse getDataByIDAndCompanyIdAndDate(@RequestParam UUID id, UUID compnayID, LocalDate date) {
        return service.getdailyInOutByIDAndDate(id, compnayID, date);
    }

    @GetMapping("/getActivityList")
    public AppResponse getActivityList(@RequestParam UUID id, UUID compnayID, LocalDate date) {
        return service.getActivityList(id, compnayID, date);
    }
    

    

}
