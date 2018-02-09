package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.ProvideWeatherInformation;

public interface ProvideWeatherInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProvideWeatherInformation record);

    int insertSelective(ProvideWeatherInformation record);

    ProvideWeatherInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProvideWeatherInformation record);

    int updateByPrimaryKey(ProvideWeatherInformation record);
    
    List<ProvideWeatherInformation> selectProvideWeatherInformationByIata(@Param("iata") String iata);
}