package org.ldd.ssm.hangyu.service.impl;

import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.mapper.LoginMapper;
import org.ldd.ssm.hangyu.service.ILoginService;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginServiceImpl implements ILoginService{
	@Autowired
	private LoginMapper loginMapper;

	@Override
	public boolean checkLogin(String username, String password) {
		Employee emp = loginMapper.checkLogin(username, password);
		if(emp==null){
			return false;
		}else{
			UserContext.setUser(emp);
			UserContext.setEmployee(emp.getUsername(), emp);
			return true;
		}
	}

	@Override
	public Employee login(String username,
			String password) {
		Employee emp = loginMapper.checkLogin(username, password);
		if(emp==null){
			return emp;
		}else{
			UserContext.setUser(emp);
			UserContext.setEmployee(emp.getUsername(), emp);
			return emp;
		}
	}
}
