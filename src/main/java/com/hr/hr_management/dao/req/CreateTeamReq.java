package com.hr.hr_management.dao.req;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTeamReq {
    @NotNull(message = "UserID can't be empty")
    private UUID userID;

    @NotNull(message = "CompanyID can't be empty")
    private UUID companyID;

    private String teamName;

}
