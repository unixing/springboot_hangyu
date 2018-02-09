package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.FlightManage;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.query.FlightManageInDto;

public interface FlightManageMapper {
    
    //管理航班集合
    List<TKTIncome> findFlightManage(FlightManage dto);
    
    /**
     * 管理航班航段 取消 直接删除
     * @param ids
     * @return
     */
    int deleteFlightManage(Long tktincomeId);
    
    int insert(FlightManage record);
    
    int update(FlightManage record);
    
    Long getCurrentTktId(FlightManage dto);
	
}