package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.PhysicalCharacteristicsOfRunwayRek;

public interface PhysicalCharacteristicsOfRunwayRekMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PhysicalCharacteristicsOfRunwayRek record);

    int insertSelective(PhysicalCharacteristicsOfRunwayRek record);

    PhysicalCharacteristicsOfRunwayRek selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PhysicalCharacteristicsOfRunwayRek record);

    int updateByPrimaryKey(PhysicalCharacteristicsOfRunwayRek record);
    
    List<PhysicalCharacteristicsOfRunwayRek> selectPhysicalCharacteristicsOfRunwayRekByIata(@Param("iata") String iata);
}