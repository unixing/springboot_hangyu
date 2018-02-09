package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.EmployeeRole;

public interface IEmployeeRoleService {
	
	public boolean add(Employee emp);
	
	public boolean update(List<Employee> emps);
	
	public boolean delete(EmployeeRole empRole);

	public List<Employee> getCurrAirportEmployees(Long createId, String name);
	
	Long load(String itia, Long employeeId);
}
