package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.TransportInfo;

public interface TransportInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TransportInfo record);

    int insertSelective(TransportInfo record);

    TransportInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TransportInfo record);

    int updateByPrimaryKey(TransportInfo record);
    
    List<TransportInfo> selectByName(@Param("name") String name);
}