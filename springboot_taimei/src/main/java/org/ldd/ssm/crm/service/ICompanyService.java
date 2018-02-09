package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Company;

public interface ICompanyService {
	public boolean add(Company company);
	public boolean update(Company company);
	public boolean delete(Long companyId);
	public Company load(Long companyId);
	List<Company> selectAll(String cpyNm, Long empId);
	List<Company> getList(Long empId);
	List<Company> selectCompanyByEmployeeId(Long employeeId);
}
