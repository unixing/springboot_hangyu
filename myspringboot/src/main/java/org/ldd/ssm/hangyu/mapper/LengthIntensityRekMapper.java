package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.LengthIntensityRek;

public interface LengthIntensityRekMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LengthIntensityRek record);

    int insertSelective(LengthIntensityRek record);

    LengthIntensityRek selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LengthIntensityRek record);

    int updateByPrimaryKey(LengthIntensityRek record);
    
    List<LengthIntensityRek> selectLengthIntensityRekByIata(@Param("iata") String iata);
}