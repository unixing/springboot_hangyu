package org.ldd.ssm.crm.domain;
/**
 * 销售报表实体(季、年报)
 * @Title:YearSalesReport 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 下午5:07:54
 */
public class YearSalesReport {
     private String year;	//年
     private String qury; //季
     private String month;//月份
     private String bc;//班次
     private String yys;//月营收
     private String jbxsys;//均班小时营收
     private String zrs;//总人数
     private String jbrs;//均班人数
     private String jbkzl;//均班客座率
     private String zglsr;//座公里收入
     private String zbt;//总补贴
     private String jbbt;//均班补贴
     private String bthdlf;//补贴含代理费
     private String siteNum;//平均座位数
     private String talTime;//总飞时间
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * @return the qury
	 */
	public String getQury() {
		return qury;
	}
	/**
	 * @param qury the qury to set
	 */
	public void setQury(String qury) {
		this.qury = qury;
	}
	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}
	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}
	/**
	 * @return the bc
	 */
	public String getBc() {
		return bc;
	}
	/**
	 * @param bc the bc to set
	 */
	public void setBc(String bc) {
		this.bc = bc;
	}
	/**
	 * @return the yys
	 */
	public String getYys() {
		return yys;
	}
	/**
	 * @param yys the yys to set
	 */
	public void setYys(String yys) {
		this.yys = yys;
	}
	/**
	 * @return the jbxsys
	 */
	public String getJbxsys() {
		return jbxsys;
	}
	/**
	 * @param jbxsys the jbxsys to set
	 */
	public void setJbxsys(String jbxsys) {
		this.jbxsys = jbxsys;
	}
	/**
	 * @return the zrs
	 */
	public String getZrs() {
		return zrs;
	}
	/**
	 * @param zrs the zrs to set
	 */
	public void setZrs(String zrs) {
		this.zrs = zrs;
	}
	/**
	 * @return the jbrs
	 */
	public String getJbrs() {
		return jbrs;
	}
	/**
	 * @param jbrs the jbrs to set
	 */
	public void setJbrs(String jbrs) {
		this.jbrs = jbrs;
	}
	/**
	 * @return the jbkzl
	 */
	public String getJbkzl() {
		return jbkzl;
	}
	/**
	 * @param jbkzl the jbkzl to set
	 */
	public void setJbkzl(String jbkzl) {
		this.jbkzl = jbkzl;
	}
	/**
	 * @return the zglsr
	 */
	public String getZglsr() {
		return zglsr;
	}
	/**
	 * @param zglsr the zglsr to set
	 */
	public void setZglsr(String zglsr) {
		this.zglsr = zglsr;
	}
	/**
	 * @return the zbt
	 */
	public String getZbt() {
		return zbt;
	}
	/**
	 * @param zbt the zbt to set
	 */
	public void setZbt(String zbt) {
		this.zbt = zbt;
	}
	/**
	 * @return the jbbt
	 */
	public String getJbbt() {
		return jbbt;
	}
	/**
	 * @param jbbt the jbbt to set
	 */
	public void setJbbt(String jbbt) {
		this.jbbt = jbbt;
	}
	/**
	 * @return the bthdlf
	 */
	public String getBthdlf() {
		return bthdlf;
	}
	/**
	 * @param bthdlf the bthdlf to set
	 */
	public void setBthdlf(String bthdlf) {
		this.bthdlf = bthdlf;
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
		return "YearSalesReport [year=" + year + ", qury=" + qury + ", month="
				+ month + ", bc=" + bc + ", yys=" + yys + ", jbxsys=" + jbxsys
				+ ", zrs=" + zrs + ", jbrs=" + jbrs + ", jbkzl=" + jbkzl
				+ ", zglsr=" + zglsr + ", zbt=" + zbt + ", jbbt=" + jbbt
				+ ", bthdlf=" + bthdlf + ", siteNum=" + siteNum + ", talTime="
				+ talTime + "]";
	}
	
     
}
