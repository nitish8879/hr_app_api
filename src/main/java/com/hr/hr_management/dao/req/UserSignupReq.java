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

    private int companyID;

    private String companyName;

    private Time inTime;

    private Time outTime;

    // @Pattern(regexp =
    // "^(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY)(,(MONDAY|TUESDAY|WEDNESDAY|THURSDAY|FRIDAY|SATURDAY|SUNDAY))*$",
    // message = "Invalid list of days. Valid values are MONDAY, TUESDAY, WEDNESDAY,
    // THURSDAY, FRIDAY, SATURDAY, SUNDAY.")
    private List<String> wrokingDays;

}
