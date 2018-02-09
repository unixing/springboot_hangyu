package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.MonthSalePlan;

public interface SalePlanDataMapper {
	int add(MonthSalePlan data);
	int update(MonthSalePlan data);
	MonthSalePlan load(@Param("seasonId") int seasonId, @Param("flt_Rte_Cd") String flt_Rte_Cd, @Param("flt_nbr") String flt_nbr, @Param("yb") Integer yb);
	MonthSalePlan selectByTimeId(@Param("timeId") int timeId, @Param("flt_nbr") String flt_nbr);
	int deleteSalePlan(@Param("timeId") int timeId, @Param("toFltNbr") String toFltNbr, @Param("backFltNbr") String backFltNbr);
	List<MonthSalePlan> selectAll();
}
