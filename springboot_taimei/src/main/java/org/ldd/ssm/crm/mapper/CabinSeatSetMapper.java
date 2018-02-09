package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.CabinSeatSet;
import org.ldd.ssm.crm.query.MonthSalesPlanQuery;

public interface CabinSeatSetMapper {
	int add(CabinSeatSet set);
	
	int update(CabinSeatSet set);
	
	CabinSeatSet load(MonthSalesPlanQuery query);
	
	List<CabinSeatSet> selectAll();
	
	List<CabinSeatSet> list(@Param("seasonId") Integer seasonId, @Param("toFltNbr") String toFltNbr, @Param("backFltNbr") String backFltNbr);
	
	int deleteCabinSeatSet(@Param("seasonId") Integer seasonId, @Param("toFltNbr") String toFltNbr, @Param("backFltNbr") String backFltNbr);
}
