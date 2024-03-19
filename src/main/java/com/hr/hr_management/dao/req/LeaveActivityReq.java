package com.hr.hr_management.dao.req;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class LeaveActivityReq {
    private int userID;

    private int companyID;

    private int approvalTo;

    @NotBlank(message = "leaveStatus can't be empty")
    private String leaveStatus;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fromdate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date todate;

    // PENDING,APPROVED, REJECTED
    @NotBlank(message = "leaveReason can't be empty")
    private String leaveReason;

}
