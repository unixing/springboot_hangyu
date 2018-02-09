package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.MenuNew;

public interface IRoleMenuNewService {

	public List<MenuNew> selectMenuList(Long roleId);
	
	public List<String> getCurrentEmployeeResource(Long roleId);
	
	boolean checkPermission(String resourceUrl);
}
