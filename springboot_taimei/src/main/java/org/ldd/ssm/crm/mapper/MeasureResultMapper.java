package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.MeasureResult;

public interface MeasureResultMapper {
	
	//我的测算结果时间 根据申请id查询
	String getMyMeasureTime(Long applyMeasureId);
	//我的测算结果 根据申请id查询
	List<MeasureResult> findMeasureResult(Long applyMeasureId);
	//最新测算时间和申请id
	MeasureResult getNewestMeasureResult(MeasureResult dto);
	
    int deleteByPrimaryKey(Long id);

    int insert(MeasureResult record);

    int insertSelective(MeasureResult record);

    MeasureResult selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MeasureResult record);

    int updateByPrimaryKey(MeasureResult record);
}