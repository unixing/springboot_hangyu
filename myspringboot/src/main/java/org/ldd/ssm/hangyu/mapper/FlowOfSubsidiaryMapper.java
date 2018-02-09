package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.FlowOfSubsidiary;

public interface FlowOfSubsidiaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlowOfSubsidiary record);

    int insertSelective(FlowOfSubsidiary record);

    FlowOfSubsidiary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlowOfSubsidiary record);

    int updateByPrimaryKey(FlowOfSubsidiary record);
    
    List<FlowOfSubsidiary> selectFlowOfSubsidiaryForLastThreeYears(@Param("airportId") Long airportId);
}