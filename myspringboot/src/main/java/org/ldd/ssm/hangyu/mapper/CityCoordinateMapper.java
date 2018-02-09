package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.CityCoordinate;

public interface CityCoordinateMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CityCoordinate record);

    int insertSelective(CityCoordinate record);

    CityCoordinate selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CityCoordinate record);

    int updateByPrimaryKey(CityCoordinate record);
    
    CityCoordinate selectByCityIcao(@Param("icao") String icao);
    
    CityCoordinate selectByAirIcao(@Param("icao") String icao);
    
    List<CityCoordinate> selectAllCityList();
}