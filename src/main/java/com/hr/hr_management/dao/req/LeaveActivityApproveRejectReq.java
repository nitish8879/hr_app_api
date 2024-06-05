package com.hr.hr_management.dao.req;

import java.util.UUID;

import com.hr.hr_management.utils.enums.LeaveStatus;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class LeaveActivityApproveRejectReq {

    @NotNull(message = "Leave ID can't be Null")
    private UUID leaveID;

    @NotNull(message = "User ID can't be Null")
    private UUID userID;

    @NotNull(message = "Employee ID can't be Null")
    private UUID employeeID;

    @NotNull(message = "Company ID can't be Null")
    private UUID companyID;

    @NotNull(message = "Leave status can't be null")
    private LeaveStatus leaveStatus;

    @Nullable
    private String rejectReason;
}
