package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.GuilanceAndControlSystemAndIdentifyTheGroundActivities;

public interface GuilanceAndControlSystemAndIdentifyTheGroundActivitiesMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GuilanceAndControlSystemAndIdentifyTheGroundActivities record);

    int insertSelective(GuilanceAndControlSystemAndIdentifyTheGroundActivities record);

    GuilanceAndControlSystemAndIdentifyTheGroundActivities selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GuilanceAndControlSystemAndIdentifyTheGroundActivities record);

    int updateByPrimaryKey(GuilanceAndControlSystemAndIdentifyTheGroundActivities record);
    
    List<GuilanceAndControlSystemAndIdentifyTheGroundActivities> selectGuilanceAndControlSystemAndIdentifyTheGroundActivitiesByIata(@Param("iata") String iata);
}