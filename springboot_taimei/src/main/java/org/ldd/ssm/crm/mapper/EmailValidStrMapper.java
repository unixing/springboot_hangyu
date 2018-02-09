package org.ldd.ssm.crm.mapper;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.EmailValidStr;

public interface EmailValidStrMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EmailValidStr record);

    int insertSelective(EmailValidStr record);

    EmailValidStr selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EmailValidStr record);

    int updateByPrimaryKey(EmailValidStr record);
    
    EmailValidStr selectByEmployeeId(@Param("employeeId") Long employeeId);
}