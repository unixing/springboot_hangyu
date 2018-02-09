package org.ldd.ssm.hangyu.query;

import java.io.Serializable;
import java.util.Arrays;

public class TransactionRecord implements Serializable {
	
	private Long id;//本条记录的id
	
	private String date;//交易日期简略
	
	private String dateComplete;//交易日期完整
	
	private String type;//交易类型:01-充值、02-提现、0301-冻结(查看意向)、0302-冻结(响应需求)、0401-解冻(撤回意向)、0402-解冻(需求下架)、05-转入、06-支付
	
	private String discription1;//显示的时候描述//响应、确认
	
	private String discription2;//显示的时候描述//响应、确认
	
	private String number;//交易金额单位元
	
	private Long demandId;//关联的需求id
	
	private String demandType;//需求类型代码
	
	private String demandTypeStr;//需求类型汉字
	
	private String demandTitle;//关联需求的标题
	
	private String demandProgress;//关联需求的状态，用于"解冻"这种情况时的显示
	
	private String phase;//交易阶段:0:发起,1:审核,2:成功
	
	private String serialNumber;//交易流水号
	
	private boolean wetherJpg;//是否有图片true:有；false：无
	
	private byte[] jpg;//电子凭证
	
	public String getDemandTypeStr() {
		return demandTypeStr;
	}

	public void setDemandTypeStr(String demandTypeStr) {
		this.demandTypeStr = demandTypeStr;
	}

	public byte[] getJpg() {
		return jpg;
	}

	public void setJpg(byte[] jpg) {
		this.jpg = jpg;
	}
	
	public boolean getWetherJpg() {
		return wetherJpg;
	}

	public void setWetherJpg(boolean wetherJpg) {
		this.wetherJpg = wetherJpg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getDemandId() {
		return demandId;
	}

	public void setDemandId(Long demandId) {
		this.demandId = demandId;
	}

	public String getDemandTitle() {
		return demandTitle;
	}

	public void setDemandTitle(String demandTitle) {
		this.demandTitle = demandTitle;
	}

	public String getDemandProgress() {
		return demandProgress;
	}

	public void setDemandProgress(String demandProgress) {
		this.demandProgress = demandProgress;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public String getDiscription1() {
		return discription1;
	}

	public void setDiscription1(String discription1) {
		this.discription1 = discription1;
	}

	public String getDiscription2() {
		return discription2;
	}

	public void setDiscription2(String discription2) {
		this.discription2 = discription2;
	}

	
	public String getDateComplete() {
		return dateComplete;
	}

	public void setDateComplete(String dateComplete) {
		this.dateComplete = dateComplete;
	}

	public String getDemandType() {
		return demandType;
	}

	public void setDemandType(String demandType) {
		this.demandType = demandType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "TransactionRecord [id=" + id + ", date=" + date
				+ ", dateComplete=" + dateComplete + ", type=" + type
				+ ", discription1=" + discription1 + ", discription2="
				+ discription2 + ", number=" + number + ", demandId="
				+ demandId + ", demandType=" + demandType + ", demandTypeStr="
				+ demandTypeStr + ", demandTitle=" + demandTitle
				+ ", demandProgress=" + demandProgress + ", phase=" + phase
				+ ", serialNumber=" + serialNumber + ", wetherJpg=" + wetherJpg
				+ ", jpg=" + Arrays.toString(jpg) + "]";
	}

	
	
}
