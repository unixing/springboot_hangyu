package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.PhysicalCharacteristicsOfRunway;

public interface PhysicalCharacteristicsOfRunwayMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PhysicalCharacteristicsOfRunway record);

    int insertSelective(PhysicalCharacteristicsOfRunway record);

    PhysicalCharacteristicsOfRunway selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PhysicalCharacteristicsOfRunway record);

    int updateByPrimaryKey(PhysicalCharacteristicsOfRunway record);
    
    List<PhysicalCharacteristicsOfRunway> selectPhysicalCharacteristicsOfRunwaysByIata(@Param("iata") String iata);
}