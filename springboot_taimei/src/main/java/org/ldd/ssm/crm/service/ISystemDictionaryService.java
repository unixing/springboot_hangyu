package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.query.SystemDictionaryObject;
import org.ldd.ssm.crm.query.SystemDictionaryQuery;
import org.springframework.stereotype.Service;
/**
 * 管理员service层的接口类
 */
@Service
public interface ISystemDictionaryService {
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
	 * 高级查询
	 * @param sQuery
	 * @return
	 */
	SystemDictionaryObject<SystemDictionary> query(SystemDictionaryQuery sQuery);
}
