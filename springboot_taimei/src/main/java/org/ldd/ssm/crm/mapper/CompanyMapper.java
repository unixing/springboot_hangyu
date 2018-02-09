package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Company;

public interface CompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Company record);

    int insertSelective(Company record);

    Company selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Company record);

    int updateByPrimaryKey(Company record);
    
    List<Company> selectAll(@Param("cpyNm") String cpyNm, @Param("empId") Long empId);
    
    List<Company> getList(@Param("id") Long id);
    
    List<Company> selectCompanyByEmployeeId(@Param("employeeId") Long employeeId);
    
    Long nextId();
}