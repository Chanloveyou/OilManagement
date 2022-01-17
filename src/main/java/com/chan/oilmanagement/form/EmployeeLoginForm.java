package com.chan.oilmanagement.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EmployeeLoginForm {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
