package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.MapperData;

/**
 * 数据拦截，需要查询的黑名单的mapper
 * @Title:DituMapper 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-20 上午9:54:34
 */
public interface DituMapper {
	
	List<MapperData> getMapperDataList();
	
}
