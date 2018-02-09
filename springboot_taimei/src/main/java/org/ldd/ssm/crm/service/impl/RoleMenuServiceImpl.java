package org.ldd.ssm.crm.service.impl;

import org.ldd.ssm.crm.domain.RoleMenu;
import org.ldd.ssm.crm.mapper.RoleMenuMapper;
import org.ldd.ssm.crm.service.IRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class RoleMenuServiceImpl implements IRoleMenuService {
@Autowired
private RoleMenuMapper roleMenuMapper;
	public boolean add(RoleMenu roleMenu) {
		int activeLines = roleMenuMapper.insertSelective(roleMenu);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long roleId,Long menuId) {
		int activeLines = roleMenuMapper.delete(roleId, menuId);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}
}
