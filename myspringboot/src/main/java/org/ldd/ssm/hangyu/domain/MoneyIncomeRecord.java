package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;
import java.util.Arrays;

public class MoneyIncomeRecord implements Serializable {
	
    private Long id;

    private Long accountId;//个人资金账户

    private Long toAccountId;//目标账户id

    private String number;//支入金额
    
    private String date;//操作日期
    
    private Long demandId;//本条交易记录关联的需求
    
    private String type;//交易类型:01-充值、02-提现、0301-冻结(查看意向)、0302-冻结(响应需求)、0401-解冻(撤回意向)、0402-解冻(需求下架)、05-转入、06-支付
    
    private String phase;//交易阶段:0:发起,1:审核,2:成功
    
    private String serialNumber;//交易流水号(平台自定义)生成规则：YYYYMMDD+操作代码+5位自增流水号操作代码：01-充值、02-提现、03-冻结、04-解冻、05-转入、06-支付EG：201712190100001
    
    private byte[] jpg;//电子凭证图片
    
    private Long auditEmployeeId;//审核人id
    
    private String auditDate;//审核日期

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public byte[] getJpg() {
		return jpg;
	}

	public void setJpg(byte[] jpg) {
		this.jpg = jpg;
	}

	public Long getAuditEmployeeId() {
		return auditEmployeeId;
	}

	public void setAuditEmployeeId(Long auditEmployeeId) {
		this.auditEmployeeId = auditEmployeeId;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	@Override
	public String toString() {
		return "MoneyIncomeRecord [id=" + id + ", accountId=" + accountId
				+ ", toAccountId=" + toAccountId + ", number=" + number
				+ ", date=" + date + ", demandId=" + demandId + ", type="
				+ type + ", phase=" + phase + ", serialNumber=" + serialNumber
				+ ", jpg=" + Arrays.toString(jpg) + ", auditEmployeeId="
				+ auditEmployeeId + ", auditDate=" + auditDate + "]";
	}

	

}