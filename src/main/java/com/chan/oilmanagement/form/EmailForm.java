package com.chan.oilmanagement.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class EmailForm {

    @NotNull
    @Email
    private String email;
}
