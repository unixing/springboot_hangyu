package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.mapper.IndividualCapitalAccountMapper;
import org.ldd.ssm.hangyu.mapper.MoneyIncomeRecordMapper;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.MoneyIncomeRecordService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.ldd.ssm.hangyu.utils.SerialNumberUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoneyIncomeRecordServiceImpl implements MoneyIncomeRecordService {

	@Autowired
	private MoneyIncomeRecordMapper moneyIncomeRecordMapper;
	
	@Autowired
	private IndividualCapitalAccountMapper individualCapitalAccountMapper;
	
	Logger log=Logger.getLogger(MoneyIncomeRecordServiceImpl.class);
	
	@Override
	public MoneyIncomeRecord selectByPrimaryKey(Long id) {
		MoneyIncomeRecord record=null;
		if (id==null||id==0) {
			log.debug("selectByPrimaryKey:id is null");
			return record;
		}
		try {
			record=moneyIncomeRecordMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something wrong here");
			return record;
		}
		return record;
	}

	@Override
	public boolean deleteMoneyIncomeRecordByPrimaryKey(Long id) {
		boolean result=false;
		if (id==null||id==0) {
			log.debug("deleteMoneyIncomeRecordByPrimaryKey:id is null");
			return result;
		}
		try {
			int activelines=moneyIncomeRecordMapper.deleteByPrimaryKey(id);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("deleteMoneyIncomeRecordByPrimaryKey:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean insert(MoneyIncomeRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("insert:record is null");
			return result;
		}
		try {
			int activelines=moneyIncomeRecordMapper.insert(record);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("insert:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean insertSelective(MoneyIncomeRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("insertSelective:record is null");
			return result;
		}
		try {
			int activelines=moneyIncomeRecordMapper.insertSelective(record);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("insertSelective:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean updateByPrimaryKeySelective(MoneyIncomeRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("updateByPrimaryKeySelective:record is null");
			return result;
		}
		try {
			int activelines=moneyIncomeRecordMapper.updateByPrimaryKeySelective(record);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKeySelective:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean updateByPrimaryKey(MoneyIncomeRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("updateByPrimaryKey:record is null");
			return result;
		}
		try {
			int activelines=moneyIncomeRecordMapper.updateByPrimaryKey(record);
			if(activelines==1){
				result=true;
			}
		} catch (Exception e) {
			log.error("updateByPrimaryKey:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public List<TransactionRecord> incomeTransactionRecordList(Long employeeId) {
		List<TransactionRecord> incomeRecords=new ArrayList<TransactionRecord>();
		if (employeeId==0||employeeId==null) {
			log.debug("incomeTransactionRecordList:employeeId is null");
			return incomeRecords;
		}
		
		try {
			incomeRecords=moneyIncomeRecordMapper.incomeTransactionRecordList(employeeId);
		} catch (Exception e) {
			log.error("incomeTransactionRecordList:there is something wrong here");
			return incomeRecords;
		}
		return incomeRecords;
	}

	@Override
	public RetBean unfreezeIntentionMoney(MoneyIncomeRecord record) {
		RetBean retBean=new RetBean();
		if (record.getDemandId()==0||record.getDemandId()==null
				||TextUtil.isEmpty(record.getType())
				||record.getAccountId()==0||record.getAccountId()==null
				||TextUtil.isEmpty(record.getNumber())) {
			retBean.setBool(true);
			retBean.setMassage("参数不足");
			return retBean;
		}
		
		record.setPhase("2");//线上自动处理,初始化交易状态2成功
		record.setSerialNumber(SerialNumberUtil.createSeriaNumber(record.getType(),getSerialLastFiveNum()));//生成交易流水号
		record.setDate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));//交易日期
		
		if (insertSelective(record)) {
			retBean.setMassage("解冻成功");
		}else {
			retBean.setBool(true);
			retBean.setMassage("解冻失败");
		}
		
		
		return retBean;
	}

	@Override
	public RetBean incomeIntentionMoney(MoneyIncomeRecord record) {
		RetBean retBean=new RetBean();
		if (record.getDemandId()==0||record.getDemandId()==null
				||TextUtil.isEmpty(record.getType())
				||record.getAccountId()==0||record.getAccountId()==null
				||record.getToAccountId()==0||record.getToAccountId()==null
				||TextUtil.isEmpty(record.getNumber())) {
			retBean.setBool(true);
			retBean.setMassage("参数不足");
			return retBean;
		}
		
		record.setPhase("2");//线上自动处理,初始化交易状态2成功
		record.setSerialNumber(SerialNumberUtil.createSeriaNumber(record.getType(),getSerialLastFiveNum()));//生成交易流水号
		record.setDate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));//交易日期
		
		if (insertSelective(record)) {
			retBean.setMassage("转入成功");
		}else {
			retBean.setBool(true);
			retBean.setMassage("转入失败");
		}
		
		
		return retBean;
	}

	@Override
	public TransactionRecord incomeTransactionRecordDetails(Long id) {
		TransactionRecord record=null;
		if (id==null||id==0) {
			log.debug("incomeTransactionRecordDetails:id is null");
			return record;
		}
		try {
			record=moneyIncomeRecordMapper.incomeTransactionRecordDetails(id);
		} catch (Exception e) {
			log.error("incomeTransactionRecordDetails:there is something wrong here");
			return record;
		}
		return record;
	}

	@Override
	public TransactionRecord incomeTransactionRecordJpg(Long id) {
		TransactionRecord record=null;
		if (id==null||id==0) {
			log.debug("incomeTransactionRecordJpg:id is null");
			return record;
		}
		try {
			record=moneyIncomeRecordMapper.incomeTransactionRecordJpg(id);
		} catch (Exception e) {
			log.error("incomeTransactionRecordJpg:there is something wrong here");
			return record;
		}
		return record;
	}
	
	private String getSerialLastFiveNum(){
		String reSeriealNum=individualCapitalAccountMapper.selectLastMoneyIncomeRecordSeriesNum();
		if(reSeriealNum==null){
			return "00000";//表里没数据
		}
		String num=reSeriealNum.trim().substring(10);
		if(num.length()>0&&Integer.parseInt(num)==99999){
			return "00000";
		}
		return (Integer.parseInt(num)+1)+"";
	}

}
