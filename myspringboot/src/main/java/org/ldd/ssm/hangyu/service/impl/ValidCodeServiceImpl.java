package org.ldd.ssm.hangyu.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.ValidCode;
import org.ldd.ssm.hangyu.mapper.ValidCodeMapper;
import org.ldd.ssm.hangyu.service.ValidCodeService;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ValidCodeServiceImpl implements ValidCodeService {
	@Autowired
	private ValidCodeMapper objMapper;
	private Logger log = Logger.getLogger(ValidCodeServiceImpl.class);
	@Override
	public boolean add(ValidCode validCode) {
		boolean result = false;
		if(validCode==null){
			log.debug("add:emailValidStr is invalid");
			return result;
		}
		try {
			//生成新验证码之后将以前的验证码都失效-暂定删除方式
			objMapper.deleteValidCodeByContactWay(validCode.getContactWay());
			validCode.setState("0");//设置验证码默认状态为0-有效
			int activeLines = objMapper.insertSelective(validCode);
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
	public ValidCode load(String contactWay) {
		ValidCode obj = null;
		if(TextUtil.isEmpty(contactWay)){
			log.debug("contactWay is an invalid parameter");
			return obj;
		}
		try {
			obj = objMapper.selectValidCode(contactWay);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return obj;
		}
		return obj;
	}
public static void main(String[] args) {
	System.out.println(new Date().getTime());
}

@Override
public boolean update(ValidCode validCode) {
	boolean result = false;
	if(validCode==null){
		log.debug("update:validCode is an invalid parameter");
		return result;
	}
	try {
		int activeLines = objMapper.updateByPrimaryKeySelective(validCode);
		if(activeLines>0){
			result = true;
		}
	} catch (Exception e) {
		e.printStackTrace();
		log.error("There are errors in code");
		return result;
	}
	return result;
}
}
