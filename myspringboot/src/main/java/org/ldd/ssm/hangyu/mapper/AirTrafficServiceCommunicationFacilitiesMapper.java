package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirTrafficServiceCommunicationFacilities;

public interface AirTrafficServiceCommunicationFacilitiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirTrafficServiceCommunicationFacilities record);

    int insertSelective(AirTrafficServiceCommunicationFacilities record);

    AirTrafficServiceCommunicationFacilities selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirTrafficServiceCommunicationFacilities record);

    int updateByPrimaryKey(AirTrafficServiceCommunicationFacilities record);
    
    List<AirTrafficServiceCommunicationFacilities> selectAirTrafficServiceCommunicationFacilitiesByIata(@Param("iata") String iata);
}