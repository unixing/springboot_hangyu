package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.FlightRoute;

public interface FlightRouteMapper {
    int insert(FlightRoute record);

    int insertSelective(FlightRoute record);
    
    int deleteByEmployeeId(@Param("itia") String itia, @Param("employeeId") Long employeeId);
    
    List<FlightRoute> selectByEmployee(@Param("employeeId") Long employeeId, @Param("itia") String itia);
    
    int selectFlightNbrCount(@Param("itia") String itia, @Param("employeeId") Long employeeId);
}