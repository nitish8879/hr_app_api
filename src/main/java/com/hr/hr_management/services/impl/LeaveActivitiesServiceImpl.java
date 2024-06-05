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
import com.hr.hr_management.utils.enums.UserRoleType;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class LeaveActivitiesServiceImpl implements LeaveActivitiesService {
    @Autowired
    private LeaveActivityRepo leaveRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyRepo companyRepo;

    @Autowired
    private ValidationUserService validationUserService;

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
                userRepo.findById(req.getApprovalTo()).get());
        var savedData = leaveRepo.save(data);
        response.setStatus(true);
        response.setData(savedData);

        var userLeaves = user.get().getUserLeaves();
        userLeaves.add(savedData);
        user.get().setUserLeaves(userLeaves);
        userRepo.save(user.get());
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
        } else if (!leaveData.get().getApprovalTo().toString().equals(req.getUserID().toString())) {
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
            var map = new HashMap<>();
            map.put("totalLeavebalance", userExit.get().getTotalLeaveBalance());
            map.put("totalWFHbalance", userExit.get().getTotalWFHBalance());
            map.put("data", userExit.get().getUserLeaves());
            response.setData(map);
        }
        response.setStatus(true);
        return response;
    }
}
