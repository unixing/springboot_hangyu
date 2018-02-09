package org.ldd.ssm.crm.service.impl;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.domain.FlyDetalisInfo;
import org.ldd.ssm.crm.mapper.BasicDataMapper;
import org.ldd.ssm.crm.query.DailyParametersQuery;
import org.ldd.ssm.crm.query.FlyBasicDataQuery;
import org.ldd.ssm.crm.service.BasicDataService;
import org.ldd.ssm.crm.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础数据实现类
 * @Title:BasicDataServiceImpl 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-20 下午4:43:33
 */
@Service
public class BasicDataServiceImpl implements BasicDataService {
	@Autowired
	private BasicDataMapper basicDataMapper;

	public Digitt get() {
		return basicDataMapper.getDigitt();
	}
	public String update(Digitt digitt) {
		basicDataMapper.updateDigitt(digitt);
		return "更新成功";
	}
	public List<FlyDetalisInfo> getFlyDetalisInfoPageList(FlyBasicDataQuery sQuery) {
		return null;
	}
	public String saveFlyDetalisInfo(FlyDetalisInfo flyDetalisInfo) {
		return "保存成功";
	}
	public String updateFlyDetalisInfo(FlyDetalisInfo flyDetalisInfo) {
		return "修改成功";
	}
	public List<DailyParameters> getDailyParametersList(DailyParametersQuery dailyParametersQuery ) {
		if(dailyParametersQuery==null){
			dailyParametersQuery = new DailyParametersQuery();
		}
		dailyParametersQuery.setRoleId(Integer.parseInt(UserContext.getCompanyId()));
		return basicDataMapper.getDailyParametersList(dailyParametersQuery);
	}
	public String updateDailyParameters(DailyParameters dailyParameters) {
		basicDataMapper.updateDailyParameters(dailyParameters);
		return "更新成功";
	}
	public String saveDailyParameters(DailyParameters dailyParameters) {
		basicDataMapper.saveDailyParameters(dailyParameters);
		return "保存成功";
	}
	public String deleteDailyParameters(String ids) {
		String [] str = ids.split(",");
		for (String id : str) {
			basicDataMapper.deleteDailyParameters(id);
		}
		return "批量删除成功";
	}
	
}
