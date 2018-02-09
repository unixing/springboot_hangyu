package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.ApronTaxiwayAndCalibrationLocationData;

public interface ApronTaxiwayAndCalibrationLocationDataMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ApronTaxiwayAndCalibrationLocationData record);

    int insertSelective(ApronTaxiwayAndCalibrationLocationData record);

    ApronTaxiwayAndCalibrationLocationData selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApronTaxiwayAndCalibrationLocationData record);

    int updateByPrimaryKey(ApronTaxiwayAndCalibrationLocationData record);
    
    List<ApronTaxiwayAndCalibrationLocationData> selectApronTaxiwayAndCalibrationLocationByIata(@Param("iata") String iata);
}