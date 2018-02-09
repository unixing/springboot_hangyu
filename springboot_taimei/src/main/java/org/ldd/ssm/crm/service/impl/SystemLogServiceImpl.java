package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.SystemLog;
import org.ldd.ssm.crm.mapper.SystemLogMapper;
import org.ldd.ssm.crm.query.PageResult;
import org.ldd.ssm.crm.query.SystemLogQuery;
import org.ldd.ssm.crm.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 系统日志的service层的实现对象
 */
@Service
public class SystemLogServiceImpl implements ISystemLogService {

	private SystemLogMapper dao;
	@Autowired
	public void setDao(SystemLogMapper dao) {
		// 由Spring注入dao
		this.dao = dao;
		
		// 自动建表
	}
	
	public void save(SystemLog obj) {
		dao.save(obj);
	}

	public void update(SystemLog obj) {
		dao.update(obj);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

	public SystemLog get(Long id) {
		return dao.get(id);
	}

	public List<SystemLog> list() {
		return dao.list();
	}

	public PageResult<SystemLog> query(SystemLogQuery query) {
		PageResult<SystemLog> pr = new PageResult<SystemLog>();
		
		// 添加符合条件的总记录数
		Long total = dao.count(query);
		pr.setTotal(total );
		
		// 添加当前页显示的内容
		List<SystemLog> rows = dao.query(query);
		pr.setRows(rows);
		
		return pr;
	}

	
}
