package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.RunwayTheDetail;

public interface RunwayTheDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RunwayTheDetail record);

    int insertSelective(RunwayTheDetail record);

    RunwayTheDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RunwayTheDetail record);

    int updateByPrimaryKey(RunwayTheDetail record);
    
    List<RunwayTheDetail> selectRunwaysForAirport(@Param("airportId") Long airportId);
}