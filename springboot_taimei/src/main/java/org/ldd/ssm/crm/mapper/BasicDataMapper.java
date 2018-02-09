package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.DailyParameters;
import org.ldd.ssm.crm.domain.Digitt;
import org.ldd.ssm.crm.query.DailyParametersQuery;

public interface BasicDataMapper {

	public Digitt getDigitt();

	public void updateDigitt(Digitt digitt);
	public List<DailyParameters> getDailyParametersList(DailyParametersQuery dailyParametersQuery);
	public void updateDailyParameters(DailyParameters dailyParameters);
	public void saveDailyParameters(DailyParameters dailyParameters) ;
	public void deleteDailyParameters(@Param("id") String id) ;
	public Double selectHourCost(@Param("monthNbr") String monthNbr, @Param("siteCount") Integer siteCount);
}
