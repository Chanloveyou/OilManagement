package com.chan.oilmanagement.exception;

import com.chan.oilmanagement.enums.ResponseEnum;
import com.chan.oilmanagement.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVo handle (RuntimeException e) {
        return ResponseVo.error(ResponseEnum.ERROR,e.getMessage());
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle() {
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MailSendingException.class)
    @ResponseBody
    public ResponseVo mailSendingHandle() {
        return ResponseVo.error(ResponseEnum.EMAIL_SENDING_ERROR);
    }
}
