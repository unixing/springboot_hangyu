package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.WeatherInfo;

public interface WeatherInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WeatherInfo record);

    int insertSelective(WeatherInfo record);

    WeatherInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WeatherInfo record);

    int updateByPrimaryKey(WeatherInfo record);
    
    List<WeatherInfo> selectWeatherInfosByIata(@Param("iata") String iata);
}