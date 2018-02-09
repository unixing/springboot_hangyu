package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.hangyu.domain.CulturaleducationyYear;

public interface CulturaleducationyYearMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CulturaleducationyYear record);

    int insertSelective(CulturaleducationyYear record);

    CulturaleducationyYear selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CulturaleducationyYear record);

    int updateByPrimaryKey(CulturaleducationyYear record);
    
    List<CulturaleducationyYear> selectCulturaleducationyYearListByCityId(@Param("cityId") Long cityId);
}