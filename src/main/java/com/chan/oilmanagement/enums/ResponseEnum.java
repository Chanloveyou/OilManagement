package com.chan.oilmanagement.enums;

import lombok.Getter;

@Getter
public enum ResponseEnum {
    ERROR(-1,"服务端错误"),
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USERNAME_EXIST(2,"用户名已存在"),
    PARAM_ERROR(3,"参数错误"),
    EMAIL_EXIST(4,"邮箱已存在"),
    NEED_LOGIN(10,"用户未登录，请先登录"),
    USERNAME_OR_PASSWORD_ERROR(11,"用户名或密码错误"),
    EMAIL_SENDING_ERROR(12,"邮箱发送失败"),
    EMAIL_REQUIRED(13,"邮箱不能为空"),
    CODE_ERROR(14,"验证码错误"),
    CODE_OVERTIME(15,"验证超时");

    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
