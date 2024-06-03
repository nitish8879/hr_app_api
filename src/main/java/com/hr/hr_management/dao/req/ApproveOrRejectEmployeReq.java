package com.hr.hr_management.dao.req;

import java.util.UUID;

import com.hr.hr_management.utils.enums.EmployeeApprovalStatus;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApproveOrRejectEmployeReq {
    @NotNull(message = "User ID can't be null.")
    private UUID userID;

    @NotNull(message = "Compnay ID can't be null.")
    private UUID companyID;

    @NotNull(message = "Approval ID can't be null.")
    private UUID approvalID;

    @NotNull(message = "status can't be null.")
    private EmployeeApprovalStatus status;

    @Nullable
    private String rejectReason;

}
