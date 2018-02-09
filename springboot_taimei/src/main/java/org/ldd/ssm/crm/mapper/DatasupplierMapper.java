package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.ldd.ssm.crm.domain.Datasupplier;

public interface DatasupplierMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Datasupplier record);

    int insertSelective(Datasupplier record);

    Datasupplier selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Datasupplier record);

    int updateByPrimaryKey(Datasupplier record);
    
    Datasupplier load(@Param("employeeId") Long employeeId, @Param("datSreCpyId") Long datSreCpyId);
    
    List<Datasupplier> selectAll(@Param("employeeId") Long employeeId);
    
    List<Datasupplier> selectByCompanyId(@Param("companyId") Long companyId);
    
    List<String> getDataSource(@Param("companyId") Long companyId);
}