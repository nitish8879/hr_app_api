package com.hr.hr_management.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.services.CompanyService;
import com.hr.hr_management.utils.models.AppResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(value = "/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/create")
    public AppResponse createHoliday(@RequestParam("companyId") Integer companyId,@RequestParam("label") String label,@RequestParam("date") Date date) {
        return companyService.createHoliday(companyId, label, date);
    }

    @GetMapping("/getHoliday")
    public List<HolidayEntity> getMethodName(@RequestParam("companyId") Integer companyId) {
        return companyService.getAllHoliday(companyId);
    }
    
    
}
