package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.EconomicYear;

public interface EconomicYearMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EconomicYear record);

    int insertSelective(EconomicYear record);

    EconomicYear selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EconomicYear record);

    int updateByPrimaryKey(EconomicYear record);
    
    List<EconomicYear> selectEconomicYearByCityId(@Param("cityId") Long cityId);
}