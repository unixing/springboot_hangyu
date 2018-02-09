package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.CityID;
import org.ldd.ssm.crm.domain.Traveler;
import org.ldd.ssm.crm.query.SourceDistriDataQuery;

public interface SourceDistriMapper {

	List<Traveler> getSourceDistriData(SourceDistriDataQuery dataQuery);

	List<CityID> getIdcardmappercity();
	
	List<CityID> getIdcardmapperprovince();

	CityID getIdcardmapperProvinceByF(String city);

	List<Traveler> getSourceDistriDataByF(SourceDistriDataQuery dataQuery);

	List<CityID> getIdcardmapperCityByF(String city);
	
	List<String> getFltNbrList(SourceDistriDataQuery dataQuery);
	
	String getCustomerNewDate(SourceDistriDataQuery dataQuery);
	List<String> getHavingDataFlyNbrList();
	//获得包含该机场的全部航线
	List<String> getAllFltNbrList(String iata);
}
