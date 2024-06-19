package com.hr.hr_management.schedulers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hr.hr_management.entities.UserEntities;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.UserRepo;

import org.springframework.scheduling.annotation.Scheduled;

@Component
public class LeavesScheduler {

    @Value("${cron.expression}")
    private String cronExpression;

    @Autowired
    CompanyRepo companyRepo;

    @Autowired
    UserRepo userRepo;

    @Scheduled(cron = "${cron.expression}")
    public void performTask() {
        for (var compnay : companyRepo.findAll()) {
            for (var employee : compnay.getUsers()) {
                employee.setTotalCasualAndSickLeave(employee.getTotalCasualAndSickLeave() + compnay.getPerMonthSLCL());
                employee.setTotalPaidLeave(employee.getTotalPaidLeave() + compnay.getPerMonthPL());
                employee.setTotalWorkFromHome(employee.getTotalWorkFromHome() + compnay.getPerMonthWFH());
                userRepo.save(employee);
            }
        }
        System.out.println("Employee Leaves Added Done");
    }
}
