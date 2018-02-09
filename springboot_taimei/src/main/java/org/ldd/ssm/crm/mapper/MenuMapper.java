package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Menu;
/**
 *持久层的dao接口方法
 */
public interface MenuMapper {
	/**
	 * 添加新用户
	 */
	void save(Menu pro);
	/**
	 * 根据id删除用户
	 */
	void delete(Long id);
	/**
	 * 更新用户
	 */
	void update(Menu pro);
	/**
	 * 根据id查询用户信息
	 */
	Menu get(Long id);
	/**
	 * 查询所有的用户
	 */
	List<Menu> getAll();
	/**
	 * 查询父菜单
	 */
	List<Menu> getParent();
	/**
	 *查询菜单
	 */
	List<Menu> getChilder(@Param("id") Long id);
	/**
	 * 自动创建表
	 */
	/**
	 * 根据角色查询菜单
	 */
	List<Menu> selectMenuByRole(@Param("roleId") Long roleId);
}
