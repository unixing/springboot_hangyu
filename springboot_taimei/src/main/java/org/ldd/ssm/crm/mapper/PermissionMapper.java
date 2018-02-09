package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.Permission;
import org.ldd.ssm.crm.query.PermissionQuery;
/**
 *持久层的dao接口方法
 */
public interface PermissionMapper {
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
	 * 自动创建表
	 */
	/**
	 * 高级查询加分页
	 * @param sQuery
	 * @return
	 */
	List<Permission> query(PermissionQuery sQuery);
	/**
	 * 查询总数
	 */
	Integer queryTotal(PermissionQuery iQuery);
	/**
	 * 根据数据库查询资源
	 */
	Permission getPermissionByResource(String resourceName);
	/**
	 * 查询所有的id
	 * id:  是当前访问的权限id
	 * user:是当前的用户
	 */
	List<Permission> userByAllresource(Long id);
}
