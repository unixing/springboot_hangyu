package org.ldd.ssm.hangyu.service;

import java.util.List;

import org.ldd.ssm.hangyu.domain.BankCard;

public interface BankCardService {

	public BankCard selectByPrimaryKey(Long id);
	
	public boolean deleteBankCardByPrimaryKey(Long id);
	
	public boolean insert(BankCard card);
    
	public boolean insertSelective(BankCard card);

    public boolean updateByPrimaryKeySelective(BankCard card);

    public boolean updateByPrimaryKey(BankCard card);
    
    public List<BankCard> selectCardsByEmployeeId(Long employeeId);
}
