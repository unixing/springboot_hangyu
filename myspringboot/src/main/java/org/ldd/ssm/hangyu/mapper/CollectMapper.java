package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.Collect;
import org.ldd.ssm.hangyu.domain.Demand;
import org.ldd.ssm.hangyu.domain.DemandSearch;
import org.ldd.ssm.hangyu.query.MyCollect;

public interface CollectMapper {
    Collect selectByPrimaryKey(Long id);
	
    int deleteByPrimaryKey(Long id);
    
    int insertSelective(Collect record);
    
    int insert(Collect record);
 
    int updateByPrimaryKeySelective(Collect record);
    
    int updateByPrimaryKey(Collect record);
    
    int selectDemandCountByEmployeeId(@Param("employeeId") Long employeeId);
    
    List<Demand> selectDemandListByEmployeeId(DemandSearch demandSearch);
    
    Collect selectCollect(@Param("employeeId") Long employeeId, @Param("demandId") Long demandId);
    //列表
    List<MyCollect> selectMyCollectList(MyCollect myCollect);
    //记数
    int selectMyCollectListCount(MyCollect myCollect);
    
    int deleteByEmployeeIdAndDemandId(Collect collect);
    
    int isAlreadyCollect(Collect collect);
    
    List<Collect> selectCollectsByDemandId(Long demandId);
}



