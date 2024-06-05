package com.hr.hr_management.dao.req;

import java.util.UUID;

import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberAddReq {

    @NotNull(message = "Creating User ID can't be Null")
    private UUID creatingUserID;

    @NotNull(message = "Team ID cannot be Null")
    private UUID teamID;

    @NotNull(message = "Company ID cannot be Null")
    private UUID companyID;

    @NotNull(message = "Full name cannot be Null")
    private String fullName;

    @NotNull(message = "Username cannot be Null")
    private String userName;

    @NotNull(message = "Roletype cannot be Null")
    private UserRoleType roleType;
}
