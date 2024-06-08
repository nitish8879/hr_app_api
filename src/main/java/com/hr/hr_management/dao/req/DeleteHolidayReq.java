package com.hr.hr_management.dao.req;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DeleteHolidayReq {
    @NotNull(message = "User ID can't be null")
    private UUID userID;

    @NotNull(message = "Company ID can't be null")
    private UUID companyID;

    @NotNull(message = "Company ID can't be null")
    private UUID holidayID;
}
