package com.hr.hr_management.services;

import com.hr.hr_management.dao.req.ApproveOrRejectEmployeReq;
import com.hr.hr_management.dao.req.UserSigninReq;
import com.hr.hr_management.dao.req.UserSignupReq;
import com.hr.hr_management.utils.models.AppResponse;

public interface UserService {
    public AppResponse signin(UserSigninReq req);

    public AppResponse signUp(UserSignupReq req);

    public AppResponse approveOrRejectEmployee(ApproveOrRejectEmployeReq req);

    public AppResponse findAllEmployesByCompanyID(Integer userID);
}
