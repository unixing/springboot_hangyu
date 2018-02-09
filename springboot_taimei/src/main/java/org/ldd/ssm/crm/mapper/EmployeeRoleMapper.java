package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EmployeeRole;

public interface EmployeeRoleMapper {
    int insert(EmployeeRole record);

    int insertSelective(EmployeeRole record);
    
    int update(EmployeeRole record);
    
    int delete(EmployeeRole record);
    
    List<Employee> getCurrAirportEmployees(@Param("createId") Long createId);
    
    Long load(@Param("itia") String itia, @Param("employeeId") Long employeeId);
    
    int updateEmployeeRoleByRoleId(@Param("roleId") Long roleId, @Param("newRoleId") Long newRoleId);
}