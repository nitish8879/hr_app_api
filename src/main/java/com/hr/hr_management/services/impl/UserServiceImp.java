package com.hr.hr_management.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.ApproveOrRejectEmployeReq;
import com.hr.hr_management.dao.req.UserSigninReq;
import com.hr.hr_management.dao.req.UserSignupReq;
import com.hr.hr_management.dao.resp.UserSigninOrSingupRes;
import com.hr.hr_management.entities.CompanyEntities;
import com.hr.hr_management.entities.UserEntities;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.LeaveActivityRepo;
import com.hr.hr_management.repo.UserActivitiesRepo;
import com.hr.hr_management.repo.UserRepo;
import com.hr.hr_management.services.UserService;
import com.hr.hr_management.utils.enums.EmployeeApprovalStatus;
import com.hr.hr_management.utils.enums.LeaveStatus;
import com.hr.hr_management.utils.enums.LeaveType;
import com.hr.hr_management.utils.enums.UserActivitiesType;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    UserActivitiesRepo activitiesRepo;

    @Autowired
    LeaveActivityRepo leaveActivityRepo;

    @Autowired
    ValidationUserService validationUserService;

    @Override
    public AppResponse signin(UserSigninReq req) {
        var response = new AppResponse();
        Optional<UserEntities> userData = userRepo.findByUserNameAndPassword(req.getUsername(), req.getPassword());
        if (userData.isPresent()) {
            if (userData.get().getFirstTimeLogin()) {
                throw new RuntimeException("Please set new password.");
            }
            var userResp = new UserSigninOrSingupRes();
            userResp.setUserID(userData.get().getId());
            userResp.setCompanyID(userData.get().getCompany().getId());
            userResp.setUsername(userData.get().getUserName());
            userResp.setFullName(userData.get().getFullName());
            userResp.setCreatedAt(userData.get().getCreatedAt());
            userResp.setRoleType(userData.get().getRoleType().name());

            userResp.setTotalLeaveBalance(userData.get().getTotalLeaveBalance());
            // userResp.setTotalLeaveApproved(userData.get().getTotalLeaveApproved());
            // userResp.setTotalLeavePending(userData.get().getTotalLeavePending());
            // userResp.setTotalLeaveCancelled(userData.get().getTotalLeaveCancelled());

            userResp.setCompanyName(userData.get().getCompany().getCompanyName());
            userResp.setWrokingDays(userData.get().getCompany().getWorkingDays());
            userResp.setInTime(userData.get().getCompany().getInTime());
            userResp.setOutTime(userData.get().getCompany().getOutTime());
            userResp.setEmployeeApproved(userData.get().isEmployeApproved());
            userResp.setAdminID(userData.get().getCompany().getAdmin().getId());
            userResp.setRejectedReason(userData.get().getAccountSuspendReason());
            response.setData(userResp);
            response.setStatus(true);
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
        } else {
            if (req.getInTime() == null || (req.getInTime().toString().isBlank())) {
                response.setErrorMsg("Please set in Time");
            } else if (req.getOutTime() == null || (req.getOutTime().toString().isBlank())) {
                response.setErrorMsg("Please set Out Time");
            } else if (req.getWrokingDays() == null || req.getWrokingDays().isEmpty()) {
                response.setErrorMsg("Please set working days");
            } else {
                try {
                    var newuser = new UserEntities(
                            req.getUsername(),
                            req.getPassword(),
                            req.getFullName(),
                            req.getRoleType());
                    newuser.setEmployeApproved(true);
                    newuser.setFirstTimeLogin(false);
                    var savedUserEntities = userRepo.save(newuser);
                    var newCompany = new CompanyEntities(
                            req.getCompanyName(),
                            savedUserEntities,
                            req.getInTime(),
                            req.getOutTime(),
                            req.getWrokingDays());
                    var savedCompanyUserEntities = companyRepo.save(newCompany);
                    savedUserEntities.setCompany(savedCompanyUserEntities);
                    var savedUserEntities2 = userRepo.save(savedUserEntities);
                    savedCompanyUserEntities.setAdmin(savedUserEntities2);
                    companyRepo.save(savedCompanyUserEntities);
                    return signin(new UserSigninReq(req.getUsername(), req.getPassword()));
                } catch (Exception e) {
                    response.setErrorMsg("Fail to save User Data" + e.getMessage());
                }
            }
        }

        return response;

    }

    @Override
    public AppResponse approveOrRejectEmployee(ApproveOrRejectEmployeReq req) {
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        validationUserService.isUserValid(req.getApprovalID(), req.getCompanyID());
        AppResponse response = new AppResponse();
        var userExit = userRepo.findById(req.getUserID());
        var approvalUserExit = userRepo.findById(req.getApprovalID());
        if (userExit.isPresent()) {
            if (userExit.get().getCompany().getAdmin().getId() == req.getUserID()) {
                if (userExit.get().getCompany().getUsers().contains(approvalUserExit.get())) {
                    if (approvalUserExit.isPresent()) {
                        if (req.getStatus() == EmployeeApprovalStatus.APPROVE) {
                            approvalUserExit.get().setEmployeApproved(true);
                            approvalUserExit.get().setAccountSuspendReason(null);
                            response.setErrorMsg("Account approve.");
                        } else {
                            approvalUserExit.get().setEmployeApproved(false);
                            approvalUserExit.get().setAccountSuspendReason(req.getRejectReason());
                            response.setErrorMsg("Account rejected.");
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
        }
        return response;
    }

    @Override
    public AppResponse findAllEmployesByCompanyID(UUID companyID) {
        AppResponse response = new AppResponse();
        var data = companyRepo.findById(companyID).get().getUsers();
        if (data != null && !data.isEmpty()) {
            response.setStatus(true);
            var allUser = new ArrayList<>();
            for (UserEntities userEntities : data) {
                var map = new HashMap<>();
                map.put("username", userEntities.getUserName());
                map.put("name", userEntities.getFullName());
                map.put("createdAt", userEntities.getCreatedAt());
                map.put("role", userEntities.getRoleType().name());
                map.put("status", userEntities.isEmployeApproved());
                map.put("id", userEntities.getId());
                allUser.add(map);
            }
            response.setData(allUser);
        } else {
            response.setErrorMsg("data not found");
        }
        return response;
    }

    @Override
    public AppResponse getUserTotalLeave(UUID userID, UUID companyID) {
        AppResponse response = new AppResponse();
        validationUserService.isUserValid(userID, companyID);
        var userExit = userRepo.findById(userID);
        if (userExit != null && userExit.isPresent()) {
            var companyExit = companyRepo.findById(companyID);
            if (companyExit != null && companyExit.isPresent()) {
                var totalLeave = new HashMap<>();
                // totalLeave.put("totalLeaveBalance", userExit.get().getTotalLeaveBalance());
                // totalLeave.put("totalLeaveApproved", userExit.get().getTotalLeaveApproved());
                // totalLeave.put("totalLeavePending", userExit.get().getTotalLeavePending());
                // totalLeave.put("totalLeaveCancelled",
                // userExit.get().getTotalLeaveCancelled());
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

    @Override
    public AppResponse updatePassword(String userName, String oldpassword, String newPasswod) {
        var response = new AppResponse();
        Optional<UserEntities> userData = userRepo.findByUserNameAndPassword(userName, oldpassword);
        if (userData.isPresent()) {
            userData.get().setFirstTimeLogin(false);
            userData.get().setPassword(newPasswod);
            userRepo.save(userData.get());
            response.setStatus(true);
        } else {
            response.setErrorMsg("User Not Found.");
        }
        return response;
    }

    @Override
    public Object homeAnalyticsData(UUID userID, UUID companyID) {
        validationUserService.isUserValid(userID, companyID);
        var map = new HashMap<>();
        var now = LocalDate.now();
        var todayDate = LocalDate.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        var foundCompany = companyRepo.findById(companyID);
        // leave or wfh
        List<UserEntities> wfh = new ArrayList<>();
        List<UserEntities> leaveUsers = new ArrayList<>();
        List<UserEntities> userLoggedIn = new ArrayList<>();
        List<UserEntities> userLoggedOut = new ArrayList<>();
        List<UserEntities> userBreakIn = new ArrayList<>();

        var allleaves = leaveActivityRepo.findByCompany_IdAndApplyDate(companyID, todayDate);
        for (int i = 0; i < allleaves.size(); i++) {
            if (allleaves.get(i).getLeaveStatus() == LeaveStatus.APPROVED) {
                if (allleaves.get(i).getLeaveType() == LeaveType.WFH) {
                    wfh.add(allleaves.get(i).getUser());
                } else {
                    leaveUsers.add(allleaves.get(i).getUser());
                }
            }
        }

        var usersActvities = activitiesRepo.findByCompany_IdAndCreatedAt(companyID, todayDate);
        for (int i = 0; i < usersActvities.size(); i++) {
            if (usersActvities.get(i).getInTime() != null) {
                userLoggedIn.add(usersActvities.get(i).getUser());
            }

            if (usersActvities.get(i).getOutTime() != null) {
                userLoggedOut.add(usersActvities.get(i).getUser());
            }

            if (usersActvities.get(i).getExceptedType() == UserActivitiesType.BREAKOUT) {
                userBreakIn.add(usersActvities.get(i).getUser());
            }
        }
        map.put("wfh", wfh);
        map.put("leaveUsers", leaveUsers);
        map.put("totalUsersCount", foundCompany.get().getUsers().size());
        map.put("userBreakIn", userBreakIn);
        map.put("userLoggedOut", userLoggedOut);
        map.put("userLoggedIn", userLoggedIn);
        return map;
    }

}
