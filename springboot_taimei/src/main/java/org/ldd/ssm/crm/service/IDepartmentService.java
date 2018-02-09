package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Department;

public interface IDepartmentService {
	public boolean add(Department department);
	public boolean update(Department department);
	public boolean delete(Long id);
	public Department load(Long id);
	public List<Department> list(Long companyId, String dptNm);
}
