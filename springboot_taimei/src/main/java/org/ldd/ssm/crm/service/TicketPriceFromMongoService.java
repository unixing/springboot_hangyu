package org.ldd.ssm.crm.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.query.FlightPriceCompDto;
import org.ldd.ssm.crm.query.FlightSaleData;
import org.ldd.ssm.crm.query.LatestAirFare;


public interface TicketPriceFromMongoService {
	
	
	public List<LatestAirFare> findTicketTrends(GuestrateFlpj dto);
	
	
	public List<LatestAirFare> findFlightManagePrice(GuestrateFlpj dto);
	
	public List<LatestAirFare> findFlightManageHistory(GuestrateFlpj dto);
	
	
	public List<LatestAirFare> findFlightManagePriceByDate(FlightPriceCompDto dto);
	/**
	 * CTU-LHW-HU7305
	 * @param param
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public FlightSaleData getRealTimeFare(String param) throws UnknownHostException, IOException;
	
	/**
	 * 在仓位销售数据 为空的情况下 票价信息可能已经爬取到了 这时候通过爬到的票价 获得有票价的航段
	 * @param dto
	 * @return
	 */
	List<LatestAirFare> findPriceLegs(GuestrateFlpj dto);
	
}
