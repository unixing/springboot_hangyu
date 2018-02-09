package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.RewardPolicy;

public interface RewardPolicyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RewardPolicy record);

    int insertSelective(RewardPolicy record);

    RewardPolicy selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RewardPolicy record);

    int updateByPrimaryKey(RewardPolicy record);
    
    List<RewardPolicy> selectRecordsByAirportId(@Param("airportId") Long airportId);
}