package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.AirClassCharPgs;

public interface AirClassCharPgsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirClassCharPgs record);

    int insertSelective(AirClassCharPgs record);

    AirClassCharPgs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirClassCharPgs record);

    int updateByPrimaryKey(AirClassCharPgs record);
}