package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Resource;
import org.ldd.ssm.crm.domain.RoleResource;

public interface RoleResourceMapper {
    int insert(RoleResource record);

    int insertSelective(RoleResource record);
    
    int deleteByRole(@Param("roleId") Long roleId);
    
    List<Resource> selectByRole(@Param("roleId") Long roleId);
}