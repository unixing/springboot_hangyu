package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.LengthIntensity;

public interface LengthIntensityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LengthIntensity record);

    int insertSelective(LengthIntensity record);

    LengthIntensity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LengthIntensity record);

    int updateByPrimaryKey(LengthIntensity record);
    
    List<LengthIntensity> selectLengthIntensitysByIata(@Param("iata") String iata);
}