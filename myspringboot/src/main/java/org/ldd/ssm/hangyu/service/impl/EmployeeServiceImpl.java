package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.mapper.EmployeeMapper;
import org.ldd.ssm.hangyu.mapper.IndividualCapitalAccountMapper;
import org.ldd.ssm.hangyu.service.EmployeeService;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private IndividualCapitalAccountMapper accountMapper;

	Logger log = Logger.getLogger(EmployeeServiceImpl.class);

	@Override
	public boolean updateEmployeeIntentionMoneyStatus(Employee employee) {
		boolean result = false;
		if (employee.getId() == null || employee.getId() == 0
				|| TextUtil.isEmpty(employee.getIntentionMoney())) {
			log.debug("updateEmployeeIntentionMoneyStatus:employee.id or employee.intentionMoney is null");
			return result;
		}

		try {
			int activelines = employeeMapper
					.updateEmployeeIntentionMoneyStatus(employee);
			if (activelines == 1) {
				result = true;
			}
		} catch (Exception e) {
			log.error("updateEmployeeIntentionMoneyStatus:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public Employee selectByprimaryKey(Long id) {
		Employee employee = null;
		if (id == 0 || id == null) {
			log.debug("selectByprimaryKey:id is null");
			return employee;
		}

		try {
			employee = employeeMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("selectByprimaryKey:there is something wrong here");
			e.printStackTrace();
			return employee;
		}

		return employee;
	}

	@Override
	public RetBean employeePay() throws Exception {
		RetBean retBean=new RetBean();
		IndividualCapitalAccount account=accountMapper.selectByEmployeeId(UserContext.getUser().getId());
		if (account==null) {
			retBean.setMassage("账户不存在");
			return retBean;
		}
		if (!"0".equals(UserContext.getUser().getIntentionMoney())) {
			 Employee paramEmployee=new Employee();
			 paramEmployee.setId(UserContext.getUser().getId());
			 paramEmployee.setIntentionMoney("0");
			if (!updateEmployeeIntentionMoneyStatus(paramEmployee)){
				retBean.setMassage("employee表修改失败");
				retBean.setBool(true);
				return retBean;
			}
		
			//充钱
			IndividualCapitalAccount param=new IndividualCapitalAccount();
			param.setId(account.getId());
			param.setNumber((Double.valueOf(account.getNumber())+100000)+"");
			if (accountMapper.updateByPrimaryKeySelective(param)!=1) {
				retBean.setMassage("账户表修改失败");
				retBean.setBool(true);
				return retBean;
			}else {
				//充钱成功后改余额
				account.setNumber(param.getNumber());
				//改session中意向金状态
				//修改session中的意向金状态
				Employee emp=UserContext.getUser();
				emp.setIntentionMoney("0");
				UserContext.rmoveUser();
				UserContext.setUser(emp);
			}
			
		}
		
		//余额不足
		if (Double.valueOf(account.getNumber())<=0||Double.valueOf(account.getNumber())<=1000d) {
			retBean.setMassage("余额不足");
			retBean.setBool(true);
			return retBean;
		}else {
			//缴费
			IndividualCapitalAccount param=new IndividualCapitalAccount();
			param.setId(account.getId());
			param.setNumber((Double.valueOf(account.getNumber())-1000d)+"");
			if (accountMapper.updateByPrimaryKeySelective(param)!=1) {
				retBean.setMassage("缴费失败");
				retBean.setBool(true);
				return retBean;
			}else {
				//设置缴费后余额
				account.setNumber(param.getNumber());
				retBean.setMassage("缴费成功");
			}
		}
		return retBean;
	}
	/**
	 * 加班把自己绕进去了，先别删，后面可能能用到
	 */
	/*
	 * //检验employee表intentionmoney状态（若不为0改为0）；检验IndividualCapitalAccount表余额（若小于0且
	 * -10000后小于0则回滚，否则-1000）
	 * 
	 * @Override
	 * 
	 * @Transactional(propagation=Propagation.REQUIRED,rollbackFor=Exception.class
	 * )//不成功回滚全部操作 public RetBean employeePay() throws Exception{ RetBean
	 * retBean=new RetBean(); if
	 * (!"0".equals(UserContext.getUser().getIntentionMoney())) { Employee
	 * paramEmployee=new Employee();
	 * paramEmployee.setId(UserContext.getUser().getId());
	 * paramEmployee.setIntentionMoney("0"); if
	 * (updateEmployeeIntentionMoneyStatus(paramEmployee)){
	 * retBean.setMassage("employee表修改失败"); return retBean; }
	 * retBean.setBool(true); retBean.setMassage("充100000"); }
	 * //用户表核验完处理IndividualCapitalAccount表 IndividualCapitalAccount
	 * account=accountMapper.selectByEmployeeId(UserContext.getUser().getId());
	 * if (account!=null) { if (retBean.isBool()) { //充100000
	 * IndividualCapitalAccount paramaAccount=new IndividualCapitalAccount();
	 * paramaAccount.setId(account.getId());
	 * paramaAccount.setNumber("100000.00");
	 * if(1!=accountMapper.updateByPrimaryKeySelective(paramaAccount)){
	 * retBean.setMassage("充值失败"); return retBean; }else {
	 * retBean.setBool(false); retBean.setMassage("充值成功");
	 * account.setNumber(Double.valueOf(account.getNumber()+100000d)+""); } }
	 * //账户存在检验余额 if (Double.valueOf(account.getNumber().toString())<0) {
	 * retBean.setMassage("余额为0，请充值"); return retBean; }else if
	 * (Double.valueOf(account.getNumber().toString())<10000d) {
	 * retBean.setMassage("余额不足10000"); return retBean; }else { //余额充足，开始扣钱
	 * IndividualCapitalAccount paramaAccount=new IndividualCapitalAccount();
	 * paramaAccount.setId(account.getId());
	 * paramaAccount.setNumber((Double.valueOf
	 * (account.getNumber().toString())-1000d)+"");
	 * if(1!=accountMapper.updateByPrimaryKeySelective(paramaAccount)){
	 * retBean.setMassage("扣款失败"); return retBean; }else {
	 * retBean.setBool(true); retBean.setMassage("交费成功"); } }
	 * 
	 * }else { retBean.setMassage("账户不存在"); return retBean; } return retBean; }
	 */

	@Override
	public String selectCpyNmByEmployeeId(Long id) {
		String CpyNm=null;
		if (id==0||id==null) {
			log.debug("selectCpyNmByEmployeeId:id is null");
			return CpyNm;
		}
		try {
			CpyNm = employeeMapper.selectCpyNmByEmployeeId(id);
		} catch (Exception e) {
			log.error("selectCpyNmByEmployeeId:there is something wrong here");
			e.printStackTrace();
			return CpyNm;
		}
		
		return CpyNm;
	}

	@Override
	public boolean updateByPrimaryKeySelective(Employee employee) {
		boolean result=false;
		if (employee==null) {
			log.debug("updateByPrimaryKeySelective:employee is null");
			return result;
		}
		
		try {
			int activeLines=employeeMapper.updateByPrimaryKeySelective(employee);
			if (activeLines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKeySelective:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		
		return result;
	}

	@Override
	public List<Employee> selectEmployeesByRole(String role) {
		List<Employee> list=new ArrayList<Employee>();
		if(TextUtil.isEmpty(role)){
			log.debug("selectEmployeesByRole:role is null");
			return list;
		}
		
		try {
			list=employeeMapper.selectEmployeesByRole(role);
		} catch (Exception e) {
			log.error("selectEmployeesByRole:there is something wrong here");
			return list;
		}
		return list;
	}

	@Override
	public boolean updateHeadPath(Employee emp) {
		boolean result = false;
		try {
			int activeLines = employeeMapper.updateHeadPath(emp);
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
	public boolean unbindMail(Long id) {
		boolean result = false;
		if(id==null||id.longValue()<=0){
			log.debug("unbindMail:id is invalid");
			return result;
		}
		int activeLines = 0;
		try {
			activeLines = employeeMapper.unbindMail(id);
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
	public Map<String, Object> validPhone(String phone,String validType) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(phone)){
			map.put("opResult", "3");
			return map;
		}
		try {
			if("0".equals(validType)){//验证手机号是否已经存在
				int count = employeeMapper.validPhone(phone);
				if(count==0){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}else{//验证绑定的手机号
				int count = employeeMapper.validBindPhone(phone);
				if(count==1){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("There are errors in code");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> validEmail(String email, String validType) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(TextUtil.isEmpty(email)){
			map.put("opResult", "3");
			return map;
		}
		try {
			int count = employeeMapper.validEmail(email);
			if("0".equals(validType)){//验证邮箱是否已经绑定
				if(count==0){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}else{//验证绑定的邮箱
				if(count==1){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("There are errors in code");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> updateEmployee(Employee employee) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(employee==null){
			log.debug("updateEmployee:employee is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			int activeLines = employeeMapper.updateByPrimaryKeySelective(employee);
			if(activeLines==0){
				map.put("opResult", "1");
			}else{
				//修改用户成功后变更session中的用户信息
				Employee emp = employeeMapper.selectByPrimaryKey(employee.getId());
				UserContext.setUser(emp);
				map.put("opResult", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("There are errors in code");
			map.put("opResult", "2");
			return map;
		}
		return map;
	}

	@Override
	public Map<String, Object> resetPwd(String contactWay, String newPwd) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(TextUtil.isEmpty(contactWay)||TextUtil.isEmpty(newPwd)){
			log.debug("updatePwd:contactWay or newPwd is an invalid parameter");
			map.put("opResult", "3");
			return map;
		}
		try {
			//通过绑定的手机或邮箱修改登录用户的密码
			Employee emp = employeeMapper.selectEmployeeByContactWay(contactWay);
			if(emp==null){
				map.put("opResult", "1");
			}else{
				if(emp.getPassword().equals(newPwd)){
					map.put("opResult", "4");
					return map;
				}
				int activeLines = employeeMapper.resetPwd(emp.getId(), newPwd);
				if(activeLines>0){
					map.put("opResult", "0");
				}else{
					map.put("opResult", "1");
				}
			}
		} catch (Exception e) {
			log.error("There are errors in code");
			e.printStackTrace();
			map.put("opResult", "2");
			return map;
		}
		return map;
	}
}
