package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.MenuNew;
import org.ldd.ssm.crm.domain.RoleMenuNew;

public interface RoleMenuNewMapper {
    int insert(RoleMenuNew record);

    int insertSelective(RoleMenuNew record);
    
    int delete(Long roleId, Long menuId);
    
    int deleteByRoleId(@Param("roleId") Long roleId);
    
    List<MenuNew> selectMenuList(@Param("roleId") Long roleId);
    
    List<String> getCurrentEmployeeResource(@Param("roleId") Long roleId);
    
    int batchInsert(List<RoleMenuNew> list);
}