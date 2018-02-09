package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.SalesReport;
import org.ldd.ssm.crm.query.FormulaUtilQuery;
import org.ldd.ssm.crm.query.SalesReportQuery;


public interface FormulaUtilMapper {
	  List<SalesReport> getTotalTime(FormulaUtilQuery formulaUtilQuery);
	  DailyParameters getHourPrice(FormulaUtilQuery formulaUtilQuery);
	  List<DailyParameters> getHourPriceList(FormulaUtilQuery formulaUtilQuery);
	  String getMonthSubsidy(FormulaUtilQuery formulaUtilQuery, SalesReportQuery salesReportQuery);
	
}
