package com.hr.hr_management.services;

import java.util.List;
import java.util.UUID;

import com.hr.hr_management.dao.req.CompanyUpdateReq;
import com.hr.hr_management.dao.req.CreateHolidayReq;
import com.hr.hr_management.entities.HolidayEntity;
import com.hr.hr_management.utils.models.AppResponse;

public interface CompanyService {

    AppResponse createHoliday(CreateHolidayReq req);

    List<HolidayEntity> getAllHoliday(UUID compnayID);

    AppResponse updateCompany(CompanyUpdateReq req);
}
