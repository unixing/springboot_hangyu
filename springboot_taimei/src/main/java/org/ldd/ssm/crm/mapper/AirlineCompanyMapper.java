package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.AirlineCompany;

public interface AirlineCompanyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AirlineCompany record);

    int insertSelective(AirlineCompany record);

    AirlineCompany selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AirlineCompany record);

    int updateByPrimaryKey(AirlineCompany record);
    
    List<String> getCurrentAirportAirlineList(@Param("itia") String itia);
}