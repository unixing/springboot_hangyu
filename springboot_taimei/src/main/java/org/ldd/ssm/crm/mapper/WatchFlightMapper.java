package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runners.Parameterized.Parameters;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.query.FlightManageInDto;
import org.ldd.ssm.crm.query.WatchFlightDto;

public interface WatchFlightMapper {
	  List<WatchFlightDto> findWatchFlight(@Param("currentTime") String currentTime);
	  
	  int deleteByTktId(Long tktincomeId);
	  
	  int batchDeleteByTktId(List<TKTIncome> list);
	  
	  int insert(WatchFlightDto dto);
	 
	  int batchUpdate(FlightManageInDto dto);

	  //修改管理航班后联动修改监控 
	  int updateByTKTIncome(TKTIncome dto);
	  //获得涨跌提醒的初始值
	  WatchFlight getWatchFlight(WatchFlight dto);
	  
	  int batchInsert(FlightManageInDto dto);
	   
	 int getWatchFlightCount(WatchFlight dto);	
}
