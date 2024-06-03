package com.hr.hr_management.dao.req;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MemberAddReq {

    @NotNull(message = "UserID can't be empty")
    private UUID creatingUserID;

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @NotNull(message = "TeamID cannot be null")
    private UUID teamID;

    @NotBlank(message = "Username cannot be blank")
    private String userName;

    @NotNull(message = "Company ID cannot be null")
    @Positive(message = "Company ID must be a positive number")
    private UUID companyID;
}
