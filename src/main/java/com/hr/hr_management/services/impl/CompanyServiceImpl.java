package com.hr.hr_management.services.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.repo.CompanyRepo;
import com.hr.hr_management.services.CompanyService;
import com.hr.hr_management.utils.models.AppResponse;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepo companyRepo;

    @Override
    public AppResponse createHoliday(UUID compnayID, String label, Date date) {
        AppResponse response = new AppResponse();
        var company = companyRepo.findById(compnayID);
        if (company.isPresent()) {
            HolidayEntity newHoliday = new HolidayEntity(company.get());
            newHoliday.setHolidayDate(date);
            newHoliday.setLabel(label);
            var allHolidays = company.get().getHolidays();
            allHolidays.add(newHoliday);
            company.get().setHolidays(allHolidays);
            try {
                response.setData(companyRepo.save(company.get()));
                response.setStatus(true);
            } catch (Exception e) {
                response.setErrorMsg(e.getMessage());
            }
        } else {
            response.setErrorMsg("Company not found");
        }
        return response;
    }

    @Override
    public List<HolidayEntity> getAllHoliday(UUID compnayID) {
        var company = companyRepo.findById(compnayID);
        return company.get().getHolidays();
    }

}
