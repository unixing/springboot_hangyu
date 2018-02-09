package org.ldd.ssm.hangyu.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.mapper.IndividualCapitalAccountMapper;
import org.ldd.ssm.hangyu.mapper.SpendingRecordMapper;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.SpendingRecordService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.RetBean;
import org.ldd.ssm.hangyu.utils.SerialNumberUtil;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpendingRecordServiceImpl implements SpendingRecordService {

	@Autowired
	private SpendingRecordMapper spendingRecordMapper;
	
	@Autowired
	private IndividualCapitalAccountMapper individualCapitalAccountMapper;
	
	Logger log=Logger.getLogger(SpendingRecordServiceImpl.class);
	
	@Override
	public SpendingRecord selectByPrimaryKey(Long id) {
		SpendingRecord record=null;
		if (id==0||id==null) {
			log.debug("selectByPrimaryKey:id is null");
			return record;
		}
		
		try {
			record=spendingRecordMapper.selectByPrimaryKey(id);
		} catch (Exception e) {
			log.error("selectByPrimaryKey:there is something wrong here");
			return record;
		}
		return record;
	}

	@Override
	public boolean deleteSpendingRecordByPrimaryKey(Long id) {
		boolean result=false;
		if (id==0||id==null) {
			log.debug("deleteSpendingRecordByPrimaryKey:id is null");
			return result;
		}
		
		try {
			int activelines=spendingRecordMapper.deleteByPrimaryKey(id);
			if (activelines==1) {
				result=true;
			}
		} catch (Exception e) {
			log.error("deleteSpendingRecordByPrimaryKey:there is something wrong here");
			return result;
		}
		return result;
	}

	@Override
	public boolean insert(SpendingRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("insert:record is null");
			return result;
		}
		
		try {
			int activelines=spendingRecordMapper.insert(record);
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
	public boolean insertSelective(SpendingRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("insertSelective:record is null");
			return result;
		}
		
		try {
			int activelines=spendingRecordMapper.insertSelective(record);
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
	public boolean updateByPrimaryKeySelective(SpendingRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("updateByPrimaryKeySelective:record is null");
			return result;
		}
		
		try {
			int activelines=spendingRecordMapper.updateByPrimaryKeySelective(record);
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
	public boolean updateByPrimaryKey(SpendingRecord record) {
		boolean result=false;
		if (record==null) {
			log.debug("updateByPrimaryKey:record is null");
			return result;
		}
		
		try {
			int activelines=spendingRecordMapper.updateByPrimaryKey(record);
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
	public List<TransactionRecord> spendingTransactionRecordList(Long employeeId) {
		List<TransactionRecord> spendingRecords=new ArrayList<TransactionRecord>();
		if (employeeId==0||employeeId==null) {
			log.debug("spendingTransactionRecordList:employeeId is null");
			return spendingRecords;
		}
		
		try {
			spendingRecords=spendingRecordMapper.spendingTransactionRecordList(employeeId);
		} catch (Exception e) {
			log.error("spendingTransactionRecordList:there is something wrong here");
			return spendingRecords;
		}
		return spendingRecords;
	}
	
	@Override
	public boolean accountWithdraw(IndividualCapitalAccount accountFromLeading,IndividualCapitalAccount accountFromBackground){
		boolean result=false;
		if (accountFromLeading==null||accountFromBackground==null) {
			return result;
		}
		SpendingRecord record=new SpendingRecord();
		
		record.setAccountId(accountFromBackground.getId());
		//record.setToAccountId(record.getAccountId());//线下操作，没有目标账户
		record.setNumber(accountFromLeading.getWithdrawNumber());
		record.setDate(DateUtil.date2Str(new Date(),"yyyy-MM-dd HH:mm:ss"));
		record.setType("02");
		record.setPhase("0");
		record.setSerialNumber(SerialNumberUtil.createSeriaNumber(record.getType(),getSerialLastFiveNum()));
		
		result=insertSelective(record);
		
		return result;
	}

	@Override
	public RetBean freezeIntentionMoney(SpendingRecord record) {
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
			retBean.setMassage("冻结成功");
		}else {
			retBean.setBool(true);
			retBean.setMassage("冻结失败");
		}
		
		return retBean;
	}

	@Override
	public RetBean spendingIntentionMoney(SpendingRecord record) {
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
			retBean.setMassage("支付成功");
		}else {
			retBean.setBool(true);
			retBean.setMassage("支付失败");
		}
		
		return retBean;
	}

	@Override
	public TransactionRecord spendingTransactionRecordDetails(Long id) {
		TransactionRecord record=null;
		if (id==0||id==null) {
			log.debug("spendingTransactionRecordDetails:id is null");
			return record;
		}
		
		try {
			record=spendingRecordMapper.spendingTransactionRecordDetails(id);
		} catch (Exception e) {
			log.error("spendingTransactionRecordDetails:there is something wrong here");
			return record;
		}
		return record;
	}

	@Override
	public TransactionRecord spendingTransactionRecordJpg(Long id) {
		TransactionRecord record=null;
		if (id==0||id==null) {
			log.debug("spendingTransactionRecordJpg:id is null");
			return record;
		}
		try {
			record=spendingRecordMapper.spendingTransactionRecordJpg(id);
		} catch (Exception e) {
			log.error("spendingTransactionRecordJpg:there is something wrong here");
			return record;
		}
		return record;
	}
	
	private String getSerialLastFiveNum(){
		String reSeriealNum=individualCapitalAccountMapper.selectLastSpendingRecordSeriesNum();
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
