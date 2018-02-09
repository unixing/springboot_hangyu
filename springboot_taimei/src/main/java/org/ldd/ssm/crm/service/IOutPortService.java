package org.ldd.ssm.crm.service;

import java.util.List;
import java.util.Map;

import org.ldd.ssm.crm.domain.ClassRanking;
import org.ldd.ssm.crm.domain.EvenPort;
import org.ldd.ssm.crm.domain.OutPort;
import org.ldd.ssm.crm.query.AirportOperatingHistoryQuery;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;

public interface IOutPortService {

	DOWObject<OutPort> getMethod(DOWQuery dta_Sce);

	List<OutPort> getOutPort(DOWQuery dowData);

	DOWObject<EvenPort> getEvenPort(DOWQuery dta_Sce);

	List<ClassRanking> getClassRanking(DOWQuery dowData);

	List<ClassRanking> getSet_Ktr_Ine(DOWQuery dowData);

	List<ClassRanking> getGuestamount(DOWQuery dowData);

	List<ClassRanking> getAmountRanking(DOWQuery dowData);

	List<ClassRanking> getCpy_ClassRanking(DOWQuery dowData);

	List<ClassRanking> getCpy_AmountRanking(DOWQuery dowData);
	
	Map<String,Map<String,Map<String,String>>> airportHistroy(DOWQuery dta_Sce);
	/**
	 * 得到机场条件下的所有航班的每班统计信息
	 * @Title: getFltNbrInfoData 
	 * @Description:  
	 * @param @param query
	 * @param @return    
	 * @return Map<String,Map<String,Map<String,String>>>     
	 * @throws
	 */
	Map<String, List<Map<String, Map<String, String>>>>getFltNbrInfoData(AirportOperatingHistoryQuery query);

	List<String> getYears(String itia);
}
