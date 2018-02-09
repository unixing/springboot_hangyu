package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirCompenyInfoThree;
import org.ldd.ssm.hangyu.domain.AirportInfoNewThree;
import org.ldd.ssm.hangyu.domain.RouteNetwork;

public interface AirportInfoNewThreeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirportInfoNewThree record);

    int insertSelective(AirportInfoNewThree record);

    AirportInfoNewThree selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirportInfoNewThree record);

    int updateByPrimaryKey(AirportInfoNewThree record);
    
    List<AirportInfoNewThree> selectAllData();
    /**
     * 获取机场航线网络
     * @param iata
     * @return
     */
    List<RouteNetwork> selectAirportRouteNetwork(@Param("iata") String iata);
    
    List<RouteNetwork> selectAirlineAirportList(@Param("iata") String iata);
    
    RouteNetwork selectAirlineAirport(@Param("dptIata") String dptIata, @Param("arrvIata") String arrvIata);
    
    AirportInfoNewThree selectByIcao(String icao);
    
    AirportInfoNewThree selectById(Long id);
    
    String selectAirportNameByIcao(String icao);
    
    List<AirportInfoNewThree> selectAirportRouteNetwork(@Param("type") int type, @Param("iata") String iata);
    
    List<AirportInfoNewThree> selectAirportsForDemand(@Param("ids") String ids);
}