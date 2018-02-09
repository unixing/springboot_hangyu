package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.SaleDptInfo;

public interface SaleDptInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SaleDptInfo record);

    int insertSelective(SaleDptInfo record);

    SaleDptInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SaleDptInfo record);

    int updateByPrimaryKey(SaleDptInfo record);
    
    List<SaleDptInfo> selectByName(@Param("name") String name);
}