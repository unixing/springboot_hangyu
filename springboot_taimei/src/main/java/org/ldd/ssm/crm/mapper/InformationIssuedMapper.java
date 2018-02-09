package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.InformationIssued;

public interface InformationIssuedMapper {
    int deleteByPrimaryKey(Long id);

    int insert(InformationIssued record);

    int insertSelective(InformationIssued record);

    InformationIssued selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InformationIssued record);

    int updateByPrimaryKey(InformationIssued record);
    
    List<String> getPageList(@Param("startIndex") int startIndex, @Param("pageNo") int pageNo);
    
    List<InformationIssued> getVersionInformation(@Param("lclDptDay") String lclDptDay);
    
    int getPageCount();
    
    int updateDataType(@Param("lclDptDay") String lclDptDay);
    
    int isHasNewVersionInfo();
}