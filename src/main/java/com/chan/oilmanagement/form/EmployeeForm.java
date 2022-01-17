package com.chan.oilmanagement.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class EmployeeForm {

    private Integer id;

    @Size(max = 6,min = 2,message = "用户名长度在2到6之间")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;

    @Email
    private String email;

    @Size(min = 11, max = 11, message = "手机长度应为11位")
    private String phone;

    private String question;

    private String answer;

    private Integer role;
}
