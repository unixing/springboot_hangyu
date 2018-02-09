package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.utils.RetBean;

public interface MoneyIncomeRecordService {
	
	public MoneyIncomeRecord selectByPrimaryKey(Long id);

	public boolean deleteMoneyIncomeRecordByPrimaryKey(Long id);
	
	public boolean insert(MoneyIncomeRecord record);
    
	public boolean insertSelective(MoneyIncomeRecord record);

    public boolean updateByPrimaryKeySelective(MoneyIncomeRecord record);

    public boolean updateByPrimaryKey(MoneyIncomeRecord record);
    
    public List<TransactionRecord> incomeTransactionRecordList(Long employeeId);
    
    public RetBean unfreezeIntentionMoney(MoneyIncomeRecord record);

    public RetBean incomeIntentionMoney(MoneyIncomeRecord record);
    
    public TransactionRecord incomeTransactionRecordDetails(Long id);
    
    public TransactionRecord incomeTransactionRecordJpg(Long id);
}
