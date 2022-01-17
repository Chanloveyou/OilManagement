package com.chan.oilmanagement.dao;

import com.chan.oilmanagement.pojo.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);

    int countByEmployeeName(String name);

    int countByEmail(String email);

    Employee selectByEmployeeName(String name);

    Employee selectByEmail(String email);
}