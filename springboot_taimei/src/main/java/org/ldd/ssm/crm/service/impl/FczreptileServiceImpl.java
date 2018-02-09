package org.ldd.ssm.crm.service.impl;

import org.apache.log4j.Logger;
import org.ldd.ssm.crm.mapper.FczreptileMapper;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.service.FczreptileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class FczreptileServiceImpl implements FczreptileService {
	@Autowired
	private FczreptileMapper objMapper;
	Logger log = Logger.getLogger(FczreptileServiceImpl.class);
	
	public void setObjMapper(FczreptileMapper objMapper) {
		this.objMapper = objMapper;
	}

	@Override
	public String getAirportIthad(HomePageQuery homePageQuery) {
		String  ithad = null;
		if(homePageQuery==null){
			log.debug("getAirportIthad:homePageQuery is invalide");
			return ithad;
		}
		try {
			ithad = objMapper.getAirportIthad(homePageQuery);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ithad;
		}
		return ithad;
	}

}
