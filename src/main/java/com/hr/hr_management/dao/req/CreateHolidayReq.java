package com.hr.hr_management.dao.req;

import java.util.Date;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateHolidayReq {
    @NotNull(message = "User ID can't be null")
    private UUID userID;

    @NotNull(message = "Company ID can't be null")
    private UUID companyID;

    @NotNull(message = "Label ID can't be null")
    private String label;

    @NotNull(message = "Date ID can't be null")
    private Date date;
}
