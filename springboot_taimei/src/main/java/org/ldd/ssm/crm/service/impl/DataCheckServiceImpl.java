package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.mapper.DataCheckMapper;
import org.ldd.ssm.crm.mapper.OutPortMapper;
import org.ldd.ssm.crm.query.DataCheckObject;
import org.ldd.ssm.crm.query.DateCheckQuery;
import org.ldd.ssm.crm.service.IDataCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 日报表的服务类
 */
@Service
public class DataCheckServiceImpl implements IDataCheckService {
	@Autowired
	private DataCheckMapper dataCheckMapper;
	@Autowired
	private OutPortMapper portMapper;
	public DataCheckObject<Z_Airdata> getDataCheck(DateCheckQuery query) {
		//将中文城市名转成三字码
		String airCodeByName = portMapper.getAirCodeByName(query.getDpt_AirPt_Cd());
		String airCodeByName2 = portMapper.getAirCodeByName(query.getArrv_Airpt_Cd());
		query.setDpt_AirPt_Cd(airCodeByName);
		query.setArrv_Airpt_Cd(airCodeByName2);
		//设置好了
		List<Z_Airdata> airdatas = dataCheckMapper.getDataCheck(query);
		Integer total = dataCheckMapper.getTotal(query);
		return new DataCheckObject<Z_Airdata>(airdatas, total);
	}
	
}
