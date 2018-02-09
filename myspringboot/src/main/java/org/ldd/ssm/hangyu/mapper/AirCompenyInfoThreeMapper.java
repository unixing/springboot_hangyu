package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;

public interface AirCompenyInfoThreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirCompenyInfoThree record);

    int insertSelective(AirCompenyInfoThree record);

    AirCompenyInfoThree selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirCompenyInfoThree record);

    int updateByPrimaryKey(AirCompenyInfoThree record);
    
    List<AirCompenyInfoThree> selectAll();
    
    String selectCompenyIcaoByIata(@Param("iata") String iata);
    
    AirCompenyInfoThree selectAircompenyByIcao(@Param("icao") String icao);
    
    AirCompenyInfoThree selectAirCompenyByIata(@Param("iata") String iata);
    
    List<AirCompenyInfoThree> selectAirCompenysByIcao(@Param("icao") String icao);
    
    List<AirCompenyInfoThree> selectAirCompenysForDemand(@Param("ids") String ids);
}