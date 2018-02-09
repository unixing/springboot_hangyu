package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.AirlineDynameicGraphics;
import org.ldd.ssm.crm.domain.Yesterday_ZD;
import org.ldd.ssm.crm.query.HomePageQuery;

public interface IAirlineDynameicService {

	Map<String,Object> getAirline_dynameic_list(String date);
	
	List<Yesterday_ZD> getAirline_dynameic_list_in(String date, String field, String sortType);
	
	List<Yesterday_ZD> getAirline_dynameic_list_out(String date, String field, String sortType);

	AirlineDynameicGraphics airline_dynameic_graphics(String date);

	void airline_dynameic_save(String index, String sp);
	
	String getAirportIthad(HomePageQuery query);
	
	List<String> getDateList(String city, String cityIata, String month, String inout);
	
	List<String> getMonthList(String year);
}
