package com.chan.oilmanagement.service;

import com.chan.oilmanagement.pojo.Employee;
import com.chan.oilmanagement.utils.VerCodeSendingUtil;
import com.chan.oilmanagement.vo.ResponseVo;

public interface IEmployeeService {

    /**
     * 注册
     */
    ResponseVo<Employee> register(Employee employee);

    /**
     * 登录
     */
    ResponseVo<Employee> login(String username, String password);

    /**
     * 发送邮箱并得到邮箱的用户信息
     */
    ResponseVo<Employee> sendEmail(String to, VerCodeSendingUtil verCodeSendingUtil);

    /**
     * 更新密码
     */
    ResponseVo<Employee> renewPassword(Integer id, String password);

}
