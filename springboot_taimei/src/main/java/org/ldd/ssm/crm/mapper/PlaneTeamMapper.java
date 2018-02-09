package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.PlaneTeam;

public interface PlaneTeamMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PlaneTeam record);

    int insertSelective(PlaneTeam record);

    PlaneTeam selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PlaneTeam record);

    int updateByPrimaryKey(PlaneTeam record);
    
    List<PlaneTeam> selectByName(@Param("name") String name);
}