package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.utils.RetBean;

public interface SpendingRecordService {
	
	public SpendingRecord selectByPrimaryKey(Long id);

	public boolean deleteSpendingRecordByPrimaryKey(Long id);
	
	public boolean insert(SpendingRecord record);
    
	public boolean insertSelective(SpendingRecord record);

    public boolean updateByPrimaryKeySelective(SpendingRecord record);

    public boolean updateByPrimaryKey(SpendingRecord record);
    
    public List<TransactionRecord> spendingTransactionRecordList(Long employeeId);
    
    public boolean accountWithdraw(IndividualCapitalAccount accountFromLeading, IndividualCapitalAccount accountFromBackground);
    
    public RetBean freezeIntentionMoney(SpendingRecord record);
    
    public RetBean spendingIntentionMoney(SpendingRecord record);
    
    public TransactionRecord spendingTransactionRecordDetails(Long id);
    
    public TransactionRecord spendingTransactionRecordJpg(Long id);
}
