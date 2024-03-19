package com.hr.hr_management.dao.req;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserSigninReq {

    @NotBlank(message = "Username can't be empty")
    private String username;

    @NotBlank(message = "Password can't be empty")
    private String password;

    public UserSigninReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

}
