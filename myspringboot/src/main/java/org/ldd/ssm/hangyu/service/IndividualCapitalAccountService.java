package org.ldd.ssm.hangyu.service;

import java.util.Map;

import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.utils.RetBean;

public interface IndividualCapitalAccountService {
	
	public IndividualCapitalAccount selectByEmployeeId(Long employeeId);
	
	public boolean updateByPrimaryKeySelective(IndividualCapitalAccount record);
	
	public Map<String, Object> changeIntentionMoney(TransactionRecord record, Long employeeId);
	
	public Map<String, Object> rechargeIntentionMoney(TransactionRecord record, Long employeeId, Long toEmployeeId);

}
