package org.ldd.ssm.crm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.Department;
import org.ldd.ssm.crm.domain.Employee;
import org.ldd.ssm.crm.domain.Token;
import org.ldd.ssm.crm.exception.LogicException;
import org.ldd.ssm.crm.mapper.DepartmentMapper;
import org.ldd.ssm.crm.mapper.EmployeeMapper;
import org.ldd.ssm.crm.service.IEmployeeService;
import org.ldd.ssm.crm.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeServiceImpl implements IEmployeeService{
	Logger log = Logger.getLogger(EmployeeServiceImpl.class);
	private EmployeeMapper dao;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	public void setDao(EmployeeMapper dao) {
		this.dao = dao;
//		dao.createTable();
	}
	public boolean save(Employee emp) {
		int activeLines = dao.save(emp);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}
	public boolean delete(Long id) {
		int activeLines = dao.delete(id);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}
	public boolean update(Employee emp) {
		int activeLines = dao.update(emp);
		boolean result = false;
		if(activeLines>0){
			result = true;
		}
		return result;
	}
	public Employee get(Long id) {
		return dao.get(id);
	}
	public List<Employee> list(Long companyId,String usrNm) {
		List<Employee> empList = new ArrayList<Employee>();
		List<Employee> employeeList = new ArrayList<Employee>();
		List<Department> dptList = departmentMapper.selectAll(companyId, null);
		if(dptList!=null&&dptList.size()>0){
			for(Department dpt:dptList){
				List<Employee> employees = dao.list(dpt.getId());
				if(employees!=null&&employees.size()>0){
					employeeList.addAll(employees);
				}
			}
		}
		if(employeeList.size()>0){
			if(null!=usrNm&&!"".equals(usrNm)){
				for(int i=0;i<employeeList.size();i++){
					Employee emp=employeeList.get(i);
					if(emp.getUsrNm().contains(usrNm)){
						empList.add(emp);
					}
				}
			}else{
				return employeeList;
			}
		}
		return empList;
	}
	public Employee checkLogin(String username, String password) {
		// 检查用户名
		Employee employee = dao.getEmployeeByUsername(username);
		String defaultPwd = "123456";
		if(employee!=null){
			//判断账号登录失败次数
			if(employee.getDefeated_number()>=5){
				if(!TextUtil.isEmpty(employee.getPhone())){
					throw new LogicException("登录错误次数过多",-103);
				}else{
					throw new LogicException("登录错误次数过多,请联系平台",-104);
				}
			}
			boolean continueCheck = employee.isContinueCheck();
			if(!continueCheck){
//				if(TextUtil.isEmpty(employee.getUsrNm())){
//					employee.setUsrNm(username);
//				}
				if(defaultPwd.equals(password)){
					return employee;
				}else{
					throw new LogicException("用户名或者密码错误",-101); 
				}
			}
			// 检查密码
			if(!employee.getUsrPwd().equals(password)){
				throw new LogicException("用户名或者密码错误",-101);
			}
		}else{
			throw new LogicException("账号不存在",-102);
		}
		
		return employee;
	}
	public long getCompanyId(Long id) {
		return dao.getCompanyId(id);
	}
	@Override
	public void deleteTokenByemp_id(Long id) {
		dao.deleteTokenByemp_id(id);
	}
	@Override
	public void saveToken(Token token) {
		dao.saveToken(token);
	}
	@Override
	public Employee getToken(String parameter) {
		
		return dao.getToken(parameter);
	}
	@Override
	public boolean unbindMail(Long id) {
		boolean result = false;
		if(id==null||id.longValue()<=0){
			log.debug("unbindMail:id is invalid");
			return result;
		}
		int activeLines = 0;
		try {
			activeLines = dao.unbindMail(id);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}
	@Override
	public boolean updateHeadPath(Employee emp) {
		boolean result = false;
		try {
			int activeLines = dao.updateHeadPath(emp);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}
	@Override
	public boolean updateBackgroundPath(Employee emp) {
		boolean result = false;
		try {
			int activeLines = dao.updateBackgroundPath(emp);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}
	@Override
	public Employee getEmployeeByPhone(String phone) {
		Employee emp = null;
		if(phone==null||"".equals(phone)){
			log.debug("getEmployeeByPhone:phone is invalid");
			return emp;
		}
		try {
			emp=dao.getEmployeeByPhone(phone);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
			return emp;
		}
		return emp;
	}
	@Override
	public boolean IsEmployeeBeExist(String userName) {
		boolean result = false;
		Employee emps = null;
		if(userName==null||"".equals(userName)){
			log.debug("getEmployeeByUserName:userName is invalid value");
			return result;
		}
		try {
			emps = dao.getEmployeeByUsername(userName);
			if(emps==null){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
			return result;
		}
		return result;
	}
	@Override
	public void updateDefeated_number(String username, int i) {
		if(i==0){
			dao.updateDefeated_number_new(username);
		}else{
			dao.updateDefeated_number(username,i);
		}
	}
	@Override
	public String findEMP(String userName) {
		return dao.findEMP(userName);
	}
	@Override
	public void updateDefeatedNnone(String username) {
		dao.updateDefeatedNnone(username);
	}
	@Override
	public void updateDefeatedNnoneBymobile(String mobile) {
		dao.updateDefeatedNnoneBymobile(mobile);		
	}
	@Override
	public boolean updateEmployeeForPwd(Long id, String password) {
		boolean result = false;
		if(id==null||id==0||StringUtils.isEmpty(password)){
			return result;
		}
		try {
			int activeLine = dao.updateEmployeeForPwd(id, password);
			if(activeLine>0){
				result=true;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
	}
	@Override
	public Employee getEmployeByUUID(String uuid) {
		return dao.getEmployeeByUUID(uuid);
	}
	
}
