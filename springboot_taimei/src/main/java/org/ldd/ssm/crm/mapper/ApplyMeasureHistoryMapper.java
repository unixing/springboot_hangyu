package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.ApplyMeasureHistory;
import org.ldd.ssm.crm.query.AirCompanyInfoQuery;
import org.ldd.ssm.crm.query.LegProfitFocastQuery;

public interface ApplyMeasureHistoryMapper {
	//申请测算
	int insertApplyMeasure(ApplyMeasureHistory record);
	//修改状态为删除
    int updateState(ApplyMeasureHistory record);
    //申请列表
	List<ApplyMeasureHistory> findApplyMeasureList(Long userId);

	LegProfitFocastQuery getApplyMeasure(Long id);
	//根据机型获得航司
	List<AirCompanyInfoQuery> getAllAirCompany(String airType);
	//根据航司获得机型
	List<String> getAllAirType(Long aircompanyId);

}