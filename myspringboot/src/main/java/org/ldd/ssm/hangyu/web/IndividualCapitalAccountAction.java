package org.ldd.ssm.hangyu.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ldd.ssm.hangyu.domain.BankCard;
import org.ldd.ssm.hangyu.domain.IndividualCapitalAccount;
import org.ldd.ssm.hangyu.domain.MoneyIncomeRecord;
import org.ldd.ssm.hangyu.domain.SpendingRecord;
import org.ldd.ssm.hangyu.query.TransactionRecord;
import org.ldd.ssm.hangyu.service.BankCardService;
import org.ldd.ssm.hangyu.service.IndividualCapitalAccountService;
import org.ldd.ssm.hangyu.service.MoneyIncomeRecordService;
import org.ldd.ssm.hangyu.service.SpendingRecordService;
import org.ldd.ssm.hangyu.utils.DateUtil;
import org.ldd.ssm.hangyu.utils.ListUtils;
import org.ldd.ssm.hangyu.utils.TextUtil;
import org.ldd.ssm.hangyu.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IndividualCapitalAccountAction {

	Logger log = Logger.getLogger(IndividualCapitalAccountAction.class);

	@Autowired
	private IndividualCapitalAccountService individualCapitalAccountService;

	@Autowired
	private BankCardService bankCardService;

	@Autowired
	private MoneyIncomeRecordService moneyIncomeRecordService;

	@Autowired
	private SpendingRecordService spendingRecordService;

	@RequestMapping("/perSonNalCompanyAccountList")
	@ResponseBody
	public Map<String, Object> perSonNalCompanyAccount() {
		Map<String, Object> map = new HashMap<String, Object>();

		// 银行卡信息
		// 账户余额信息
		// 交易记录列表

		// 1、银行卡信息
		List<BankCard> cards = bankCardService
				.selectCardsByEmployeeId(UserContext.getUser().getId());
		if (cards.size() < 1) {
			map.put("cardMessage", "您还未绑定银行卡");
			return map;
		} else {
			map.put("card", cards.get(0));
		}

		// 2、账户余额信息
		IndividualCapitalAccount account = individualCapitalAccountService
				.selectByEmployeeId(UserContext.getUser().getId());
		if (account == null) {
			map.put("accountMessage", "您还未开通账户");
		} else {
			map.put("account", account);
		}

		// 3、交易记录列表
		// 3.1支入
		List<TransactionRecord> incomeRecords = new ArrayList<TransactionRecord>();
		incomeRecords = moneyIncomeRecordService
				.incomeTransactionRecordList(UserContext.getUser().getId());
		// 3.2支出
		List<TransactionRecord> spendingRecords = new ArrayList<TransactionRecord>();
		spendingRecords = spendingRecordService
				.spendingTransactionRecordList(UserContext.getUser().getId());

		// 3.3整合交易记录
		incomeRecords.addAll(spendingRecords);
		// 3.4按交易日期降序排列
		if (incomeRecords.size() > 0) {
			ListUtils.sort(incomeRecords, false, "date");// true升序，false降序
			for (TransactionRecord record : incomeRecords) {
				// 处理日期
				ediTransactionRecords(record);
				// 处理阶段状态
			}
		} else {
			map.put("transactionRecordsMessage", "您还没有交易记录");
		}
		map.put("transactionRecords", incomeRecords);

		return map;
	}

	private void ediTransactionRecords(TransactionRecord record) {
		// 日期格式
		record.setDateComplete(DateUtil.date2Str(
				DateUtil.StringToDate(record.getDate()), "yyyy.MM.dd HH:mm"));
		record.setDate(DateUtil.date2Str(
				DateUtil.StringToDate(record.getDate()), "MM.dd.yyyy"));
		// 需求类型
		if ("0".equals(record.getDemandType())) {
			record.setDemandTypeStr("航线需求");
		} else if ("1".equals(record.getDemandType())) {
			record.setDemandTypeStr("运力投放");
		} else if ("2".equals(record.getDemandType())) {
			record.setDemandTypeStr("运营托管");
		} else if ("3".equals(record.getDemandType())) {
			record.setDemandTypeStr("航线委托");
		} else if ("4".equals(record.getDemandType())) {
			record.setDemandTypeStr("运力委托");
		}
		
		if (record.getJpg()!=null) {
			record.setJpg(null);
			record.setWetherJpg(true);
		}else {
			record.setWetherJpg(false);
		}

		if ("01".equals(record.getType())) {// 充值
			record.setDiscription1("充值" + record.getNumber());

			if ("0".equals(record.getPhase())) {
				record.setDiscription2("充值发起状态");
			} else if ("1".equals(record.getPhase())) {
				record.setDiscription2("充值审核状态");
			} else if ("2".equals(record.getPhase())) {
				record.setDiscription2("充值成功");
			}
		} else if ("02".equals(record.getType())) {// 提现
			record.setDiscription1("申请提现" + record.getNumber());

			if ("0".equals(record.getPhase())) {
				record.setDiscription2("提现发起状态");
			} else if ("1".equals(record.getPhase())) {
				record.setDiscription2("提现审核状态");
			} else if ("2".equals(record.getPhase())||"00".equals(record.getPhase())) {
				record.setDiscription2("提现成功");
			}
		} else if ("0301".equals(record.getType())) {// 冻结
			record.setDiscription1("查看" + " ");
			record.setDiscription2("冻结" + record.getNumber());
		} else if ("0302".equals(record.getType())) {// 冻结
			record.setDiscription1("响应" + " ");
			record.setDiscription2("冻结" + record.getNumber());
		} else if ("0401".equals(record.getType())) {// 解冻
			record.setDiscription1("撤回" + " ");
			record.setDiscription2("解冻" + record.getNumber());
		} else if ("0402".equals(record.getType())) {// 解冻
			record.setDiscription1("下架" + " ");
			record.setDiscription2("解冻" + record.getNumber());
		} else if ("0403".equals(record.getType())) {// 解冻
			record.setDiscription1("落选" + " ");
			record.setDiscription2("解冻" + record.getNumber());
		} else if ("05".equals(record.getType())) {// 转入
			record.setDiscription1("确认" + " ");
			record.setDiscription2("转入意向金" + record.getNumber());
		} else if ("06".equals(record.getType())) {// 支付
			record.setDiscription1("确认" + " ");
			record.setDiscription2("支出意向金" + record.getNumber());
		}
		
	}

	// 账户充值
	@RequestMapping("/accountRecharge")
	@ResponseBody
	public synchronized Map<String, Object> accountRecharge() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1、太美银行卡信息
		List<BankCard> cards = bankCardService
				.selectCardsByEmployeeId((long) 160);
		if (cards.size() < 1) {
			map.put("cardMessage", "平台暂未绑定银行卡");
			return map;
		} else {
			map.put("card", cards.get(0));
		}
		return map;
	}

	// 账户提现
	@RequestMapping("/accountWithdraw")
	@ResponseBody
	public synchronized Map<String, Object> accountWithdraw(
			IndividualCapitalAccount accountFromLeading) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 1、入参判断
		if (accountFromLeading == null
				|| accountFromLeading.getId() == 0
				|| accountFromLeading.getId() == null
				|| accountFromLeading.getEmployeeId() == 0
				|| accountFromLeading.getEmployeeId() == null
				|| accountFromLeading.getFreezeNumber() == null
				|| accountFromLeading.getNumber() == null
				|| accountFromLeading.getTotalNumber() == null
				|| "0".equals(accountFromLeading.getWithdrawNumber())
				|| accountFromLeading.getWithdrawNumber() == null
				|| Integer.parseInt(accountFromLeading.getWithdrawNumber().trim()) < 10000) {
			map.put("opResult", "3");// 参数不足或提现金额小于10000
			return map;
		}

		// 2、判断登录用户
		if (accountFromLeading.getEmployeeId() != UserContext.getUser().getId()) {
			map.put("opResult", "1");// 非法操作，提现用户与登录用户不符
			return map;
		}
		// 后台查得的账户
		IndividualCapitalAccount accountFromBackground = individualCapitalAccountService
				.selectByEmployeeId(UserContext.getUser().getId());
		// 3、前后台账户判断
		if ((!accountFromBackground.getFreezeNumber().equals(
				accountFromLeading.getFreezeNumber()))
				|| (!accountFromBackground.getNumber().equals(
						accountFromLeading.getNumber()))
				|| (!accountFromBackground.getTotalNumber().equals(
						accountFromLeading.getTotalNumber()))) {
			map.put("opResult", "2");// 后台账户金额信息与前台传来的金额信息不符，提示用户刷新页面(从后台重新获取数据)
			return map;
		}

		// 4、可提现金额与提现金额判断
		if (Integer.parseInt(accountFromLeading.getWithdrawNumber()) > Integer
				.parseInt(accountFromBackground.getNumber())) {
			map.put("opResult", "4");// 提现金额大于该账户可提现金额，提示用户重新输入
			return map;
		}

		// 满足提现的条件时，开始提现操作（1、交易记录表（支出表插入数据）；2、帐户表）

		boolean recordResult = spendingRecordService.accountWithdraw(
				accountFromLeading, accountFromBackground);
		if (recordResult) {
			int withDrowNumber = Integer.parseInt(accountFromLeading
					.getWithdrawNumber());
			accountFromBackground
					.setNumber((Integer.parseInt(accountFromBackground
							.getNumber()) - withDrowNumber) + "");
			accountFromBackground
					.setTotalNumber((Integer.parseInt(accountFromBackground
							.getTotalNumber()) - withDrowNumber) + "");
			boolean accountResult = individualCapitalAccountService
					.updateByPrimaryKeySelective(accountFromBackground);
			if (accountResult) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "6");// 后台操作账户金额失败
				return map;
			}
		} else {
			map.put("opResult", "5");// 后台生成交易记录失败
			return map;
		}

		return map;
	}

	// 用户申请电子凭证
	@RequestMapping("/userApplicationForElectronicVoucher")
	@ResponseBody
	public Map<String, Object> userApplicationForElectronicVoucher(Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == 0 || id == null) {
			map.put("opResult", "3");// 参数为空
			return map;
		}

		SpendingRecord record = new SpendingRecord();
		record.setId(id);
		record.setPhase("00");
		try {
			boolean result = spendingRecordService
					.updateByPrimaryKeySelective(record);
			if (result) {
				map.put("opResult", "0");
			} else {
				map.put("opResult", "1");
			}
		} catch (Exception e) {
			map.put("opResult", "2");// 系统错误
			return map;
		}

		return map;
	}

	// 太美审核，且可以上传电子凭证
	// @RequestMapping("/checkTransactionRecord")
	// @ResponseBody
	public Map<String, Object> checkTransactionRecord(MultipartFile jpg,
			HttpServletRequest request, HttpServletResponse response, Long id,
			String phase) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id == 0 || TextUtil.isEmpty(phase)) {
			map.put("opResult", "3");// 参数为空
			return map;
		}

		String jpgName = jpg.getOriginalFilename();

		System.out
				.println("id:" + id + "phase:" + phase + "jpgName:" + jpgName);

		SpendingRecord record = new SpendingRecord();
		record.setId(id);
		record.setPhase(phase);
		if (!TextUtil.isEmpty(jpgName)) {
			try {
				record.setJpg(jpg.getBytes());
			} catch (IOException e) {
				map.put("opResult", "2");// 上传图片失败
				return map;
			}
		}
		record.setAuditEmployeeId(UserContext.getUser().getId());
		record.setAuditDate(DateUtil
				.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		try {
			boolean result = spendingRecordService
					.updateByPrimaryKeySelective(record);
			if (result) {
				map.put("opResult", "0");
			}
		} catch (Exception e) {
			map.put("opResult", "1");// 更新失败
			return map;
		}

		return map;
	}

	// 查看交易记录详情
	@RequestMapping("/seeTransactionRecordDetails")
	@ResponseBody
	public Map<String, Object> seeTransactionRecordDetails(Long id, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id == 0 || TextUtil.isEmpty(type)) {
			map.put("opResult", "3");// 参数为空
			return map;
		}

		TransactionRecord record = null;

		if ("02".equals(type.substring(0, 2))
				|| "03".equals(type.substring(0, 2))
				|| "06".equals(type.substring(0, 2))) {
			record = spendingRecordService.spendingTransactionRecordDetails(id);
			if (record == null) {
				map.put("opResult", "1");// 不存在
				return map;
			} else {
				ediTransactionRecords(record);
				map.put("record", record);
			}
		} else if ("01".equals(type.substring(0, 2))
				|| "04".equals(type.substring(0, 2))
				|| "05".equals(type.substring(0, 2))) {
			record = moneyIncomeRecordService
					.incomeTransactionRecordDetails(id);
			if (record == null) {
				map.put("opResult", "1");// 不存在
			} else {
				ediTransactionRecords(record);
				map.put("record", record);
			}

		}

		return map;
	}

	// 查看交易记录电子凭证
	@RequestMapping("/seeTransactionRecordJpg")
	@ResponseBody
	public Map<String, Object> seeTransactionRecordJpg(
			ServletResponse response, Long id, String type) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (id == null || id == 0 || TextUtil.isEmpty(type)) {
			map.put("opResult", "3");// 参数为空
			return map;
		}

		TransactionRecord record = null;

		if ("02".equals(type.substring(0, 2))
				|| "03".equals(type.substring(0, 2))
				|| "06".equals(type.substring(0, 2))) {
			record = spendingRecordService.spendingTransactionRecordJpg(id);
			if (record == null) {
				map.put("opResult", "1");// 不存在
				return map;
			}
		} else if ("01".equals(type.substring(0, 2))
				|| "04".equals(type.substring(0, 2))
				|| "05".equals(type.substring(0, 2))) {
			record = moneyIncomeRecordService.incomeTransactionRecordJpg(id);
			if (record == null) {
				map.put("opResult", "1");// 不存在
			}

		}
		int num = 1024;
		OutputStream os = null;
		try {
			os = response.getOutputStream();
			InputStream inputStream = new ByteArrayInputStream(record.getJpg());

			byte buf[] = new byte[1024];
			while ((num = inputStream.read(buf)) != -1) {
				os.write(buf, 0, num);
			}
			if (inputStream != null) {
				inputStream.close();
			}
			if (os != null) {
				os.flush();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			map.put("opResult", "2");//系统发生异常
		}
		return map;
	}

}
