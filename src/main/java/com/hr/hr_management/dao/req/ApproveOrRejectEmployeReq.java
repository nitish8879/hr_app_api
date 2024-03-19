package com.hr.hr_management.dao.req;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ApproveOrRejectEmployeReq {
    private int userID;

    private int companyID;

    private int approvalID;

    @Pattern(regexp = "^(APPROVE|REJECT)$", message = "Invalid staus type. Allowed values are APPROVE or REJECT.")
    private String status;

    @Nullable
    private String rejectReason;

}
