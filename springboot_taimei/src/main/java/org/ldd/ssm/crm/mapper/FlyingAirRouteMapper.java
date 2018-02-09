package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.FlyingAirRoute;

public interface FlyingAirRouteMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FlyingAirRoute record);

    int insertSelective(FlyingAirRoute record);

    FlyingAirRoute selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FlyingAirRoute record);

    int updateByPrimaryKey(FlyingAirRoute record);
    
    List<FlyingAirRoute> selectByName(@Param("name") String name);
}