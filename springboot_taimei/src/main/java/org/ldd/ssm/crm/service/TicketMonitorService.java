package org.ldd.ssm.crm.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.ldd.ssm.crm.domain.FlightManage;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.domain.WatchFlight;
import org.ldd.ssm.crm.query.FlightManageInDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.SalesReportQuery;
import org.ldd.ssm.crm.query.WatchFlightDto;


/**
 * 
 * @author wxm
 *
 */
public interface TicketMonitorService {
	//当日
	 List<FlightSaleData> findTicketMonitor(GuestrateFlpj dto) throws UnknownHostException, IOException;
	 
	 //历史
	 List<FlightSaleData> findTicketMonitorHistory(GuestrateFlpj dto);
	 
	 boolean flightManage(FlightManageInDto dto);
	
	 List<String> getflightLeg(String fltNbr);
	 
	 //根据航班号 出发地到达地用户id 查询到航班管理航班
	 List<TKTIncome> findFlightManage(WatchFlight dto);
	  
	 boolean upDownRemind(FlightManageInDto dto);
	 
	 List<WatchFlightDto> findWatchFlight(String currentTime);
	 
	 //根据传入的航班号和航线获得正确的航段
	public List<String> findLegsByFltNbr(SalesReportQuery salesReportQuery);
}
