package org.ldd.ssm.hangyu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.mapper.IndividualCapitalAccountMapper;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.IndividualCapitalAccountService;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndividualCapitalAccountServiceImpl implements
		IndividualCapitalAccountService {

	@Autowired
	private IndividualCapitalAccountMapper individualCapitalAccountMapper;

	Logger log = Logger.getLogger(IndividualCapitalAccountServiceImpl.class);

	@Override
	public IndividualCapitalAccount selectByEmployeeId(Long employeeId) {
		IndividualCapitalAccount account = null;
		if (employeeId.longValue() == 0 || employeeId == null) {
			log.debug("selectByEmployeeId:employeeId is null");
			return account;
		}
		try {
			account = individualCapitalAccountMapper
					.selectByEmployeeId(employeeId);
		} catch (Exception e) {
			log.error("selectByEmployeeId:there is something wrong");
			return account;
		}

		return account;
	}

	@Override
	public boolean updateByPrimaryKeySelective(IndividualCapitalAccount record) {
		boolean result = false;
		if (record == null) {
			log.debug("updateByPrimaryKeySelective:record is null");
			return result;
		}
		try {
			int activlines = individualCapitalAccountMapper
					.updateByPrimaryKeySelective(record);
			if (activlines == 1) {
				result = true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKeySelective:there is something wrong here");
			return result;
		}
		return result;
	}

	// 冻结
	@Override
	public Map<String, Object> changeIntentionMoney(TransactionRecord record,
			Long employeeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		RetBean retBean = new RetBean();
		// 设置账户id
		IndividualCapitalAccount account = selectByEmployeeId(employeeId);
		if (account == null) {
			retBean.setBool(true);
			retBean.setMassage("账户不存在");
			map.put("retBean", retBean);
			map.put("account", account);
			return map;
		} else {
			// 冻结
			if ("03".equals(record.getType().substring(0, 2))) {
				if (Integer.parseInt(account.getNumber()) < Integer
						.parseInt(record.getNumber())) {
					retBean.setBool(true);
					retBean.setMassage("余额不足");
					map.put("retBean", retBean);
					map.put("account", account);
					return map;
				} else {
					// 操作帐户表
					account.setNumber((Integer.parseInt(account.getNumber()) - Integer
							.parseInt(record.getNumber())) + "");
					account.setFreezeNumber((Integer.parseInt(account
							.getFreezeNumber()) + Integer.parseInt(record
							.getNumber()))
							+ "");
					if (updateByPrimaryKeySelective(account)) {
						retBean.setMassage("帐户表操作成功");
						map.put("retBean", retBean);
						map.put("account", account);// 返回的是冻结后的账户信息
					} else {
						retBean.setBool(true);
						retBean.setMassage("帐户表操作失败");
						map.put("retBean", retBean);
						map.put("account", account);
						return map;
					}
				}

				// 解冻
			} else if ("04".equals(record.getType().substring(0, 2))) {
				// 操作帐户表
				account.setNumber((Integer.parseInt(account.getNumber()) + Integer
						.parseInt(record.getNumber())) + "");
				account.setFreezeNumber((Integer.parseInt(account
						.getFreezeNumber()) - Integer.parseInt(record
						.getNumber()))
						+ "");
				if (updateByPrimaryKeySelective(account)) {
					retBean.setMassage("帐户表操作成功");
					map.put("retBean", retBean);
					map.put("account", account);// 返回的是解冻后的账户信息
				} else {
					retBean.setBool(true);
					retBean.setMassage("帐户表操作失败");
					map.put("retBean", retBean);
					map.put("account", account);
					return map;
				}

			}

		}
		return map;
	}

	@Override
	public Map<String, Object> rechargeIntentionMoney(TransactionRecord record,
			Long employeeId, Long toEmployeeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		RetBean retBean = new RetBean();
		// 设置账户id
		IndividualCapitalAccount account = selectByEmployeeId(employeeId);
		IndividualCapitalAccount toAccount = selectByEmployeeId(toEmployeeId);
		if (account == null || toAccount == null) {
			retBean.setBool(true);
			retBean.setMassage("账户不存在");
			map.put("retBean", retBean);
			map.put("account", account);
			return map;
		} else {
			// 转入
			if ("05".equals(record.getType().substring(0, 2))) {

				// 操作目标帐户支付
				toAccount
						.setFreezeNumber((Integer.parseInt(toAccount.getFreezeNumber()) - Integer
								.parseInt(record.getNumber())) + "");
				toAccount.setTotalNumber((Integer.parseInt(toAccount
						.getTotalNumber()) - Integer.parseInt(record
						.getNumber()))
						+ "");
				if (updateByPrimaryKeySelective(toAccount)) {
					//retBean.setMassage("目标帐户操作成功");
					//map.put("retBean", retBean);
					map.put("toAccount", toAccount);// 返回的是支付后的账户信息
				} else {
					retBean.setBool(true);
					retBean.setMassage("目标帐户操作失败");
					map.put("retBean", retBean);
					map.put("account", account);
					map.put("toAccount", toAccount);
					return map;
				}

				// 操作帐户表转入
				account.setNumber((Integer.parseInt(account.getNumber()) + Integer
						.parseInt(record.getNumber())) + "");
				account.setTotalNumber((Integer.parseInt(account
						.getTotalNumber()) + Integer.parseInt(record
						.getNumber()))
						+ "");
				if (updateByPrimaryKeySelective(account)) {
					retBean.setMassage("帐户表操作成功");
					map.put("retBean", retBean);
					map.put("account", account);// 返回的是转入后的账户信息
				} else {
					retBean.setBool(true);
					retBean.setMassage("帐户表操作失败");
					map.put("retBean", retBean);
					map.put("account", account);
					map.put("toAccount", toAccount);
					return map;
				}

				// 支出
			} else if ("06".equals(record.getType().substring(0, 2))) {
				// 操作帐户表 支付
				account.setFreezeNumber((Integer.parseInt(account.getFreezeNumber()) - Integer
						.parseInt(record.getNumber())) + "");
				account.setTotalNumber((Integer.parseInt(account
						.getTotalNumber()) - Integer.parseInt(record
						.getNumber()))
						+ "");
				if (updateByPrimaryKeySelective(account)) {
					//retBean.setMassage("帐户表操作成功");
					//map.put("retBean", retBean);
					map.put("account", account);// 返回的是支付后的账户信息
				} else {
					retBean.setBool(true);
					retBean.setMassage("帐户表操作失败");
					map.put("retBean", retBean);
					map.put("account", account);
					map.put("toAccount", toAccount);
					return map;
				}
				
				// 操作目标帐户转入
				toAccount
						.setNumber((Integer.parseInt(toAccount.getNumber()) + Integer
								.parseInt(record.getNumber())) + "");
				toAccount.setTotalNumber((Integer.parseInt(toAccount
						.getTotalNumber()) + Integer.parseInt(record
						.getNumber()))
						+ "");
				if (updateByPrimaryKeySelective(toAccount)) {
					retBean.setMassage("目标帐户操作成功");
					map.put("retBean", retBean);
					map.put("toAccount", toAccount);// 返回的是转入后的账户信息
				} else {
					retBean.setBool(true);
					retBean.setMassage("目标帐户操作失败");
					map.put("retBean", retBean);
					map.put("account", account);
					map.put("toAccount", toAccount);
					return map;
				}

			}

		}
		return map;
	}

}
