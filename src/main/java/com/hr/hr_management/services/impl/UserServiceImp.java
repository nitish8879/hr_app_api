package com.hr.hr_management.services.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.ApproveOrRejectEmployeReq;
import com.hr.hr_management.dao.req.UserSigninReq;
import com.hr.hr_management.dao.req.UserSignupReq;
import com.hr.hr_management.dao.resp.UserSigninOrSingupRes;
import com.hr.hr_management.entities.CompanyEntities;
import com.hr.hr_management.entities.UserEntities;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.UserRepo;
import com.hr.hr_management.services.UserService;
import com.hr.hr_management.utils.enums.EmployeeApprovalStatus;
import com.hr.hr_management.utils.enums.UserRoleType;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CompanyRepo companyRepo;
    // @Autowired
    // private LeaveActivityRepo leaveActivityRepo;

    @Override
    public AppResponse signin(UserSigninReq req) {
        var response = new AppResponse();
        Optional<UserEntities> userData = userRepo.findByUserNameAndPassword(req.getUsername(), req.getPassword());
        if (userData.isPresent()) {
            Optional<CompanyEntities> companyData = companyRepo.findById(userData.get().getCompanyID());
            if (companyData.isPresent()) {
                var userResp = new UserSigninOrSingupRes();
                userResp.setUserID(userData.get().getId());
                userResp.setCompanyID(userData.get().getCompanyID());
                userResp.setUsername(userData.get().getUserName());
                userResp.setFullName(userData.get().getFullName());
                userResp.setCreatedAt(userData.get().getCreatedAt());
                userResp.setRoleType(userData.get().getRoleType().name());

                userResp.setTotalLeaveBalance(userData.get().getTotalLeaveBalance());
                userResp.setTotalLeaveApproved(userData.get().getTotalLeaveApproved());
                userResp.setTotalLeavePending(userData.get().getTotalLeavePending());
                userResp.setTotalLeaveCancelled(userData.get().getTotalLeaveCancelled());

                userResp.setCompanyName(companyData.get().getCompanyName());
                userResp.setWrokingDays(companyData.get().getWorkingDays());
                userResp.setInTime(companyData.get().getInTime());
                userResp.setOutTime(companyData.get().getOutTime());
                userResp.setEmployeeApproved(userData.get().isEmployeApproved());
                userResp.setAdminID(companyData.get().getOwnerID());
                userResp.setRejectedReason(userData.get().getAccountSuspendReason());
                response.setData(userResp);
                response.setStatus(true);

            } else {
                response.setErrorMsg("Company Not Found.");
            }
        } else {
            response.setErrorMsg("User Not Found.");
        }
        return response;
    }

    @Override
    public AppResponse signUp(UserSignupReq req) {

        var userexit = userRepo.findByUserName(req.getUsername());

        var response = new AppResponse();

        if (userexit.isPresent()) {
            response.setErrorMsg("Username already taken!");
        } else if (req.getRoleType().equals(UserRoleType.ADMIN.name())) {
            if (req.getInTime() == null || (req.getInTime().toString().isBlank())) {
                response.setErrorMsg("Please set in Time");
            } else if (req.getOutTime() == null || (req.getOutTime().toString().isBlank())) {
                response.setErrorMsg("Please set in Time");
            } else if (req.getWrokingDays() == null || req.getWrokingDays().isEmpty()) {
                response.setErrorMsg("Please set working days");
            } else {
                try {
                    var savedUserEntities = userRepo.save(new UserEntities(
                            req.getUsername(),
                            req.getPassword(),
                            req.getFullName(),
                            UserRoleType.valueOf(req.getRoleType())));
                    var savedCompanyUserEntities = companyRepo.save(new CompanyEntities(
                            req.getCompanyName(),
                            savedUserEntities.getId(),
                            req.getInTime(),
                            req.getOutTime(),
                            req.getWrokingDays()));
                    savedUserEntities.setEmployeApproved(true);
                    savedUserEntities.setCompanyID(savedCompanyUserEntities.getId());
                    userRepo.save(savedUserEntities);
                    return signin(new UserSigninReq(req.getUsername(), req.getPassword()));
                } catch (Exception e) {
                    response.setErrorMsg("Fail to save User Data" + e.getMessage());
                }
            }
        } else if (req.getRoleType().equals(UserRoleType.EMPLOYEE.name())) {
            if (req.getCompanyID() == 0) {
                response.setErrorMsg("Please enter proper company id");
            } else {
                var companyExit = companyRepo.findById(req.getCompanyID());
                if (companyExit != null && companyExit.isPresent()) {
                    var employeeData = new UserEntities(
                            req.getUsername(),
                            req.getPassword(),
                            req.getFullName(),
                            UserRoleType.valueOf(req.getRoleType()));
                    employeeData.setCompanyID(req.getCompanyID());

                    var userSavedData = userRepo.save(employeeData);
                    java.util.Collection<Integer> allEmployess = companyExit.get().getAllEmployesID();
                    allEmployess.add(userSavedData.getId());
                    companyExit.get().setAllEmployesID(allEmployess);
                    companyRepo.save(companyExit.get());
                    return signin(new UserSigninReq(req.getUsername(), req.getPassword()));
                } else {
                    response.setErrorMsg("Compnay not found by your id.");
                }
            }

        } else {
            response.setErrorMsg("Please add proper Role type");
        }

        return response;

    }

    @Override
    public AppResponse approveOrRejectEmployee(ApproveOrRejectEmployeReq req) {
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(req.getUserID());
        if (userExit != null && userExit.isPresent()) {
            var companyExit = companyRepo.findById(req.getCompanyID());
            if (companyExit != null && companyExit.isPresent()) {
                if (companyExit.get().getOwnerID() == req.getUserID()) {
                    if (companyExit.get().getAllEmployesID().contains(req.getApprovalID())) {
                        var approvalUserExit = userRepo.findById(req.getApprovalID());
                        if (approvalUserExit != null && approvalUserExit.isPresent()) {
                            if (EmployeeApprovalStatus.valueOf(req.getStatus()) == EmployeeApprovalStatus.APPROVE) {
                                approvalUserExit.get().setEmployeApproved(true);
                                approvalUserExit.get().setAccountSuspendReason(null);
                                response.setErrorMsg("Account approve successfully.");
                            } else {
                                approvalUserExit.get().setEmployeApproved(false);
                                approvalUserExit.get().setAccountSuspendReason(req.getRejectReason());
                                response.setErrorMsg("Account rejected successfully.");
                            }
                            response.setStatus(true);
                            userRepo.save(approvalUserExit.get());
                        } else {
                            response.setErrorMsg("Approval user is not exit");
                        }
                    } else {
                        response.setErrorMsg("this employee is not form this company.");
                    }
                } else {
                    response.setErrorMsg("You are not the right person of this compnay.");
                }
            } else {
                response.setErrorMsg("Company not found");
            }
        } else {
            response.setErrorMsg("User not found");
        }
        return response;
    }

    @Override
    public AppResponse findAllEmployesByCompanyID(Integer userID) {
        AppResponse response = new AppResponse();
        var data = userRepo.findByCompanyID(userID);
        if (data != null && !data.isEmpty()) {
            response.setStatus(true);
            response.setData(data);
        } else {
            response.setErrorMsg("data not found");
        }
        return response;
    }

    // private void setUserLeave(Integer userID, Integer companyID) throws Exception
    // {
    // var userExit = userRepo.findById(userID);
    // if (userExit != null && userExit.isPresent()) {
    // var companyExit = companyRepo.findById(companyID);
    // if (companyExit != null && companyExit.isPresent()) {
    // var totalLeave = new HashMap<>();
    // List<LeaveAcitivityEntities> userLeaveActivityList =
    // leaveActivityRepo.findByUserID(userID);
    // for (LeaveAcitivityEntities leaveAcitivityEntities : userLeaveActivityList) {
    // if (leaveAcitivityEntities.getLeaveStatus() == LeaveStatus.APPROVED) {
    // }
    // }
    // totalLeave.put("totalLeaveBalance", userExit.get().getTotalLeaveBalance());
    // totalLeave.put("totalLeaveApproved", userExit.get().getTotalLeaveApproved());
    // totalLeave.put("totalLeavePending", userExit.get().getTotalLeavePending());
    // totalLeave.put("totalLeaveCancelled",
    // userExit.get().getTotalLeaveCancelled());
    // } else {
    // throw new Exception("Company not found");
    // }
    // } else {
    // throw new Exception("User not found");
    // }
    // }

    @Override
    public AppResponse getUserTotalLeave(Integer userID, Integer companyID) {
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(userID);
        if (userExit != null && userExit.isPresent()) {
            var companyExit = companyRepo.findById(companyID);
            if (companyExit != null && companyExit.isPresent()) {
                var totalLeave = new HashMap<>();
                totalLeave.put("totalLeaveBalance", userExit.get().getTotalLeaveBalance());
                totalLeave.put("totalLeaveApproved", userExit.get().getTotalLeaveApproved());
                totalLeave.put("totalLeavePending", userExit.get().getTotalLeavePending());
                totalLeave.put("totalLeaveCancelled", userExit.get().getTotalLeaveCancelled());
                response.setStatus(true);
                response.setData(totalLeave);
            } else {
                response.setErrorMsg("Company not found");
            }
        } else {
            response.setErrorMsg("User not found");
        }
        return response;
    }

}
