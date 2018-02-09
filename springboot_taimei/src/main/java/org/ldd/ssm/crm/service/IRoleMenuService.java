package org.ldd.ssm.crm.service;

import org.ldd.ssm.crm.domain.RoleMenu;

public interface IRoleMenuService {
	boolean add(RoleMenu roleMenu);
	boolean delete(Long roleId, Long menuId);
}
