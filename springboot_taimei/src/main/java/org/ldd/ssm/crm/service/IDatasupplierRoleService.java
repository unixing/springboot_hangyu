package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.DatasupplierRole;

public interface IDatasupplierRoleService {
	public boolean add(DatasupplierRole datasupplierRole);
	
	public boolean delete(Long roleId);
	
	public List<DatasupplierRole> selectAll(Long datasupplierId);
	
	public boolean addRoles(String[] roleIds, Long datasupplierId);
}
