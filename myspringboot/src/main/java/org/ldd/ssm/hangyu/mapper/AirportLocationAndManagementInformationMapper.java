package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirportLocationAndManagementInformation;

public interface AirportLocationAndManagementInformationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirportLocationAndManagementInformation record);

    int insertSelective(AirportLocationAndManagementInformation record);

    AirportLocationAndManagementInformation selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirportLocationAndManagementInformation record);

    int updateByPrimaryKey(AirportLocationAndManagementInformation record);
    
    List<AirportLocationAndManagementInformation> selectAirportLocationByIata(@Param("iata") String iata);
}