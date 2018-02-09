package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.DatasupplierRole;
import org.ldd.ssm.crm.mapper.DatasupplierRoleMapper;
import org.ldd.ssm.crm.service.IDatasupplierRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DatasupplierRoleServiceImpl implements IDatasupplierRoleService {
@Autowired
private DatasupplierRoleMapper datasupplierRoleMapper;
	public boolean add(DatasupplierRole datasupplierRole) {
		int activeLines = datasupplierRoleMapper.insertSelective(datasupplierRole);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long roleId) {
		int activeLines = datasupplierRoleMapper.delete(roleId);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}


	public List<DatasupplierRole> selectAll(Long datasupplierId) {
		return datasupplierRoleMapper.selectAll(datasupplierId);
	}

	public boolean addRoles(String[] roleIds, Long datasupplierId) {
		boolean result = false;
		//先删除与相关的角色关系
		datasupplierRoleMapper.deleteByDatasupplierId(datasupplierId);
		if(roleIds!=null&&roleIds.length>0){
			DatasupplierRole datasupplierRole = new DatasupplierRole();
			datasupplierRole.setDatasupplierId(datasupplierId);
			for(String str:roleIds){
				datasupplierRole.setRoleId(Long.valueOf(str));
				result=add(datasupplierRole);
				if(!result){
					break;
				}	
			}
		}
		return result;
	}

}
