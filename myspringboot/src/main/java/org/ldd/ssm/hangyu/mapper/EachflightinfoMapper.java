package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.EachflightDemo;
import org.ldd.ssm.hangyu.domain.Eachflightinfo;

public interface EachflightinfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Eachflightinfo record);

    int insertSelective(Eachflightinfo record);

    Eachflightinfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Eachflightinfo record);

    int updateByPrimaryKey(Eachflightinfo record);

	List<Eachflightinfo> selectAirportTimeInfo(@Param("itia") String itia, @Param("getTime") String getTime, @Param("orderField") String orderField, @Param("orderType") Integer orderType);

	List<String> getTimeList();
	
	List<EachflightDemo> selectAirportTimeDistribution(@Param("itia") String itia, @Param("getTime") String getTime);
}