package com.chan.oilmanagement.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewPasswordForm {

    @NotNull
    String verCode;

    @NotNull
    private String newPassword;

    @NotNull
    private String newPasswordConfirm;
}
