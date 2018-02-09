package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemDictionary;
import org.ldd.ssm.crm.mapper.SystemDictionaryMapper;
import org.ldd.ssm.crm.query.SystemDictionaryObject;
import org.ldd.ssm.crm.query.SystemDictionaryQuery;
import org.ldd.ssm.crm.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * service层的实现类
 */
@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService{
	//spring注入dao对象
	private SystemDictionaryMapper dao;
	@Autowired
	public void setDao(SystemDictionaryMapper dao) {
		this.dao = dao;
		//在调用service层方法的时候, 默认检查数据库是否有表, 没有就创建表
	}
	/**
	 * 添加一个新的管理员
	 */
	public void save(SystemDictionary dept) {
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
	public void update(SystemDictionary dept) {
		dao.update(dept);
	}
	/**
	 * 根据id查询管理员
	 */
	public SystemDictionary get(Long id) {
		return dao.get(id);
	}
	/**
	 * 查询所有的管理员
	 */
	public List<SystemDictionary> getAll() {
		return dao.getAll();
	}
	public SystemDictionaryObject<SystemDictionary> query(SystemDictionaryQuery sQuery) {
		Integer total = dao.queryTotal(sQuery);
		List<SystemDictionary> rows = dao.query(sQuery);
		SystemDictionaryObject<SystemDictionary> systemDictionaryObject = new SystemDictionaryObject<SystemDictionary>(rows, total);
		return systemDictionaryObject;
	}

}
