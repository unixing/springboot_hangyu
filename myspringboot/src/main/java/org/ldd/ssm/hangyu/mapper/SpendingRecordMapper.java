package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;

public interface SpendingRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SpendingRecord record);

    int insertSelective(SpendingRecord record);

    SpendingRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SpendingRecord record);

    int updateByPrimaryKey(SpendingRecord record);
    
    List<TransactionRecord> spendingTransactionRecordList(Long employeeId);
    
    TransactionRecord spendingTransactionRecordDetails(Long id);
    
    TransactionRecord spendingTransactionRecordJpg(Long id);
}