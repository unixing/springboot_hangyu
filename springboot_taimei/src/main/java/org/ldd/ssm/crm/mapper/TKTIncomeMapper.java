package org.ldd.ssm.crm.mapper;

import java.util.List;

import org.ldd.ssm.crm.domain.TKTIncome;

public interface TKTIncomeMapper {
    int insert(TKTIncome dto);
   
    int deleteTKTIncome(Long id);
    
    List<String> getflightLeg(String fltNbr);
    
    int updateTKTIncome(TKTIncome dto);
}