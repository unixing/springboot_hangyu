package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Population;

public interface PopulationMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Population record);

    int insertSelective(Population record);

    Population selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Population record);

    int updateByPrimaryKey(Population record);
    
    List<Population> selectPopulationsByCityId(@Param("cityId") Long cityId);
}