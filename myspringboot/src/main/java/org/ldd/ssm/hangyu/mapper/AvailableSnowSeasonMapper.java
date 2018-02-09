package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AvailableSnowSeason;

public interface AvailableSnowSeasonMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AvailableSnowSeason record);

    int insertSelective(AvailableSnowSeason record);

    AvailableSnowSeason selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AvailableSnowSeason record);

    int updateByPrimaryKey(AvailableSnowSeason record);
    
    List<AvailableSnowSeason> selectAvailableSnowSeasonByIata(@Param("iata") String iata);
}