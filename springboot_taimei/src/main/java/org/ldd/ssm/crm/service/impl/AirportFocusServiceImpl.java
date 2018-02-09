package org.ldd.ssm.crm.service.impl;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.domain.AirportFocus;
import org.ldd.ssm.crm.mapper.AirportFocusMapper;
import org.ldd.ssm.crm.service.AirportFocusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AirportFocusServiceImpl implements AirportFocusService {
	@Autowired
	private AirportFocusMapper objMapper;
	Logger log = Logger.getLogger(AirportFocusServiceImpl.class);
	public void setObjMapper(AirportFocusMapper objMapper) {
		this.objMapper = objMapper;
	}

	@Override
	public boolean add(AirportFocus airportFocus) {
		boolean result = false;
		int activeLines = 0;
		if(airportFocus==null){
			log.debug("add:airportFocus is null");
			return result;
		}
		try {
			activeLines = objMapper.insertSelective(airportFocus);
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
	public boolean update(AirportFocus airportFocus) {
		boolean result = false;
		int activeLines = 0;
		if(airportFocus==null){
			log.debug("update:airportFocus is null");
			return result;
		}
		try {
			activeLines = objMapper.updateByPrimaryKeySelective(airportFocus);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public boolean delete(Long id) {
		int activeLines = 0;
		boolean result = false;
		if(id==null||id<=0l){
			log.debug("delete:id is invalid");
			return result;
		}
		try {
			activeLines = objMapper.deleteByPrimaryKey(id);
			if(activeLines>0){
				result = true;
			}
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			return result;
		}
		return result;
	}

	@Override
	public AirportFocus select(Long id) {
		AirportFocus airportFocus = null;
		if(id==null||id<=0l){
			return airportFocus;
		}
		try {
			airportFocus = objMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.debug(e.getMessage(),e);
			return airportFocus;
		}
		return airportFocus;
	}

}
