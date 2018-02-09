package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.FocusFlight;

public interface FocusFlightMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FocusFlight record);

    int insertSelective(FocusFlight record);

    FocusFlight selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FocusFlight record);

    int updateByPrimaryKey(FocusFlight record);
    
    int deleteByEmployeeId(@Param("employeeId") Long employeeId);
    
    int save(List<FocusFlight> list);
    
    int batchUpdate(List<FocusFlight> list);
    
    int update(FocusFlight recourd);
    
    FocusFlight load(@Param("record") FocusFlight record, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    int delete(FocusFlight recourd);
    
    List<FocusFlight> getFocusFlight(@Param("employeeId") Long employeeId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}