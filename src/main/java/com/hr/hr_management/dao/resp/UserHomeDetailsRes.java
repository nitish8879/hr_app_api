package com.hr.hr_management.dao.resp;

import java.sql.Time;

import com.hr.hr_management.entities.UserEntities;

import lombok.Data;

@Data
public class UserHomeDetailsRes {
    public UserHomeDetailsRes() {
    }

    public UserHomeDetailsRes(Time time, UserEntities user) {
        this.time = time;
        this.user = user;
    }

    private Time time;
    private UserEntities user;
}
