package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.OtherLightsBackupPowerSupply;

public interface OtherLightsBackupPowerSupplyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OtherLightsBackupPowerSupply record);

    int insertSelective(OtherLightsBackupPowerSupply record);

    OtherLightsBackupPowerSupply selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OtherLightsBackupPowerSupply record);

    int updateByPrimaryKey(OtherLightsBackupPowerSupply record);
    
    List<OtherLightsBackupPowerSupply> selectOtherLightsBackupPowerSupplyByIata(@Param("iata") String iata);
}