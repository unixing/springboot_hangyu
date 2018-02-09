package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Token;
import org.ldd.ssm.crm.web.interceptor.MethodNote;
import org.springframework.stereotype.Service;

@Service
public interface IEmployeeService {

	boolean save(Employee emp);

	boolean delete(Long id);

	boolean update(Employee emp);

	Employee get(Long id);

	List<Employee> list(Long companyId, String usrNm);
	
	@MethodNote(comment="nihaoa2")
	Employee checkLogin(String username, String password);
	Employee getEmployeByUUID(String uuid);

	long getCompanyId(Long id);

	void deleteTokenByemp_id(Long id);

	void saveToken(Token token);

	Employee getToken(String parameter);
	
	boolean unbindMail(Long id);
	
	boolean updateHeadPath(Employee emp);
	
	boolean updateBackgroundPath(Employee emp);
	
	Employee getEmployeeByPhone(String phone);
	
	boolean IsEmployeeBeExist(String userName);

	void updateDefeated_number(String username, int i);

	String findEMP(String userName);

	void updateDefeatedNnone(String username);

	void updateDefeatedNnoneBymobile(String mobile);
	
	boolean updateEmployeeForPwd(Long id, String password);
}
