package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.HomePageQuery;

public interface PortalDataMapper {
	
	String getNewDate(@Param("airport") String airport);
	
	List<Z_Airdata> getPortalDataList(HomePageQuery query);
}
