package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.Datasupplier;

public interface IDatasupplierService {
	public boolean add(Datasupplier datasupplier);
	
	public boolean delete(Long id);
	
	public boolean update(Datasupplier datasupplier);
	
	public Datasupplier load(Long datasupplierId);
	
	public List<Datasupplier> selectByCompany(Long datSreCpyId);
	
	public List<Datasupplier> selectAll(Long employeeId, String cpyNm);
	
	public Datasupplier load(Long employeeId, Long datSreCpyId);
	
	public List<String> getDataSource(Long companyId);
}
