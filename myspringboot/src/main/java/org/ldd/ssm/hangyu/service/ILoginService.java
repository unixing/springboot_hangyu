package org.ldd.ssm.hangyu.service;

import org.ldd.ssm.hangyu.domain.Employee;

public interface ILoginService {

	boolean checkLogin(String username, String password);
	
	Employee login(String username, String password);

}
