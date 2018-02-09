package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.mapper.DatasupplierMapper;
import org.ldd.ssm.crm.mapper.DatasupplierRoleMapper;
import org.ldd.ssm.crm.mapper.EmployeeMapper;
import org.ldd.ssm.crm.service.IDatasupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class DatasupplierServiceImpl implements IDatasupplierService {
@Autowired
private DatasupplierMapper datasupplierMapper;
@Autowired
private DatasupplierRoleMapper datasupplierRoleMapper;
@Autowired
private EmployeeMapper empMapper;
	public boolean add(Datasupplier datasupplier) {
		Datasupplier obj = load(datasupplier.getEmployeeId(),datasupplier.getDatSreCpyId());
		if(obj!=null){
			return false;
		}
		int activeLines = datasupplierMapper.insertSelective(datasupplier);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long id) {
		//删除关联关系
		datasupplierRoleMapper.deleteByDatasupplierId(id);
		int activeLines = datasupplierMapper.deleteByPrimaryKey(id);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean update(Datasupplier datasupplier) {
		Datasupplier obj = load(datasupplier.getEmployeeId(),datasupplier.getDatSreCpyId());
		if(obj!=null){
			return false;
		}
		int activeLines = datasupplierMapper.updateByPrimaryKeySelective(datasupplier);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public List<Datasupplier> selectAll(Long companyId,String cpyNm) {
		List<Datasupplier> list = new ArrayList<Datasupplier>();
		List<Employee> emps = empMapper.getEmployeeByCompanyId(companyId,cpyNm);
		if(emps!=null&&emps.size()>0){
			for(int i=0;i<emps.size();i++){
				List<Datasupplier> dataList = datasupplierMapper.selectAll(emps.get(i).getId());
				if(dataList!=null&&dataList.size()>0){
					list.addAll(dataList);
				}
			}
		}
		return list;
	}
	public Datasupplier load(Long employeeId, Long datSreCpyId) {
		return datasupplierMapper.load(employeeId, datSreCpyId);
	}

	public Datasupplier load(Long datasupplierId) {
		return datasupplierMapper.selectByPrimaryKey(datasupplierId);
	}

	public List<Datasupplier> selectByCompany(Long datSreCpyId) {
		return datasupplierMapper.selectByCompanyId(datSreCpyId);
	}

	public List<String> getDataSource(Long companyId) {
		return datasupplierMapper.getDataSource(companyId);
	}

}
