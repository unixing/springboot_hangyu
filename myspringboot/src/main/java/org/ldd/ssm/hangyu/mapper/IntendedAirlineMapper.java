package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.IntendedAirline;

public interface IntendedAirlineMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IntendedAirline record);

    int insertSelective(IntendedAirline record);

    IntendedAirline selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IntendedAirline record);

    int updateByPrimaryKey(IntendedAirline record);
    
    int deleteByDemandId(@Param("demandId") Long demandId);
    
    List<IntendedAirline> selectByDemandId(@Param("demandId") Long demandId);
    
    int selectCountByDemandId(@Param("demandId") Long demandId);
}