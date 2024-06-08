package com.hr.hr_management.dao.req;

import java.sql.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hr.hr_management.utils.enums.LeaveType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class LeaveActivityReq {
    private UUID userID;

    private UUID companyID;

    private UUID approvalTo;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date fromdate;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date todate;

    @NotBlank(message = "leaveReason can't be empty")
    private String leaveReason;

    @NotNull(message = "Leave Type can't be empty")
    private LeaveType leaveType;

}
