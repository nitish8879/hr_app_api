package com.hr.hr_management.dao.req;

import java.sql.Time;
import java.util.List;

import com.hr.hr_management.utils.enums.UserRoleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class UserSignupReq {

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    @NotBlank(message = "FullName can't be empty")
    private String fullName;

    @NotNull(message = "roleType can't be empty")
    private UserRoleType roleType;

    @NotNull(message = "companyID can't be empty")
    private int companyID;

    @NotNull(message = "companyName can't be empty")
    private String companyName;

    @NotNull(message = "inTime can't be empty")
    private Time inTime;

    @NotNull(message = "outTime can't be empty")
    private Time outTime;

    @NotNull(message = "wrokingDays can't be empty")
    private List<String> wrokingDays;

    @NotNull(message = "Per Month PAID Leave can't be empty")
    private Integer perMonthPL;

    @NotNull(message = "Per Month Sick Leave can't be empty")
    private Integer perMonthSL;

    @NotNull(message = "Per Month Casual Leave can't be empty")
    private Integer perMonthCL;

    @NotNull(message = "Per Month Work From Home can't be empty")
    private Integer perMonthWFH;

}
