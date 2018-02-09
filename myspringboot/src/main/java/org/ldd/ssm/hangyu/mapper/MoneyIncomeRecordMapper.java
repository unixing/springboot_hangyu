package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;

public interface MoneyIncomeRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MoneyIncomeRecord record);

    int insertSelective(MoneyIncomeRecord record);

    MoneyIncomeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MoneyIncomeRecord record);

    int updateByPrimaryKey(MoneyIncomeRecord record);
    
    List<TransactionRecord> incomeTransactionRecordList(Long employeeId);
    
    TransactionRecord incomeTransactionRecordDetails(Long id);
    
    TransactionRecord incomeTransactionRecordJpg(Long id);
}