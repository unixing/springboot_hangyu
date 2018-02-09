package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Department;
import org.ldd.ssm.crm.mapper.DepartmentMapper;
import org.ldd.ssm.crm.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DepartmentServiceImpl implements IDepartmentService {
@Autowired
private DepartmentMapper departmentMapper;
	public boolean add(Department department) {
		int activeLines = departmentMapper.insertSelective(department);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean update(Department department) {
		int activeLines = departmentMapper.updateByPrimaryKeySelective(department);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long id) {
		int activeLines = departmentMapper.deleteByPrimaryKey(id);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public Department load(Long id) {
		Department dpt = null;
		if(id!=null && id>0){
			dpt = departmentMapper.selectByPrimaryKey(id);
		}
		return dpt;
	}

	public List<Department> list(Long companyId, String dptNm) {
		return departmentMapper.selectAll(companyId,dptNm);
	}

}
