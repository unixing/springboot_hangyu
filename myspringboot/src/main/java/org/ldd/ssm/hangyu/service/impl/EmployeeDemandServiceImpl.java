package org.ldd.ssm.hangyu.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.Employee;
import org.ldd.ssm.hangyu.domain.EmployeeDemand;
import org.ldd.ssm.hangyu.mapper.EmployeeDemandMapper;
import org.ldd.ssm.hangyu.service.EmployeeDemandService;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeDemandServiceImpl implements EmployeeDemandService {
	@Autowired
	private EmployeeDemandMapper employeeDemandMapper;
	Logger log = Logger.getLogger(EmployeeDemandServiceImpl.class);
	

	@Override
	public boolean add(EmployeeDemand employeeDemand) {
		boolean result = false;
		if(employeeDemand==null){
			log.debug("add:employeeDemand is a invalid parameter");
			return result;
		}
		try {
			int activeLines = employeeDemandMapper.insertSelective(employeeDemand);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error("add:there is something wrong here",e);
			e.printStackTrace();
			return result;
		}
		return result;
	}
	
	@Override
	public boolean batchAdd(String employeeDemandIds) {
		boolean result = false;
		if(TextUtil.isEmpty(employeeDemandIds)){
			log.debug("add:employeeDemandIds is a invalid parameter");
			return result;
		}
		try {
			Employee emp = UserContext.getUser();
			Long employeeId = emp.getId();
			String[] ids = employeeDemandIds.split(",");
			if(ids!=null&&ids.length>0){
				boolean flag = true;
				for(String id:ids){
					EmployeeDemand employeeDemand = new EmployeeDemand();
					employeeDemand.setDemandId(Long.valueOf(id));
					employeeDemand.setEmployeeId(employeeId);
					int activeLines = employeeDemandMapper.insertSelective(employeeDemand);
					if(activeLines==0){
						flag = false;
						break;
					}
				}
				if(flag){
					result = true;
				}
			}
		} catch (Exception e) {
			log.error("add:there is something wrong here",e);
			e.printStackTrace();
			return result;
		}
		return result;
	}
	@Override
	public boolean employeeDemandDel(Long employeeId) {
		boolean result = false;
		if(employeeId==null||employeeId==0){
			log.debug("employeeDemandDel:employeeId is invalid");
			return result;
		}
		try {
			int activeLines = employeeDemandMapper.deleteByEmployeeId(employeeId);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.error("employeeDemandDel:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}
	@Override
	public List<EmployeeDemand> employeeDemandFind(Long employeeId) {
		List<EmployeeDemand> employeeDemands = null;
		if(employeeId==null||employeeId==0){
			log.debug("employeeDemandFind:employeeId is invalid");
			return employeeDemands;
		}
		try {
			employeeDemands = employeeDemandMapper.selectByEmployeeId(employeeId);
		} catch (Exception e) {
			log.error("employeeDemandFind:there is something wrong here");
			e.printStackTrace();
			return employeeDemands;
		}
		return employeeDemands;
	}
	@Override
	public int selectByEmployeeDemand(EmployeeDemand record) {
		
		int result = -1;
		if(record.getDemandId()==null||record.getDemandId()==0||record.getEmployeeId()==null||record.getEmployeeId()==0){
			log.debug("selectByEmployeeDemand:EmployeeDemand record is invalid");
			return result;
		}
		try {
			result = employeeDemandMapper.selectByEmployeeDemand(record);
		} catch (Exception e) {
			log.error("selectByEmployeeDemand:there is something wrong here");
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
