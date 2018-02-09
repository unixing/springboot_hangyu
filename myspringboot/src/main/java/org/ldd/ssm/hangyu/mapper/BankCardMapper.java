package org.ldd.ssm.hangyu.mapper;

import java.util.List;

import org.ldd.ssm.hangyu.domain.BankCard;

public interface BankCardMapper {
	int deleteByPrimaryKey(Long id);

    int insert(BankCard card);

    int insertSelective(BankCard card);

    BankCard selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BankCard card);

    int updateByPrimaryKey(BankCard card);
    
    List<BankCard> selectCardsByEmployeeId(Long employeeId);

}
