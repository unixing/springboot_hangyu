package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.RoleNew;

public interface RoleNewMapper {
	int insert(RoleNew role);
	
	int insertSelective(RoleNew role);
	
	int delete(@Param("roleId") Long roleId);
	
	int update(RoleNew role);
	
	RoleNew load(@Param("roleId") Long roleId);
	
	List<RoleNew> selectRoles(@Param("createId") Long createId, @Param("name") String name);
	
	List<RoleNew> selectRolesByRoleName(@Param("createId") Long createId, @Param("name") String name);
}
