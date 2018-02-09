package org.ldd.ssm.crm.domain;
/**
 * 销售报表实体(周、月报)
 * @Title:SalesReport 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 下午5:07:54
 */
public class OtherSalesReport {
     private String date;	//航班日期
     private String weekly; //星期
     private String hbys;//航班营收
     private String xsys;//小时营收
     private String rs;//人数
     private String sk;//散客
     private String td;//团队
     private String kzl;//客座率
     private String pjzk;//平均折扣
     private String zgl;//座公里
     private String mbdcl;//目标达成率
     private String bt;//补贴
     private String bthdl;//补贴含代理
     private String siteNum;//平均座位数
     private String talTime;//总飞时间
	
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the weekly
	 */
	public String getWeekly() {
		return weekly;
	}
	/**
	 * @param weekly the weekly to set
	 */
	public void setWeekly(String weekly) {
		this.weekly = weekly;
	}
	/**
	 * @return the hbys
	 */
	public String getHbys() {
		return hbys;
	}
	/**
	 * @param hbys the hbys to set
	 */
	public void setHbys(String hbys) {
		this.hbys = hbys;
	}
	/**
	 * @return the xsys
	 */
	public String getXsys() {
		return xsys;
	}
	/**
	 * @param xsys the xsys to set
	 */
	public void setXsys(String xsys) {
		this.xsys = xsys;
	}
	/**
	 * @return the rs
	 */
	public String getRs() {
		return rs;
	}
	/**
	 * @param rs the rs to set
	 */
	public void setRs(String rs) {
		this.rs = rs;
	}
	/**
	 * @return the sk
	 */
	public String getSk() {
		return sk;
	}
	/**
	 * @param sk the sk to set
	 */
	public void setSk(String sk) {
		this.sk = sk;
	}
	/**
	 * @return the td
	 */
	public String getTd() {
		return td;
	}
	/**
	 * @param td the td to set
	 */
	public void setTd(String td) {
		this.td = td;
	}
	/**
	 * @return the kzl
	 */
	public String getKzl() {
		return kzl;
	}
	/**
	 * @param kzl the kzl to set
	 */
	public void setKzl(String kzl) {
		this.kzl = kzl;
	}
	/**
	 * @return the pjzk
	 */
	public String getPjzk() {
		return pjzk;
	}
	/**
	 * @param pjzk the pjzk to set
	 */
	public void setPjzk(String pjzk) {
		this.pjzk = pjzk;
	}
	/**
	 * @return the zgl
	 */
	public String getZgl() {
		return zgl;
	}
	/**
	 * @param zgl the zgl to set
	 */
	public void setZgl(String zgl) {
		this.zgl = zgl;
	}
	/**
	 * @return the mbdcl
	 */
	public String getMbdcl() {
		return mbdcl;
	}
	/**
	 * @param mbdcl the mbdcl to set
	 */
	public void setMbdcl(String mbdcl) {
		this.mbdcl = mbdcl;
	}
	/**
	 * @return the bt
	 */
	public String getBt() {
		return bt;
	}
	/**
	 * @param bt the bt to set
	 */
	public void setBt(String bt) {
		this.bt = bt;
	}
	/**
	 * @return the bthdl
	 */
	public String getBthdl() {
		return bthdl;
	}
	/**
	 * @param bthdl the bthdl to set
	 */
	public void setBthdl(String bthdl) {
		this.bthdl = bthdl;
	}
	/**
	 * @return the siteNum
	 */
	public String getSiteNum() {
		return siteNum;
	}
	/**
	 * @param siteNum the siteNum to set
	 */
	public void setSiteNum(String siteNum) {
		this.siteNum = siteNum;
	}
	/**
	 * @return the talTime
	 */
	public String getTalTime() {
		return talTime;
	}
	/**
	 * @param talTime the talTime to set
	 */
	public void setTalTime(String talTime) {
		this.talTime = talTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OtherSalesReport [date=" + date + ", weekly=" + weekly
				+ ", hbys=" + hbys + ", xsys=" + xsys + ", rs=" + rs + ", sk="
				+ sk + ", td=" + td + ", kzl=" + kzl + ", pjzk=" + pjzk
				+ ", zgl=" + zgl + ", mbdcl=" + mbdcl + ", bt=" + bt
				+ ", bthdl=" + bthdl + ", siteNum=" + siteNum + ", talTime="
				+ talTime + "]";
	}
     
}
