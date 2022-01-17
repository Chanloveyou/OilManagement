package com.chan.oilmanagement.service.impl;


import com.chan.oilmanagement.OilManagementApplicationTests;
import com.chan.oilmanagement.enums.ResponseEnum;
import com.chan.oilmanagement.enums.RoleEnum;
import com.chan.oilmanagement.pojo.Employee;
import com.chan.oilmanagement.vo.ResponseVo;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

//@Transactional
class EmployeeServiceImplTest extends OilManagementApplicationTests {

    @Autowired
    EmployeeServiceImpl employeeService;

    public static final String USERNAME = "admin";
    public static final String PASSWORD = "admin";
    public static final String EMAIL = "abc@qq.com";

    @Test
    void register() {
        Employee employee = new Employee(USERNAME,PASSWORD,EMAIL, RoleEnum.CUSTOMER.getCode());
        employeeService.register(employee);

    }

    @Test
    void login() {
        ResponseVo<Employee> responseVo = employeeService.login(USERNAME,PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}