package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.query.SystemDictionaryQuery;
/**
 * dao的接口,运用SpringMVC机制可以不需要实现类
 */
public interface SystemDictionaryMapper {
	/**
	 * 添加新管理员
	 */
	void save(SystemDictionary dept);
	/**
	 * 根据id删除管理员
	 */
	void delete(Long id);
	/**
	 * 更新管理员
	 */
	void update(SystemDictionary dept);
	/**
	 * 根据id查询管理员信息
	 */
	SystemDictionary get(Long id);
	/**
	 * 查询所有的管理员
	 */
	List<SystemDictionary> getAll();
	/**
	 * 自动简表
	 */
	/**
	 * 高级查询
	 * @param sQuery
	 * @return
	 */
	List<SystemDictionary> query(SystemDictionaryQuery sQuery);
	/**
	 * 查询数据总数
	 * @return
	 */
	Integer queryTotal(SystemDictionaryQuery sQuery);
}
