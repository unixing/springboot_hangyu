package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Barrier;

public interface BarrierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Barrier record);

    int insertSelective(Barrier record);

    Barrier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Barrier record);

    int updateByPrimaryKey(Barrier record);
    
    List<Barrier> selectBarriersByIata(@Param("iata") String iata);
}