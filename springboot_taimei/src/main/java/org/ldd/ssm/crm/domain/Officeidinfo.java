package org.ldd.ssm.crm.domain;
/**
 * 出票人信息
 */
public class Officeidinfo {
	private String ofc_Cde; // 'office号 ',
	private String ofc_Nm; // 'office公司 ',
	private String pho_No; // 'office号对应电话 ',
	private String address; // '地址 ',
	private String qq; // 'QQ ',
	private String tkt_Ads; // '出票地址 ',
	private String tkt_per; // '出票人 ',
	public String getOfc_Cde() {
		return ofc_Cde;
	}
	public void setOfc_Cde(String ofc_Cde) {
		this.ofc_Cde = ofc_Cde;
	}
	public String getOfc_Nm() {
		return ofc_Nm;
	}
	public void setOfc_Nm(String ofc_Nm) {
		this.ofc_Nm = ofc_Nm;
	}
	public String getPho_No() {
		return pho_No;
	}
	public void setPho_No(String pho_No) {
		this.pho_No = pho_No;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getTkt_Ads() {
		return tkt_Ads;
	}
	public void setTkt_Ads(String tkt_Ads) {
		this.tkt_Ads = tkt_Ads;
	}
	public String getTkt_per() {
		return tkt_per;
	}
	public void setTkt_per(String tkt_per) {
		this.tkt_per = tkt_per;
	}
	@Override
	public String toString() {
		return "Officeidinfo [ofc_Cde=" + ofc_Cde + ", ofc_Nm=" + ofc_Nm
				+ ", pho_No=" + pho_No + ", address=" + address + ", qq=" + qq
				+ ", tkt_Ads=" + tkt_Ads + ", tkt_per=" + tkt_per + "]";
	}
	
}
