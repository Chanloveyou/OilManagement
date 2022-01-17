package com.chan.oilmanagement.controller;

import com.chan.oilmanagement.form.EmailForm;
import com.chan.oilmanagement.form.EmployeeForm;
import com.chan.oilmanagement.form.NewPasswordForm;
import com.chan.oilmanagement.pojo.Employee;
import com.chan.oilmanagement.service.impl.EmployeeServiceImpl;
import com.chan.oilmanagement.utils.VerCodeSendingUtil;
import com.chan.oilmanagement.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

import static com.chan.oilmanagement.consts.OilConst.CURRENT_USER;
import static com.chan.oilmanagement.enums.ResponseEnum.*;

@RestController
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @PostMapping("/user/register")
    public ResponseVo register(@Valid @RequestBody EmployeeForm employeeForm,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("注册提交的参数有误,{} {}", Objects.requireNonNull(bindingResult.getFieldError()).getField(),
                    bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(PARAM_ERROR,bindingResult);
        }

        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeForm,employee);
        return employeeService.register(employee);
    }

    @PostMapping("/user/login")
    public ResponseVo login(@Valid @RequestBody EmployeeForm employeeForm,
                            BindingResult bindingResult,
                            HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(PARAM_ERROR);
        }
        ResponseVo<Employee> employeeResponseVo = employeeService.login(employeeForm.getUsername(), employeeForm.getPassword());
        session.setAttribute(CURRENT_USER,employeeResponseVo.getData());
        log.info(" /login sessionId={}",session.getId());

        return employeeResponseVo;
    }

    @GetMapping("/user")
    public ResponseVo viewInfo(HttpSession session) {
        log.info(" /user sessionId={}",session.getId());
        Employee employee = (Employee) session.getAttribute(CURRENT_USER);
        return ResponseVo.success(employee);
    }

    @PostMapping("/user/logout")
    public ResponseVo logout(HttpSession session) {
        Employee employee = (Employee) session.getAttribute(CURRENT_USER);
        session.removeAttribute(CURRENT_USER);
        return ResponseVo.success();
    }

    @PostMapping("/findPassword/email")
    public ResponseVo sendEmail(@Valid @RequestBody EmailForm emailForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return ResponseVo.error(EMAIL_REQUIRED);
        }
        VerCodeSendingUtil verCodeSendingUtil = new VerCodeSendingUtil();
        ResponseVo<Employee> employeeResponseVo = employeeService.sendEmail(emailForm.getEmail(),verCodeSendingUtil);
        if (employeeResponseVo.getData() == null) {
            session.setAttribute("idNum",null);
            return ResponseVo.success();
        }
        session.setAttribute("idNum",employeeResponseVo.getData().getId());
        session.setAttribute("emailUtils",verCodeSendingUtil);
        return ResponseVo.success();
    }

    @PostMapping("/updatePassword")
    public ResponseVo updatePassword(@Valid @RequestBody NewPasswordForm newPasswordForm,
                                     HttpSession session,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors() || !newPasswordForm.getNewPassword().equals(newPasswordForm.getNewPasswordConfirm())) {
            return ResponseVo.error(PARAM_ERROR);
        }
        VerCodeSendingUtil verCodeSendingUtil = (VerCodeSendingUtil) session.getAttribute("emailUtils");
        if (!Integer.valueOf(newPasswordForm.getVerCode()).equals(Integer.valueOf(verCodeSendingUtil.getCode()))) {
            return ResponseVo.error(CODE_ERROR);
        }
        if (session.getAttribute("emailUtils") == null || verCodeSendingUtil.getMinute(verCodeSendingUtil.getSendTime(),new Date()) > 5) {
            session.setAttribute("emailUtils", null);
            return ResponseVo.error(CODE_OVERTIME);
        }

        ResponseVo<Employee> employeeResponseVo = employeeService.renewPassword((Integer) session.getAttribute("idNum"), newPasswordForm.getNewPasswordConfirm());
        session.invalidate();
        return employeeResponseVo;

    }


}
