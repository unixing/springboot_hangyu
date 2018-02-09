package org.ldd.ssm.crm.domain;
/**
 * 折扣表
 * @author Taimei
 *
 */
public class Airdiscount {
	private String dct_Pge;//折扣人数
	private String dct_Chr;//折扣字符
	private String dct_Ppt;//折扣比例
	public String getDct_Pge() {
		return dct_Pge;
	}
	public void setDct_Pge(String dct_Pge) {
		this.dct_Pge = dct_Pge;
	}
	public String getDct_Chr() {
		return dct_Chr;
	}
	public void setDct_Chr(String dct_Chr) {
		this.dct_Chr = dct_Chr;
	}
	public String getDct_Ppt() {
		return dct_Ppt;
	}
	public void setDct_Ppt(String dct_Ppt) {
		this.dct_Ppt = dct_Ppt;
	}
	@Override
	public String toString() {
		return "Airdiscount [dct_Pge=" + dct_Pge + ", dct_Chr=" + dct_Chr
				+ ", dct_Ppt=" + dct_Ppt + "]";
	}
}
