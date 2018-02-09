package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.XcAirFareComItem;

public interface XcAirFareComItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(XcAirFareComItem record);

    int insertSelective(XcAirFareComItem record);

    XcAirFareComItem selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(XcAirFareComItem record);

    int updateByPrimaryKey(XcAirFareComItem record);
}