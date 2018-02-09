package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Role;

public interface RoleMapper {
	int insert(Role role);
	
	int insertSelective(Role role);
	
	int delete(@Param("roleId") Long roleId);
	
	int update(Role role);
	
	Role load(@Param("roleId") Long roleId);
	
	List<Role> selectRoles(@Param("createId") Long createId, @Param("companyId") Long datSreCpyId, @Param("name") String name);
}
