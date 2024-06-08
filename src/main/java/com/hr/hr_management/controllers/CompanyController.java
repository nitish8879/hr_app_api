package com.hr.hr_management.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hr.hr_management.dao.req.CompanyUpdateReq;
import com.hr.hr_management.dao.req.CreateHolidayReq;
import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.services.CompanyService;
import com.hr.hr_management.utils.models.AppResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/create")
    public AppResponse createHoliday(@RequestBody @Valid CreateHolidayReq req) {
        return companyService.createHoliday(req);
    }

    @GetMapping("/getHoliday")
    public List<HolidayEntity> getHoliday(@RequestParam("companyId") UUID companyId) {
        return companyService.getAllHoliday(companyId);
    }

    @PostMapping("/updateCompany")
    public ResponseEntity<?> updateCompany(@RequestBody @Valid CompanyUpdateReq req) {
        return ResponseEntity.ok(companyService.updateCompany(req));
    }

}
