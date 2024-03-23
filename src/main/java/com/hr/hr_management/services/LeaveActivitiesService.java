package com.hr.hr_management.services;

import com.hr.hr_management.dao.req.LeaveActivityApproveRejectReq;
import com.hr.hr_management.dao.req.LeaveActivityReq;
import com.hr.hr_management.utils.models.AppResponse;

public interface LeaveActivitiesService {

    public AppResponse applyLeave(LeaveActivityReq req);

    public AppResponse approveOrRejectLeave(LeaveActivityApproveRejectReq req);

    public AppResponse getAllLeavesByCompanyID(Integer userID, Integer companyID);
}
