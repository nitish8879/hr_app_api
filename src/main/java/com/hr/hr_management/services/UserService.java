package com.hr.hr_management.services;

import java.util.UUID;

import com.hr.hr_management.dao.req.ApproveOrRejectEmployeReq;
import com.hr.hr_management.dao.req.UserSigninReq;
import com.hr.hr_management.dao.req.UserSignupReq;
import com.hr.hr_management.utils.models.AppResponse;

public interface UserService {
    public AppResponse signin(UserSigninReq req);

    public AppResponse signUp(UserSignupReq req);

    public AppResponse approveOrRejectEmployee(ApproveOrRejectEmployeReq req);

    public AppResponse findAllEmployesByCompanyID(UUID companyID);

    public AppResponse getUserTotalLeave(UUID userID, UUID companyID);

    public AppResponse updatePassword(String userName,String oldpassword,String newPasswod);
}
