package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.HelicopterLandingArea;

public interface HelicopterLandingAreaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HelicopterLandingArea record);

    int insertSelective(HelicopterLandingArea record);

    HelicopterLandingArea selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HelicopterLandingArea record);

    int updateByPrimaryKey(HelicopterLandingArea record);
    
    List<HelicopterLandingArea> selectHelicopterLandingAreaByIata(@Param("iata") String iata);
}