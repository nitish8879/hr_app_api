package com.hr.hr_management.schedulers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
// @Configuration
// @EnableScheduling
public class LeavesScheduler {

    @Value("${cron.expression}")
    private String cronExpression;


    @Scheduled(cron = "#{@leavesScheduler.cronExpression}")
    public void performTask() {
        System.out.println("Heyy nitish");
    }
}
