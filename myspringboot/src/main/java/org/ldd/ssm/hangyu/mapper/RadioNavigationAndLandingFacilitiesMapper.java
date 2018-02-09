package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.RadioNavigationAndLandingFacilities;

public interface RadioNavigationAndLandingFacilitiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RadioNavigationAndLandingFacilities record);

    int insertSelective(RadioNavigationAndLandingFacilities record);

    RadioNavigationAndLandingFacilities selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RadioNavigationAndLandingFacilities record);

    int updateByPrimaryKey(RadioNavigationAndLandingFacilities record);
    
    List<RadioNavigationAndLandingFacilities> selectRadioNavigationAndLandingFacilitiesByIata(@Param("iata") String iata);
}