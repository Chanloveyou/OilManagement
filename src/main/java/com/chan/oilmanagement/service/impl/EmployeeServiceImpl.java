package com.chan.oilmanagement.service.impl;

import com.chan.oilmanagement.dao.EmployeeMapper;
import com.chan.oilmanagement.enums.ResponseEnum;
import com.chan.oilmanagement.pojo.Employee;
import com.chan.oilmanagement.service.IEmployeeService;
import com.chan.oilmanagement.utils.VerCodeSendingUtil;
import com.chan.oilmanagement.vo.ResponseVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

import static com.chan.oilmanagement.enums.ResponseEnum.*;

@Service
@Data
public class EmployeeServiceImpl implements IEmployeeService {


    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public ResponseVo<Employee> register(Employee employee) {
        //username不能重复
        int countByEmployeeName = employeeMapper.countByEmployeeName(employee.getUsername());
        if (countByEmployeeName > 0) {
            return ResponseVo.error(USERNAME_EXIST);
        }

        //email不能重复
        int countByEmail = employeeMapper.countByEmail(employee.getEmail());
        if (countByEmail > 0) {
            return ResponseVo.error(EMAIL_EXIST);
        }
        //MD5摘要算法
        employee.setPassword(DigestUtils.md5DigestAsHex
                (employee.getPassword().getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int i = employeeMapper.insertSelective(employee);
        if (i == 0) {
            return ResponseVo.error(ERROR);
        }

        return ResponseVo.success();
    }

    @Override
    public ResponseVo<Employee> login(String username, String password) {
        Employee employee = employeeMapper.selectByEmployeeName(username);
        if (employee == null) {
            return ResponseVo.error(USERNAME_OR_PASSWORD_ERROR);
        }
        if (!employee.getPassword().equalsIgnoreCase(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        employee.setPassword(" ");
        return ResponseVo.success(employee);
    }

    @Override
    public ResponseVo<Employee> sendEmail(String to, VerCodeSendingUtil verCodeSendingUtil) {
        verCodeSendingUtil.sendMail(to,mailSender);
        Employee employee = employeeMapper.selectByEmail(to);
        return ResponseVo.success(employee);
    }

    @Override
    public ResponseVo<Employee> renewPassword(Integer id, String password) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        //MD5摘要算法
        employee.setPassword(DigestUtils.md5DigestAsHex
                (password.getBytes(StandardCharsets.UTF_8)));

        //写入数据库
        int i = employeeMapper.updateByPrimaryKeySelective(employee);
        if (i == 0) {
            return ResponseVo.error(ERROR);
        }



        return ResponseVo.success();

    }
}
