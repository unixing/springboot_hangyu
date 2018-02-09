package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.ldd.ssm.crm.domain.Company;
import org.ldd.ssm.crm.domain.Datasupplier;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.mapper.CompanyMapper;
import org.ldd.ssm.crm.mapper.DatasupplierMapper;
import org.ldd.ssm.crm.service.ICompanyService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class CompanyServiceImpl implements ICompanyService {
@Autowired
private CompanyMapper companyMapper;
@Autowired
private DatasupplierMapper datasupplierMapper;

	public boolean add(Company company) {
		//获取下一个自增id值
		Long id = companyMapper.nextId();
		int activeLines = companyMapper.insertSelective(company);
		boolean result = false;
		if(activeLines >0){
			Employee emp = UserContext.getUser();
			if(emp.getUsrSts()!=99){
				Datasupplier data = new Datasupplier();
				data.setDatSreCpyId(id);
				data.setEmployeeId(emp.getId());
				datasupplierMapper.insertSelective(data);
			}
			result = true;
		}
		return result;
	}

	public boolean update(Company company) {
		int activeLines = companyMapper.updateByPrimaryKeySelective(company);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public boolean delete(Long companyId) {
		int activeLines = companyMapper.deleteByPrimaryKey(companyId);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}

	public Company load(Long companyId) {
		Company company = null;
		if(companyId !=null && companyId >0){
			company = companyMapper.selectByPrimaryKey(companyId);
		}
		return company;
	}

	public List<Company> selectAll(String cpyNm,Long empId) {
		return companyMapper.selectAll(cpyNm,empId);
	}
	
	public List<Company> getList(Long empId){
		List<Company> companyList = new ArrayList<Company>();
		List<Datasupplier> datasupplierList = null;
		if(empId==null){
			companyList = companyMapper.selectAll(null,null);
		}else{
			datasupplierList = datasupplierMapper.selectAll(empId);
			if(datasupplierList!=null&&datasupplierList.size()>0){
				for(Datasupplier data:datasupplierList){
					Company company=data.getCompany();
					if(company!=null)
						companyList.add(company);
				}
			}
		}
		return companyList;
	}

	public List<Company> selectCompanyByEmployeeId(Long employeeId) {
		return companyMapper.selectCompanyByEmployeeId(employeeId);
	}

}
