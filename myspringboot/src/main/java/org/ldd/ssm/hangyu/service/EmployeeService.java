package org.ldd.ssm.hangyu.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.utils.RetBean;

public interface EmployeeService {

	public boolean updateEmployeeIntentionMoneyStatus(Employee employee);
	
	public Employee selectByprimaryKey(Long id);
	
	public RetBean employeePay() throws Exception;
	
	public String selectCpyNmByEmployeeId(Long id);
	
	public boolean updateByPrimaryKeySelective(Employee employee);
	//通过用户角色查找用户
    public List<Employee> selectEmployeesByRole(String role);

	public boolean updateHeadPath(Employee emp);

	public boolean unbindMail(Long id);
	
	public Map<String,Object> validPhone(String phone, String validType);
	
	public Map<String,Object> validEmail(String email, String validType);
	
	public Map<String,Object> updateEmployee(Employee employee);
	
	public Map<String,Object> resetPwd(String contactWay, String newPwd);
}
