package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Airdiscount;
import org.ldd.ssm.crm.domain.AirportData;
import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.AirportOperatingHistoryQuery;
import org.ldd.ssm.crm.query.DOWQuery;

public interface OutPortMapper {

	List<String> getMonth(String lcl_Dpt_Day);

	OutPort getMonthDate(DOWQuery dta_Sce);

	int getAirDistacne(String dta_Sce);

	List<OutPort> getOutPort(DOWQuery dowData);

	EvenPort getEvenPort(DOWQuery dta_Sce);

	List<ClassRanking> getClassRanking(DOWQuery dowData);

	List<ClassRanking> getSet_Ktr_Ine(DOWQuery dowData);
	/**
	 * 座公里前十查询
	 * @Title: getSet_Ktr_IneNew 
	 * @Description:  
	 * @param @param dowData
	 * @param @return    
	 * @return List<ClassRanking>     
	 * @throws
	 */
	List<Z_Airdata> getSet_Ktr_IneNew(DOWQuery dowData);

	List<ClassRanking> getGuestamount(DOWQuery dowData);

	String getAirCodeByName(String dpt_AirPt_Cd);

	String getNameByCode(String dpt_AirPt_Cd);
	
	List<String> getAirCodesByCityName(String city);

	List<ClassRanking> getAmountRanking(DOWQuery dowData);
	
	List<AirportData> getAirPortData();
	
	List<AirportData> getAirPortDataNew();
	
	String getAirportCodeByAirportName(String airportName);
	
	String getairportNameByCode(String code);

	List<ClassRanking> getCpy_ClassRanking(DOWQuery dta_Sce);

	List<ClassRanking> getCpy_AmountRanking(DOWQuery dta_Sce);
	/**
	 * 根据航司获得航司公布的折扣表数据
	 * @param company
	 * @return
	 */
	List<Airdiscount> getAirdiscountByCompany(String company);
	
	List<String> getYearList(@Param("itia") String itia);
	/**
	 * 获得条件下的数据详情
	 * @Title: getZairdatasList 
	 * @Description:  
	 * @param @param dowData
	 * @param @return    
	 * @return List<Z_Airdata>     
	 * @throws
	 */
	List<Z_Airdata> getZairdatasList(AirportOperatingHistoryQuery query);

}
