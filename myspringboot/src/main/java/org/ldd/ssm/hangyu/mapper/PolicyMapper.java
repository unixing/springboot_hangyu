package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Policy;

public interface PolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Policy record);

    int insertSelective(Policy record);

    Policy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Policy record);

    int updateByPrimaryKey(Policy record);
    
    List<Policy> selectPolicyByCityId(@Param("cityId") Long cityId);
}