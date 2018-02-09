package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Base;

public interface BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Base record);

    int insertSelective(Base record);

    Base selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Base record);

    int updateByPrimaryKey(Base record);
    
    List<Base> selectByName(@Param("name") String name);
}