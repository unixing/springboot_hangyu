package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirPortWeatherInfo;

public interface AirPortWeatherInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirPortWeatherInfo record);

    int insertSelective(AirPortWeatherInfo record);

    AirPortWeatherInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirPortWeatherInfo record);

    int updateByPrimaryKey(AirPortWeatherInfo record);
    
    List<AirPortWeatherInfo> selectAirPortWeatherInfosByIata(@Param("iata") String iata);
}