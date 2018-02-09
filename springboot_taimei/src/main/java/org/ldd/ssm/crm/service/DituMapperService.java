package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.MapperData;
/**
 * 获取黑名单接口
 * @Title:DituMapperService 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-20 上午10:21:56
 */
public interface DituMapperService {
	/**
	 * 获得黑名单mapper
	 * @Title: getDataMapperList 
	 * @Description:  
	 * @param @return    
	 * @return List<MapperData>     
	 * @throws
	 */
	List<MapperData> getDataMapperList();
}
