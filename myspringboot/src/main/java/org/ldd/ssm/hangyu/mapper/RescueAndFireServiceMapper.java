package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.RescueAndFireService;

public interface RescueAndFireServiceMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RescueAndFireService record);

    int insertSelective(RescueAndFireService record);

    RescueAndFireService selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RescueAndFireService record);

    int updateByPrimaryKey(RescueAndFireService record);
    
    List<RescueAndFireService> selectRescueAndFireServiceByIata(@Param("iata") String iata);
}