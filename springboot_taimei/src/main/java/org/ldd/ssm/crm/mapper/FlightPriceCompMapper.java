package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.FlightPriceComp;
import org.ldd.ssm.crm.domain.GuestrateFlpj;
import org.ldd.ssm.crm.domain.TKTIncome;
import org.ldd.ssm.crm.query.FlightPriceCompDto;
import org.ldd.ssm.crm.query.FlightSaleData;

public interface FlightPriceCompMapper {
	 List<FlightPriceComp> findFlightPriceComp(Long tktincomeId);
	 
	 int updatePrice(FlightPriceComp record);
	
	 int deleteByTktId(Long tktincomeId);
	 
	 int batchInsert(FlightSaleData dto);
	 
	 int batchDeleteByTktId(List<TKTIncome> list);
	 
	 //每天凌晨删除今天之前的数据
	 int deleteHistory(String currentDate);
	 
	 //获得当前价格对比出师表最新的日期和航段
	 List<FlightPriceCompDto> findFlightPriceMaxDate();
	 
	 int batchInsertNewest(FlightPriceCompDto dto);
	 
	 //监控的但是还没加初始值的航班航段
	 List<GuestrateFlpj> findEmptyWatch();
	 
}