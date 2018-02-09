package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.AirTrafficServiceAirspace;

public interface AirTrafficServiceAirspaceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirTrafficServiceAirspace record);

    int insertSelective(AirTrafficServiceAirspace record);

    AirTrafficServiceAirspace selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirTrafficServiceAirspace record);

    int updateByPrimaryKey(AirTrafficServiceAirspace record);
    
    List<AirTrafficServiceAirspace> selectAirTrafficServiceAirspacesByIata(@Param("iata") String iata);
}