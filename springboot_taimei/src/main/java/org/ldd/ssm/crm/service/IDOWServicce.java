package org.ldd.ssm.crm.service;

import java.util.List;

import org.ldd.ssm.crm.domain.AcquisitionTime;
import org.ldd.ssm.crm.domain.DOW;
import org.ldd.ssm.crm.domain.MonthData;
import org.ldd.ssm.crm.domain.Z_Airdata;
import org.ldd.ssm.crm.query.DOWObject;
import org.ldd.ssm.crm.query.DOWQuery;
import org.ldd.ssm.crm.query.MonthObject;

public interface IDOWServicce {

	List<Z_Airdata> getCompany();

	List<AcquisitionTime> getDOW_Date();

	DOWObject<DOW> getMethod(DOWQuery dta_Sce);

	MonthObject<MonthData> getMonthData(DOWQuery dta_Sce);

	List<DOW> getView(DOWQuery dowData2);
	
	List<DOW> getViewSum(DOWQuery dowData2);
	
	List<DOW> getView2(DOWQuery dowData2);

	List<DOW> getViewSumPer(DOWQuery dowData2);

}
