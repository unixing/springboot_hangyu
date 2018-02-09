package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.RoleResource;

public interface IRoleResourceService {
	boolean add(RoleResource roleResource);
	boolean deleteByRole(Long roleId);
	List<Resource> selectResourceByRole(Long roleId);
}
