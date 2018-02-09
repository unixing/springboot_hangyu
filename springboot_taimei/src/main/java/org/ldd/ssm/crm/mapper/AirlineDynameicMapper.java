package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.query.HomePageQuery;
import org.ldd.ssm.crm.query.SortQuery;

public interface AirlineDynameicMapper {

	List<Yesterday_ZD> getGetAirline_dynameic_list_j(String getcompanyItia, String date);
	List<Yesterday_ZD> getGetAirline_dynameic_list_c(String getcompanyItia, String date);
	List<Yesterday_ZD> getGetAirline_dynameic_list_in(SortQuery query);
	List<Yesterday_ZD> getGetAirline_dynameic_list_out(SortQuery query);
	List<Yesterday_ZD> getGetAirline_dynameic_list_month(SortQuery query);
	/**
	 * 获得某天的所有航班的准点率
	 * @Title: getGetAirline_dynameic_list_day 
	 * @Description:  
	 * @param @param date
	 * @param @return    
	 * @return List<Yesterday_ZD>     
	 * @throws
	 */
	List<Yesterday_ZD> getGetAirline_dynameic_list_day(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("itia") String itia);
	void airline_dynameic_save(String index, String sp);
	
	String getAirportIthad(HomePageQuery query);
	
	String getNewestDate(@Param("city") String city, @Param("inout") String inout);
	
	List<String> getDateList(@Param("cityiata") String cityiata, @Param("year") String year, @Param("month") String month, @Param("inout") String inout);
	
	List<String> getMonthList(@Param("city") String city, @Param("year") String year);
}
