package com.hr.hr_management.dao.resp;

import java.util.List;

import com.hr.hr_management.entities.UserEntities;

import lombok.Data;

@Data
public class UserHomeAnalyticsDataRes {
    private Integer totalUsersCount;
    private List<UserEntities> wfh;
    private List<UserEntities> leaveUsers;
    private List<UserHomeDetailsRes> usersLoggedIn;
    private List<UserHomeDetailsRes> usersLoggedOut;
    private List<UserHomeDetailsRes> userOnBreak;

}
