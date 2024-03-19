package com.hr.hr_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.LeaveActivityApproveRejectReq;
import com.hr.hr_management.dao.req.LeaveActivityReq;
import com.hr.hr_management.services.LeaveActivitiesService;
import com.hr.hr_management.utils.models.AppResponse;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(path = "/leave")
public class LeaveActivityController {

    @Autowired
    private LeaveActivitiesService service;

    @GetMapping("/{companyID}")
    public AppResponse getAllLeaves(@PathVariable("companyID") Integer companyID) {
        return service.getAllLeavesByCompanyID(companyID);
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
