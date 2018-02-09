package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.FlightInfo;

public interface FlightInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlightInfo record);

    int insertSelective(FlightInfo record);

    FlightInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlightInfo record);

    int updateByPrimaryKey(FlightInfo record);
    
    List<FlightInfo> selectAll(@Param("itia") String itia, @Param("status") Integer status);
    
    int groundOrGoAround(@Param("id") Integer id, @Param("status") int status);
    
    List<String> getFltNum(@Param("itia") String itia, @Param("dpt_AirPt_Cd") String dpt_AirPt_Cd, @Param("arrv_Airpt_Cd") String arrv_Airpt_Cd, @Param("flt_Rte_Cd") String flt_Rte_Cd);
}