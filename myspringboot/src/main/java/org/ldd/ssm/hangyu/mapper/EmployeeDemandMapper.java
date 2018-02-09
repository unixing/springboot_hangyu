package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.ldd.ssm.hangyu.domain.EmployeeDemand;

public interface EmployeeDemandMapper {
    int insert(EmployeeDemand record);

    int insertSelective(EmployeeDemand record);
    
    int deleteByEmployeeId(Long employeeId);
    
    List<EmployeeDemand> selectByEmployeeId(Long employeeId);
    
    int selectByEmployeeDemand(EmployeeDemand record);
}