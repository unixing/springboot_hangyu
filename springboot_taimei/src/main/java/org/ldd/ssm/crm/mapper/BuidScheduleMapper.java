package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.BuidSchedule;

public interface BuidScheduleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BuidSchedule record);

    int insertSelective(BuidSchedule record);

    BuidSchedule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BuidSchedule record);

    int updateByPrimaryKey(BuidSchedule record);
    
    List<BuidSchedule> selectByName(@Param("name") String name);
}