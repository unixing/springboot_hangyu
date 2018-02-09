package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.AirportInfo;

public interface AirportInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AirportInfo record);

    int insertSelective(AirportInfo record);

    AirportInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AirportInfo record);

    int updateByPrimaryKey(AirportInfo record);
    
    List<AirportInfo> selectByName(@Param("name") String name);
}