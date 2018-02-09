package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Permission;
import org.ldd.ssm.crm.mapper.PermissionMapper;
import org.ldd.ssm.crm.query.PermissionObject;
import org.ldd.ssm.crm.query.PermissionQuery;
import org.ldd.ssm.crm.service.IPermissionService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 权限的service层
 */
@Service
public class PermissionServiceImpl implements IPermissionService{
	@Autowired
	private PermissionMapper dao;
	
	public void setDao(PermissionMapper dao) {
		this.dao = dao;
		//自动建表
	}

	public void save(Permission pro) {
		dao.save(pro);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

	public void update(Permission pro) {
		dao.update(pro);
	}

	public Permission get(Long id) {
		return dao.get(id);
	}

	public List<Permission> getAll() {
		return dao.getAll();
	}
	//高级查询加分页
	public PermissionObject<Permission> query(PermissionQuery sQuery) {
		//高级查询
		List<Permission> query = dao.query(sQuery);
		//查询总数
		Integer queryTotal = dao.queryTotal(sQuery);
		//封装数据
		PermissionObject<Permission> permissionObject = new PermissionObject<Permission>(query, queryTotal);
		return permissionObject;
	}
	//根据资源路径查询数据库的资源
	public Permission getPermissionByResource(String resourceName) {
		return dao.getPermissionByResource(resourceName);
	}

	public boolean permissionCheckUser(Permission permission) {
		List<Permission> permissionList = dao.userByAllresource(UserContext.getUser().getId());
		Long id = permission.getId();
		//小权限检查
		if(id!=null){
			for (Permission per : permissionList) {
				if(id.equals(per.getId())){
					return true;
				}
			}
		}
		String[] split = permission.getResource().split(":");
		String resourceAll = split[0]+":ALL";
		//大权限检查
		if(id!=null){
			for (Permission per : permissionList) {
				if(resourceAll.equals(per.getResource())){
					return true;
				}
			}
		}
		return false;
	}
}