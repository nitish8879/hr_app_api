package com.hr.hr_management.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.ApproveOrRejectEmployeReq;
import com.hr.hr_management.dao.req.UserSigninReq;
import com.hr.hr_management.dao.req.UserSignupReq;
import com.hr.hr_management.services.UserService;
import com.hr.hr_management.utils.models.AppResponse;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.UUID;

@RestController
@RequestMapping(path = "/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public AppResponse signin(@RequestBody @Valid UserSigninReq req) {
        return userService.signin(req);
    }

    @PostMapping("/signup")
    public AppResponse signUp(@RequestBody @Valid UserSignupReq req) {
        return userService.signUp(req);
    }

    @PostMapping("/approveOrRejectEmployee")
    public AppResponse approveOrRejectEmployee(@RequestBody @Valid ApproveOrRejectEmployeReq req) {
        return userService.approveOrRejectEmployee(req);
    }

    @GetMapping("/allEmployees/{userID}")
    public AppResponse findAllEmployesByCompanyID(@PathVariable("companyID") UUID companyID) {
        return userService.findAllEmployesByCompanyID(companyID);
    }

    @GetMapping("/getTotalLeave")
    public AppResponse getTotalLeave(@PathVariable("userID") UUID userID, @PathVariable("companyID") UUID companyID) {
        return userService.getUserTotalLeave(userID, companyID);
    }

    @GetMapping("/updatePassword")
    public AppResponse updatePassword(@RequestParam("username") String username,
            @RequestParam("passWord") String passWord, @RequestParam("newpassword") String newPassword) {
        return userService.updatePassword(username, passWord, newPassword);
    }
}
