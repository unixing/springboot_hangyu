package org.ldd.ssm.crm.mapper;

import org.ldd.ssm.crm.domain.Fczreptile;
import org.ldd.ssm.crm.query.HomePageQuery;

public interface FczreptileMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Fczreptile record);

    int insertSelective(Fczreptile record);

    Fczreptile selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Fczreptile record);

    int updateByPrimaryKey(Fczreptile record);
    
    String getAirportIthad(HomePageQuery homePageQuery);
}