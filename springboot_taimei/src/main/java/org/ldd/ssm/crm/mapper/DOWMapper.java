package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.SunAndCount;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWQuery;

public interface DOWMapper {

	List<Z_Airdata> getCompany();

	List<AcquisitionTime> getDOW_Date();

	List<DOW> getMethod(DOWQuery dta_Sce);

	List<Z_Airdata> getWeekDate(DOWQuery dta_Sce);

	Integer getTotal(DOWQuery dta_Sce);

	String getMonthData(DOWQuery dta_Sce);

	List<String> getTime(DOWQuery dta_Sce);

	String getMonthData2(DOWQuery dta_Sce);

	SunAndCount getSunAndCount(DOWQuery dta_Sce);

	List<Z_Airdata> getWeekDate2(DOWQuery dta_Sce);

	SunAndCount getSunAndCount2(DOWQuery dta_Sce);

	String getAirPort2(String str);


}
