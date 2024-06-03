package com.hr.hr_management.controllers;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.LeaveActivityApproveRejectReq;
import com.hr.hr_management.dao.req.LeaveActivityReq;
import com.hr.hr_management.services.LeaveActivitiesService;
import com.hr.hr_management.utils.models.AppResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/leave")
public class LeaveActivityController {

    @Autowired
    private LeaveActivitiesService service;

    @GetMapping("/getAllLeaves")
    public AppResponse getAllLeaves(@RequestParam("userID") UUID userID,
            @RequestParam("companyID") UUID companyID) {
        return service.getAllLeavesByCompanyID(userID, companyID);
    }

    @PostMapping("/approveReject")
    public AppResponse approveOrRejectLeave(@RequestBody @Valid LeaveActivityApproveRejectReq req) {
        return service.approveOrRejectLeave(req);
    }

    @PostMapping("/addLeave")
    public AppResponse applyLeave(@RequestBody @Valid LeaveActivityReq req) {
        return service.applyLeave(req);
    }

}
