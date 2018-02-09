package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.RoleResource;
import org.ldd.ssm.crm.mapper.RoleResourceMapper;
import org.ldd.ssm.crm.service.IRoleResourceService;
import org.springframework.stereotype.Service;
@Service
public class RoleResourceServiceImpl implements IRoleResourceService {
private RoleResourceMapper roleResourceMapper;
	public boolean add(RoleResource roleResource) {
		int activeLines = roleResourceMapper.insertSelective(roleResource);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean deleteByRole(Long roleId) {
		int activeLines = roleResourceMapper.deleteByRole(roleId);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public List<Resource> selectResourceByRole(Long roleId) {
		return roleResourceMapper.selectByRole(roleId);
	}

}
