package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.query.TransactionRecord;

public interface IndividualCapitalAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(IndividualCapitalAccount record);

    int insertSelective(IndividualCapitalAccount record);

    IndividualCapitalAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IndividualCapitalAccount record);

    int updateByPrimaryKey(IndividualCapitalAccount record);
    
    IndividualCapitalAccount selectByEmployeeId(Long employeeId);
    
    //查最后一条支出交易记录
    @Select("select   serialNumber   from   spendingRecord   order   by   right(serialNumber, 5)   desc   limit   1")
    String selectLastSpendingRecordSeriesNum();
    
    //查最后一条收入交易记录
    @Select("select   serialNumber   from   moneyIncomeRecord   order   by   right(serialNumber, 5)   desc   limit   1")
    String selectLastMoneyIncomeRecordSeriesNum();
}