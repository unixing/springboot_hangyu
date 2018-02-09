package org.ldd.ssm.crm.service;
import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.Role;

public interface IRoleService {
	boolean add(Role role);
	boolean delete(Long roleId);
	boolean update(Role role);
	Role load(Long roleId);
	List<Role> selectRoles(Long employeeId, Long datSreCpyId, String name);
	List<Role> getList(Long companyId, Long employeeId, Long ralateId);
	boolean savePermission(String[] menuIds, String[] resourceIds, Long roleId);
	Map<String,Object> getCurrentPermission(Long companyId, Long employeeId);
}
