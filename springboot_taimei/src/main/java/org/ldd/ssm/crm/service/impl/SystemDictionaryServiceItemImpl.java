package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.domain.SystemDictionaryItem;
import org.ldd.ssm.crm.mapper.SystemDictionaryItemMapper;
import org.ldd.ssm.crm.query.SystemDictionaryItemObject;
import org.ldd.ssm.crm.query.SystemDictionaryItemQuery;
import org.ldd.ssm.crm.service.ISystemDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * service层的实现类
 */
@Service
public class SystemDictionaryServiceItemImpl implements ISystemDictionaryItemService{
	//spring注入dao对象
	private SystemDictionaryItemMapper dao;
	@Autowired
	public void setDao(SystemDictionaryItemMapper dao) {
		this.dao = dao;
		//在调用service层方法的时候, 默认检查数据库是否有表, 没有就创建表
	}
	/**
	 * 添加一个新的管理员
	 */
	public void save(SystemDictionaryItem dept) {
		dao.save(dept);
	}
	/**
	 * 根据id删除管理员
	 */
	public void delete(Long id) {
		dao.delete(id);
	}
	/**
	 * 更新管理员
	 */
	public void update(SystemDictionaryItem dept) {
		dao.update(dept);
	}
	/**
	 * 根据id查询管理员
	 */
	public SystemDictionaryItem get(Long id) {
		return dao.get(id);
	}
	/**
	 * 查询所有的管理员
	 */
	public List<SystemDictionaryItem> getAll() {
		return dao.getAll();
	}
	@SuppressWarnings("unchecked")
	public SystemDictionaryItemObject<SystemDictionaryItem> getItem(
			SystemDictionaryItemQuery iQuery) {
		List<SystemDictionary> rows = dao.query(iQuery);
		Integer total = dao.queryTotal(iQuery);
		@SuppressWarnings("rawtypes")
		SystemDictionaryItemObject<SystemDictionaryItem> systemDictionaryItemObject = new SystemDictionaryItemObject(rows,total);
		return systemDictionaryItemObject;
	}

}
