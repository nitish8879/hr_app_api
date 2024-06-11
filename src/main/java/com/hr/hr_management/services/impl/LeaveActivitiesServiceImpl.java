package com.hr.hr_management.services.impl;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.LeaveActivityApproveRejectReq;
import com.hr.hr_management.dao.req.LeaveActivityReq;
import com.hr.hr_management.entities.LeaveAcitivityEntities;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.LeaveActivityRepo;
import com.hr.hr_management.repo.UserRepo;
import com.hr.hr_management.services.LeaveActivitiesService;
import com.hr.hr_management.utils.enums.LeaveStatus;
import com.hr.hr_management.utils.enums.LeaveType;
import com.hr.hr_management.utils.enums.UserRoleType;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class LeaveActivitiesServiceImpl implements LeaveActivitiesService {
    @Autowired
    LeaveActivityRepo leaveRepo;

    @Autowired
    UserRepo userRepo;
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    ValidationUserService validationUserService;

    @Override
    public AppResponse applyLeave(LeaveActivityReq req) {
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        validationUserService.isUserValid(req.getApprovalTo(), req.getCompanyID());
        AppResponse response = new AppResponse();
        var user = userRepo.findById(req.getUserID());
        LeaveAcitivityEntities data = new LeaveAcitivityEntities(
                LeaveStatus.PENDING,
                req.getFromdate(),
                req.getTodate(),
                req.getLeaveReason(),
                user.get().getCompany(),
                user.get(),
                userRepo.findById(req.getApprovalTo()).get(),
                req.getLeaveType());
        var savedData = leaveRepo.save(data);

        var userLeaves = user.get().getUserLeaves();
        userLeaves.add(savedData);
        user.get().setUserLeaves(userLeaves);
        if (req.getLeaveType() == LeaveType.WFH) {
            user.get().setTotalWorkFromHome(user.get().getTotalWorkFromHome() - 1);
        } else if (req.getLeaveType() == LeaveType.PAID_LEAVE) {
            user.get().setTotalPaidLeave(user.get().getTotalPaidLeave() - 1);
        } else if (req.getLeaveType() == LeaveType.CASUAL_LEAVE) {
            user.get().setTotalCasualLeave(user.get().getTotalCasualLeave() - 1);
        } else if (req.getLeaveType() == LeaveType.SICK_LEAVE) {
            user.get().setTotalSickLeave(user.get().getTotalSickLeave() - 1);
        }
        userRepo.save(user.get());
        response.setStatus(true);
        response.setData(savedData);
        return response;
    }

    @Override
    public AppResponse approveOrRejectLeave(LeaveActivityApproveRejectReq req) {
        AppResponse response = new AppResponse();
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        validationUserService.isUserValid(req.getEmployeeID(), req.getCompanyID());
        var leaveData = leaveRepo.findById(req.getLeaveID());
        if (!leaveData.isPresent()) {
            response.setStatus(false);
            response.setErrorMsg("Leave Data not found");
        } else if (!leaveData.get().getApprovalTo().getId().toString().equals(req.getUserID().toString())) {
            response.setStatus(false);
            response.setErrorMsg("You are not the right person to approve this.");
        } else {
            leaveData.get().setLeaveStatus(req.getLeaveStatus());
            leaveData.get().setRejectedReason(req.getRejectReason());
            var saveData = leaveRepo.save(leaveData.get());
            response.setStatus(true);
            response.setData(saveData);
        }

        return response;
    }

    @Override
    public AppResponse getAllLeaves(UUID userID, UUID companyID, UserRoleType roleType, Boolean myLeave) {
        AppResponse response = new AppResponse();
        validationUserService.isUserValid(userID, companyID);
        if ((roleType == UserRoleType.ADMIN || roleType == UserRoleType.SUPERADMIN || roleType == UserRoleType.MANAGER)
                && !myLeave) {
            var company = companyRepo.findById(companyID);
            response.setData(company.get().getAllLeaves());
        } else {
            var userExit = userRepo.findById(userID);
            userExit.get();
            var map = new HashMap<>();
            map.put("paidLeaveBalance", userExit.get().getTotalPaidLeave());
            map.put("totalWFHbalance", userExit.get().getTotalWorkFromHome());
            map.put("sickLeaveBalance", userExit.get().getTotalSickLeave());
            map.put("casualLeaveBalance", userExit.get().getTotalCasualLeave());
            map.put("data", userExit.get().getUserLeaves());
            response.setData(map);
            response.setStatus(true);
        }
        return response;
    }
}
