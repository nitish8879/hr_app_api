package com.hr.hr_management.dao.req;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyUpdateReq {

    @NotNull(message = "User Id can't be null")
    private UUID userId;

    @NotNull(message = "Company Id can't be null")
    private UUID companyID;

    @NotNull(message = "companyName can't be null")
    private String companyName;

    @NotNull(message = "In Time can't be null")
    private Time inTime;

    @NotNull(message = "Out Time can't be null")
    private Time outTime;

    @NotNull(message = "Working days can't be null")
    private List<String> workingDays;
}
