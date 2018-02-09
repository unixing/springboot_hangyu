package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.CityinfoThree;

public interface CityinfoThreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CityinfoThree record);

    int insertSelective(CityinfoThree record);

    CityinfoThree selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CityinfoThree record);

    int updateByPrimaryKey(CityinfoThree record);
    
    List<CityinfoThree> selectCityDetailByName(@Param("cityName") String cityName);
}