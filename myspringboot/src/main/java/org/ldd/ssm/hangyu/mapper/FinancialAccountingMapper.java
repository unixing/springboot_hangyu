package org.ldd.ssm.hangyu.mapper;

import org.ldd.ssm.hangyu.domain.FinancialAccounting;

public interface FinancialAccountingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FinancialAccounting record);

    int insertSelective(FinancialAccounting record);

    FinancialAccounting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinancialAccounting record);

    int updateByPrimaryKey(FinancialAccounting record);
}