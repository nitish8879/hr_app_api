package com.hr.hr_management.services;

import java.util.Date;
import java.util.List;

import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.utils.models.AppResponse;

public interface CompanyService {

    AppResponse createHoliday(Integer compnayID, String label, Date date);

    List<HolidayEntity> getAllHoliday(Integer compnayID);
}
