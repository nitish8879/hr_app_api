package com.hr.hr_management.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.dao.req.CompanyUpdateReq;
import com.hr.hr_management.dao.req.CreateHolidayReq;
import com.hr.hr_management.dao.req.DeleteHolidayReq;
import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.repo.HolidayRepo;
import com.hr.hr_management.services.CompanyService;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    ValidationUserService validationUserService;

    @Autowired
    HolidayRepo holidayRepo;

    @Override
    public AppResponse updateCompany(CompanyUpdateReq req) {
        AppResponse response = new AppResponse();
        validationUserService.isUserValid(req.getUserId(), req.getCompanyID());
        var companyExit = companyRepo.findById(req.getCompanyID());
        if (!companyExit.isPresent()) {
            response.setStatus(false);
            response.setErrorMsg("Company not found");
        } else if (!companyExit.get().getAdmin().getId().toString().equals(req.getUserId().toString())) {
            response.setStatus(false);
            response.setErrorMsg("You are not right person to update this");
        } else {
            companyExit.get().setCompanyName(req.getCompanyName());
            companyExit.get().setWorkingDays(req.getWorkingDays());
            companyExit.get().setInTime(req.getInTime());
            companyExit.get().setOutTime(req.getOutTime());
            var saveData = companyRepo.save(companyExit.get());
            response.setStatus(true);
            response.setData(saveData);
        }
        return response;
    }

    @Override
    public AppResponse createHoliday(CreateHolidayReq req) {
        AppResponse response = new AppResponse();
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        var company = companyRepo.findById(req.getCompanyID());
        HolidayEntity newHoliday = new HolidayEntity(company.get(), req.getDate(), req.getLabel());
        var savedHoliday = holidayRepo.save(newHoliday);
        var allHolidays = company.get().getHolidays();
        allHolidays.add(savedHoliday);
        company.get().setHolidays(allHolidays);
        response.setData(companyRepo.save(company.get()));
        response.setStatus(true);
        return response;
    }

    @Override
    public AppResponse deleteHoliday(DeleteHolidayReq req) {
        validationUserService.isUserValid(req.getUserID(), req.getCompanyID());
        AppResponse response = new AppResponse();
        var foundHoliday = holidayRepo.findById(req.getHolidayID());
        if (!foundHoliday.isPresent()) {
            response.setStatus(false);
            response.setErrorMsg("Holiday not found");
        } else {
            holidayRepo.deleteById(req.getHolidayID());
            response.setStatus(true);
        }

        return response;
    }

    @Override
    public List<HolidayEntity> getAllHoliday(UUID compnayID) {
        var company = companyRepo.findById(compnayID);
        return company.get().getHolidays();
    }

}
