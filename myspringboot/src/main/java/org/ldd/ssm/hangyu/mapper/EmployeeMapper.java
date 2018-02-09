package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
    
    int updateEmployeeIntentionMoneyStatus(Employee record);
    
    //根据用户id查公司名字
    String selectCpyNmByEmployeeId(Long id);
    //获取某一需求的所有意向方列表及未读信息（以时间排序）
    List<Employee> selectEmployeesByDemandId(@Param("employeeId") Long employeeId, @Param("demandId") Long demandId);
    //通过用户角色查找用户
    List<Employee> selectEmployeesByRole(String role);

	int unbindMail(Long id);

	int updateHeadPath(Employee emp);
	
	int validPhone(@Param("phone") String phone);

	int validBindPhone(@Param("phone") String phone);
	
	int validEmail(@Param("email") String email);
	
	Employee selectEmployeeByContactWay(@Param("contactWay") String contactWay);
	
	int resetPwd(@Param("id") Long id, @Param("newPwd") String newPwd);
}