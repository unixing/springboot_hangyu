package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.query.CabinData;
import org.ldd.ssm.crm.query.FlightPriceTrend;
import org.ldd.ssm.crm.query.TicketMonitorResult;

public interface GuestrateFlpjMapper {
	
	List<FlightPriceTrend>  findTicketTrends(GuestrateFlpj dto);
	
	List<String> findLegs(GuestrateFlpj dto);
	
	List<CabinData>  findCabinYesterDay(GuestrateFlpj dto);
	
	List<CabinData> findCabin(GuestrateFlpj dto);
	
	//航班管理该航班趋势
	/*List<FlightPriceTrend> findFlightManagePrice(GuestrateFlpj dto);*/
	  
}