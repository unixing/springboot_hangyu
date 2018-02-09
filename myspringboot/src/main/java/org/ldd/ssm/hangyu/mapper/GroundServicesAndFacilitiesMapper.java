package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.GroundServicesAndFacilities;

public interface GroundServicesAndFacilitiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GroundServicesAndFacilities record);

    int insertSelective(GroundServicesAndFacilities record);

    GroundServicesAndFacilities selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GroundServicesAndFacilities record);

    int updateByPrimaryKey(GroundServicesAndFacilities record);
    
    List<GroundServicesAndFacilities> selectGroundServicesAndFacilitiesByIata(@Param("iata") String iata);
}