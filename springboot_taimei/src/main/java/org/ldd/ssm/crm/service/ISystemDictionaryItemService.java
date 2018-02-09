package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionaryItem;
import org.ldd.ssm.crm.query.SystemDictionaryItemObject;
import org.ldd.ssm.crm.query.SystemDictionaryItemQuery;
import org.springframework.stereotype.Service;
/**
 * 管理员service层的接口类
 */
@Service
public interface ISystemDictionaryItemService {
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
	 * 查询当前数据字典目录下的明细
	 * @param id
	 */
	SystemDictionaryItemObject<SystemDictionaryItem> getItem(
            SystemDictionaryItemQuery iQuery);
}
