package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.BankCard;
import org.ldd.ssm.hangyu.mapper.BankCardMapper;
import org.ldd.ssm.hangyu.service.BankCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankCardServiceImpl implements BankCardService {

	@Autowired
	private BankCardMapper bankCardMapper;
	
	Logger log=Logger.getLogger(BankCardServiceImpl.class);
	
	@Override
	public BankCard selectByPrimaryKey(Long id) {
		BankCard card=null;
		if (id==null||id==0) {
			log.debug("selectByPrimaryKey:id is null");
			return card;
		}
		try {
			card=bankCardMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something wrong here");
			e.printStackTrace();
			return card;
		}
		return card;
	}

	@Override
	public boolean deleteBankCardByPrimaryKey(Long id) {
		boolean result=false;
		if (id==null||id==0) {
			log.debug("deleteBankCardByPrimaryKey:id is null");
			return result;
		}
		try {
			int activelines=bankCardMapper.deleteByPrimaryKey(id);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("deleteBankCardByPrimaryKey:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean insert(BankCard card) {
		boolean result=false;
		if (card==null) {
			log.debug("insert:card is null");
			return result;
		}
		try {
			int activelines=bankCardMapper.insert(card);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("insert:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean insertSelective(BankCard card) {
		boolean result=false;
		if (card==null) {
			log.debug("insertSelective:card is null");
			return result;
		}
		try {
			int activelines=bankCardMapper.insertSelective(card);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("insertSelective:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean updateByPrimaryKeySelective(BankCard card) {
		boolean result=false;
		if (card==null) {
			log.debug("updateByPrimaryKeySelective:card is null");
			return result;
		}
		try {
			int activelines=bankCardMapper.updateByPrimaryKeySelective(card);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKeySelective:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean updateByPrimaryKey(BankCard card) {
		boolean result=false;
		if (card==null) {
			log.debug("updateByPrimaryKey:card is null");
			return result;
		}
		try {
			int activelines=bankCardMapper.updateByPrimaryKey(card);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKey:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public List<BankCard> selectCardsByEmployeeId(Long employeeId) {
		List<BankCard> cards=new ArrayList<BankCard>();
		if (employeeId==null||employeeId==0) {
			log.debug("selectCardsByEmployeeId:employeeId is null");
			return cards;
		}
		try {
			cards=bankCardMapper.selectCardsByEmployeeId(employeeId);
		} catch (Exception e) {
			log.error("selectCardsByEmployeeId:there is something wrong here");
			return cards;
		}
		return cards;
	}

}
