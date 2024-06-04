package com.hr.hr_management.dao.req;

import java.util.UUID;

import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MemberAddReq {

    private UUID creatingUserID;

    @NotNull(message = "Full name cannot be blank")
    private String fullName;

    private UUID teamID;

    @NotNull(message = "Username cannot be blank")
    private String userName;

    @NotNull(message = "Company ID cannot be null")
    private UUID companyID;

    private UserRoleType roleType;
}
