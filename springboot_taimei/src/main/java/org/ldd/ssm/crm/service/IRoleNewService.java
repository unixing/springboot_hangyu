package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.RoleNew;

public interface IRoleNewService {
	
	public boolean add(RoleNew role);
	
	public boolean update(RoleNew role);
	
	public boolean delete(Long roleId);
	
	List<RoleNew> selectRoles(Long createId, String name);
	
	List<RoleNew> getRolesByRoleName(Long createId, String roleName);
}
