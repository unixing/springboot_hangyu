package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.DatasupplierRole;

public interface DatasupplierRoleMapper {
    int insert(DatasupplierRole record);

    int insertSelective(DatasupplierRole record);
    
    int delete(Long roleId);
    
    int deleteByDatasupplierId(@Param("datasupplierId") Long datasupplierId);
    
    List<DatasupplierRole> selectAll(@Param("datasupplierId") Long datasupplierId);
}