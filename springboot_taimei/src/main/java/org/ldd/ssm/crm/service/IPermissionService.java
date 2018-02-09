package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Permission;
import org.ldd.ssm.crm.query.PermissionObject;
import org.ldd.ssm.crm.query.PermissionQuery;
/**
 *持久层的dao接口方法
 */
public interface IPermissionService {
	/**
	 * 添加新用户
	 */
	void save(Permission pro);
	/**
	 * 根据id删除用户
	 */
	void delete(Long id);
	/**
	 * 更新用户
	 */
	void update(Permission pro);
	/**
	 * 根据id查询用户信息
	 */
	Permission get(Long id);
	/**
	 * 查询所有的用户
	 */
	List<Permission> getAll();
	/**
	 * 高级查询
	 */
	PermissionObject<Permission> query(PermissionQuery sQuery);
	/**
	 * 根据资源名查询数据库
	 */
	Permission getPermissionByResource(String resourceName);
	/**
	 * 验证权限
	 */
	boolean permissionCheckUser(Permission permission);
}
