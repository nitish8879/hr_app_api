package com.hr.hr_management.dao.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "create")
public class LeaveActivityApproveRejectReq {

    private int leaveID;

    private int userID;

    private int companyID;

    // private int approvalTO;
    // PENDING,APPROVED, REJECTED
    // @Pattern(regexp = "^(REJECTED|APPROVED|PENDING)$", message = "Invalid Leave type. Allowed values are REJECTED|APPROVED|PENDING.")
    @NotBlank(message = "leaveStatus can't be empty")
    private String leaveStatus;

    @Nullable
    private String rejectReason;
}
