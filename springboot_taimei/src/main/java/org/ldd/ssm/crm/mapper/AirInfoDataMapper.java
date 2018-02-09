package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AirInfoData;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.query.SortQuery;
/**
 * 机场信息查询MAPper
 * @Title:AirInfoDataMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-12-13 上午11:08:04
 */
public interface AirInfoDataMapper {
	/**
	 * 得到所有的机场信息
	 * @Title: getAirInfoDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<AirInfoData>     
	 * @throws
	 */
	List<AirInfoData> getAirInfoDataList();
	/**
	 * 得到所有的机场信息新的
	 * @Title: getAirInfoDataList 
	 * @Description:  
	 * @param @return    
	 * @return List<AirInfoData>     
	 * @throws
	 */
	List<AirInfoData> getAirInfoDataListNew();
	/**
	 * 获得国外机场信息
	 * @Title: getAirInfoDataListByOtherContory 
	 * @Description:  
	 * @param @return    
	 * @return List<AirInfoData>     
	 * @throws
	 */
	List<AirInfoData> getAirInfoDataListByOtherContory();
}
