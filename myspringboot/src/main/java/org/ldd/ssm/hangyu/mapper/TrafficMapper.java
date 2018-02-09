package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Traffic;

public interface TrafficMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Traffic record);

    int insertSelective(Traffic record);

    Traffic selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Traffic record);

    int updateByPrimaryKey(Traffic record);
    
    List<Traffic> selectyTrafficsByCityId(@Param("cityId") Long cityId);
}