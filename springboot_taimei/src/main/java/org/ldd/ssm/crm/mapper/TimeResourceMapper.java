package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.TimeResource;

public interface TimeResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TimeResource record);

    int insertSelective(TimeResource record);

    TimeResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TimeResource record);

    int updateByPrimaryKey(TimeResource record);
    
    List<TimeResource> selectByName(@Param("name") String name);
}