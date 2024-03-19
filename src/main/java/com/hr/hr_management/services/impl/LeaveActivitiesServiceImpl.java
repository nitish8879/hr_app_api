package com.hr.hr_management.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.LeaveActivityApproveRejectReq;
import com.hr.hr_management.dao.req.LeaveActivityReq;
import com.hr.hr_management.entities.LeaveAcitivityEntities;
import com.hr.hr_management.repo.LeaveActivityRepo;
import com.hr.hr_management.services.LeaveActivitiesService;
import com.hr.hr_management.utils.enums.LeaveStatus;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class LeaveActivitiesServiceImpl implements LeaveActivitiesService {
    @Autowired
    private LeaveActivityRepo repo;

    // @Autowired
    // private UserRepo userRepo;

    // @Autowired
    // private CompanyRepo companyRepo;

    @Override
    public AppResponse applyLeave(LeaveActivityReq req) {
        AppResponse response = new AppResponse();
        LeaveAcitivityEntities data = new LeaveAcitivityEntities();
        data.setUserID(req.getUserID());
        data.setCompanyID(req.getCompanyID());
        data.setApprovalTo(req.getApprovalTo());
        data.setLeaveStatus(LeaveStatus.valueOf(req.getLeaveStatus()));
        data.setLeaveReason(req.getLeaveReason());
        data.setFromdate(req.getFromdate());
        data.setTodate(req.getTodate());
        try {
            var savedData = repo.save(data);
            response.setData(savedData);
            response.setStatus(true);
            response.setData(savedData);
        } catch (Exception e) {
            response.setStatus(false);
            response.setData("Failed to save Data." + e.getMessage());
        }
        return response;
    }

    @Override
    public AppResponse approveOrRejectLeave(LeaveActivityApproveRejectReq req) {
        AppResponse response = new AppResponse();
        if (req.getLeaveStatus() == LeaveStatus.REJECTED.name()
                && (req.getRejectReason() == null || req.getRejectReason().isEmpty())) {
            response.setStatus(false);
            response.setErrorMsg("Please enter reject reason");
        } else {
            var leaveData = repo.findById(req.getLeaveID());

            if (leaveData == null || !leaveData.isPresent()) {
                response.setStatus(false);
                response.setErrorMsg("Leave Data not found by your ID");
            } else if (leaveData.get().getApprovalTo() != req.getUserID()) {
                response.setStatus(false);
                response.setErrorMsg("You are not the right person to approve this.");
            } else {
                leaveData.get().setLeaveStatus(LeaveStatus.valueOf(req.getLeaveStatus()));
                leaveData.get().setRejectedReason(req.getRejectReason());
                var saveData = repo.save(leaveData.get());
                response.setStatus(true);
                response.setData(saveData);
            }
        }
        return response;
    }

    @Override
    public AppResponse getAllLeavesByCompanyID(Integer companyID) {
        AppResponse response = new AppResponse();
        try {
            var data = repo.findByCompanyID(companyID);
            response.setData(data);
            response.setStatus(true);
        } catch (Exception e) {
            response.setStatus(false);
            response.setData("Failed to get Data." + e.getMessage());
        }
        return response;
    }
}
