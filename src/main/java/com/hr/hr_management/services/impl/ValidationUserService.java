package com.hr.hr_management.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.UserRepo;

@Service
public class ValidationUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CompanyRepo companyRepo;

    public void isUserValid(Integer userID, Integer companyID) {
        var userExit = userRepo.findById(userID);
        if (userExit != null && userExit.isPresent()) {
            if (userExit.get().isEmployeApproved()) {
                var compnayExit = companyRepo.findById(companyID);
                if (compnayExit != null && compnayExit.isPresent()) {
                    if (compnayExit.get().getAllEmployesID().contains(userID)) {
                        return;
                    } else {
                        throw new RuntimeException("User is not from this compnay");
                    }
                } else {
                    throw new RuntimeException("Company not exit exit in db");
                }
            } else {
                throw new RuntimeException("Your account is not active");
            }
        } else {
            throw new RuntimeException("UserNot exit in db");
        }
    }
}
