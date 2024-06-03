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

    private UUID leaveID;

    private UUID userID;

    private UUID employeeID;

    private UUID companyID;

    @NotNull(message = "Leave status can't be null")
    private LeaveStatus leaveStatus;

    @Nullable
    private String rejectReason;
}
