package com.hr.hr_management.dao.req;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateTeamReq {
    @NotNull(message = "UserID can't be empty")
    private Integer userID;

    @NotNull(message = "CompanyID can't be empty")
    private Integer companyID;

    private String teamName;

}
