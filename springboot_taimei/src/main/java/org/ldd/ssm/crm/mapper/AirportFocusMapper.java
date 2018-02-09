package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.AirportFocus;

public interface AirportFocusMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirportFocus record);

    int insertSelective(AirportFocus record);

    AirportFocus selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirportFocus record);

    int updateByPrimaryKey(AirportFocus record);
}