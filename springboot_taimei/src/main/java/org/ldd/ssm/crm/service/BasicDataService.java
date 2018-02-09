package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.domain.FlyDetalisInfo;
import org.ldd.ssm.crm.query.DailyParametersQuery;
import org.ldd.ssm.crm.query.FlyBasicDataQuery;
/**
 * 基础数据接口
 * @Title:BasicDataService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-20 下午4:35:04
 */
public interface BasicDataService {
	/**
	 * 得到小数位数设置
	 * @Title: get 
	 * @Description:  
	 * @param @return    
	 * @return Digitt     
	 * @throws
	 */
	public Digitt get();
	/**
	 * 更新小数位数设置
	 * @Title: update 
	 * @Description:  
	 * @param @param digitt
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String update(Digitt digitt);
	/**
	 * 得到飞机信息分页列表
	 * @Title: getFlyDetalisInfoPageList 
	 * @Description:  
	 * @param @param sQuery
	 * @param @return    
	 * @return List<FlyDetalisInfo>     
	 * @throws
	 */
	public List<FlyDetalisInfo> getFlyDetalisInfoPageList(FlyBasicDataQuery sQuery);
	/**
	 * 增加飞机信息
	 * @Title: saveFlyDetalisInfo 
	 * @Description:  
	 * @param @param flyDetalisInfo
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String saveFlyDetalisInfo(FlyDetalisInfo flyDetalisInfo);
	/**
	 * 修改飞机信息
	 * @Title: updateFlyDetalisInfo 
	 * @Description:  
	 * @param @param flyDetalisInfo
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String updateFlyDetalisInfo(FlyDetalisInfo flyDetalisInfo);
	/**
	 * 得到所有的参数列表
	 * @Title: getDailyParametersList 
	 * @Description:  
	 * @param @return    
	 * @return List<DailyParameters>     
	 * @throws
	 */
	public List<DailyParameters> getDailyParametersList(DailyParametersQuery dailyParametersQuery);
	/**
	 * 修改报表参数
	 * @Title: updateDailyParameters 
	 * @Description:  
	 * @param @param dailyParameters
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String updateDailyParameters(DailyParameters dailyParameters);
	/**
	 * 新增报表参数
	 * @Title: saveDailyParameters 
	 * @Description:  
	 * @param @param dailyParameters
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String saveDailyParameters(DailyParameters dailyParameters);
	/**
	 * 删除报表参数
	 * @Title: deleteDailyParameters 
	 * @Description:  
	 * @param @param ids
	 * @param @return    
	 * @return String     
	 * @throws
	 */
	public String deleteDailyParameters(String ids);
}
