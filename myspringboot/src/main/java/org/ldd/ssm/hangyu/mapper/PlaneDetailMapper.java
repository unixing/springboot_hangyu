package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.PlaneDetail;

public interface PlaneDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PlaneDetail record);

    int insertSelective(PlaneDetail record);

    PlaneDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PlaneDetail record);

    int updateByPrimaryKey(PlaneDetail record);
    
    List<PlaneDetail> selectPlaneDetailsByAircompenyId(@Param("aircompenyId") Long aircompenyId);
}