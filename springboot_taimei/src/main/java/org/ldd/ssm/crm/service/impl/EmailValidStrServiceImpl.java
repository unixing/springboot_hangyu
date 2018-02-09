package org.ldd.ssm.crm.service.impl;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.EmailValidStr;
import org.ldd.ssm.crm.mapper.EmailValidStrMapper;
import org.ldd.ssm.crm.service.IEmailValidStrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmailValidStrServiceImpl implements IEmailValidStrService {
	@Autowired
	private EmailValidStrMapper objMapper;
	private Logger log = Logger.getLogger(EmailValidStrServiceImpl.class);
	@Override
	public boolean addOrUpdate(EmailValidStr emailValidStr) {
		boolean result = false;
		if(emailValidStr==null){
			log.debug("add:emailValidStr is invalid");
			return result;
		}
		try {
			EmailValidStr obj = objMapper.selectByEmployeeId(emailValidStr.getEmployeeId());
			int activeLines = 0;
			if(obj!=null){
				emailValidStr.setId(obj.getId());
				activeLines = objMapper.updateByPrimaryKeySelective(emailValidStr);
			}else{
				activeLines = objMapper.insertSelective(emailValidStr);
			}
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
	public boolean delete(int id) {
		boolean result = false;
		if(id<=0){
			log.debug("add:emailValidStr is invalid");
			return result;
		}
		try {
			int activeLines = objMapper.deleteByPrimaryKey(id);
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
	public EmailValidStr load(Long employeeId) {
		EmailValidStr obj = null;
		if(employeeId==null||employeeId.longValue()<=0){
			log.debug("load:employeeId is invalid");
			return obj;
		}
		try {
			obj = objMapper.selectByEmployeeId(employeeId);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return obj;
		}
		return obj;
	}

}
