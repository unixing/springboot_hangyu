package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.domain.SystemDictionaryItem;
import org.ldd.ssm.crm.query.SystemDictionaryItemQuery;
/**
 * dao的接口,运用SpringMVC机制可以不需要实现类
 */
public interface SystemDictionaryItemMapper {
	/**
	 * 添加新管理员
	 */
	void save(SystemDictionaryItem dept);
	/**
	 * 根据id删除管理员
	 */
	void delete(Long id);
	/**
	 * 更新管理员
	 */
	void update(SystemDictionaryItem dept);
	/**
	 * 根据id查询管理员信息
	 */
	SystemDictionaryItem get(Long id);
	/**
	 * 查询所有的管理员
	 */
	List<SystemDictionaryItem> getAll();
	/**
	 * 自动简表
	 */
	/**
	 * 根据父数据字典目录查询明细字典目录
	 * @param id
	 */
	/**
	 * 高级查询
	 * @param sQuery
	 * @return
	 */
	List<SystemDictionary> query(SystemDictionaryItemQuery iQuery);
	/**
	 * 查询数据总数
	 * @return
	 */
	Integer queryTotal(SystemDictionaryItemQuery iQuery);
}
