package org.ldd.ssm.crm.mapper;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.RoleMenu;

public interface RoleMenuMapper {
    int insert(RoleMenu record);

    int insertSelective(RoleMenu record);
    
    int delete(Long roleId, Long menuId);
    
    int deleteByRoleId(@Param("roleId") Long roleId);
}