package org.ldd.ssm.crm.domain;
/**
 * 共飞对比对象
 * @Title:TotalFly 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-12 上午9:43:32
 */
public class TotalFly {
	
	private String hangduan;//  航段
	private String startDate; // 年份
	private String EndDate; // 月份
	private String Flt_Nbr ; // 航班号
	private double Flt_Nbr_num ; // 航班数量
	private double Pgs_Per_Cls_num ; // 航班客量
	private double Set_Ktr_Ine_num; // 座公里收入
	private double Tal_Nbr_num; // 每班营收
	private double kzl; // 客座率
	private double Tal_Nbr_Set_num ; // 每班座位
	private double Pgs_Per_num; // 每班旅客
	private double Grp_Nbr_num ; // 每班团队
	private double jb;//均班次
	private double jbzw;//均班座位
	private double jblk;//均班旅客
	private double jbtd;//均班团队
	private double jbkzl;//均班客座率
	private double jbys;//均班营收
	private double jbzglsr;//均班座公里收入
	private double jbkl;//客量平均
	private String name;
	private double value;//客量占比
	private double yBFare;//YB运价
	private double jbtdsr;//均班团队收入
	private double tdzk;//团队折扣
	private double skzk;//散客折扣
	private double pjzk;//平均折扣
	private double zglsr;//座公里收入
	
	public TotalFly() {
		super();
		Flt_Nbr = "";
		Flt_Nbr_num = 0;
		Pgs_Per_Cls_num = 0;
		Set_Ktr_Ine_num = 0;
		Tal_Nbr_num = 0;
		Tal_Nbr_Set_num = 0;
		Pgs_Per_num = 0;
		Grp_Nbr_num = 0;
		kzl = 0;
		jb = 0;
		jbzw = 0;
		jblk = 0;
		jbtd = 0;
		jbkzl = 0;
		jbys = 0;
		jbzglsr = 0;
		jbkl = 0;
		yBFare = 0;
		jbtdsr = 0;
		tdzk = 0;
		skzk = 0;
		pjzk = 0;
		zglsr = 0;
	}


	/**
	 * @return the hangduan
	 */
	public String getHangduan() {
		return hangduan;
	}


	/**
	 * @param hangduan the hangduan to set
	 */
	public void setHangduan(String hangduan) {
		this.hangduan = hangduan;
	}


	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return EndDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		EndDate = endDate;
	}


	/**
	 * @return the flt_Nbr
	 */
	public String getFlt_Nbr() {
		return Flt_Nbr;
	}


	/**
	 * @param flt_Nbr the flt_Nbr to set
	 */
	public void setFlt_Nbr(String flt_Nbr) {
		Flt_Nbr = flt_Nbr;
	}


	/**
	 * @return the flt_Nbr_num
	 */
	public double getFlt_Nbr_num() {
		return Flt_Nbr_num;
	}


	/**
	 * @param flt_Nbr_num the flt_Nbr_num to set
	 */
	public void setFlt_Nbr_num(double flt_Nbr_num) {
		Flt_Nbr_num = flt_Nbr_num;
	}


	/**
	 * @return the pgs_Per_Cls_num
	 */
	public double getPgs_Per_Cls_num() {
		return Pgs_Per_Cls_num;
	}


	/**
	 * @param pgs_Per_Cls_num the pgs_Per_Cls_num to set
	 */
	public void setPgs_Per_Cls_num(double pgs_Per_Cls_num) {
		Pgs_Per_Cls_num = pgs_Per_Cls_num;
	}


	/**
	 * @return the set_Ktr_Ine_num
	 */
	public double getSet_Ktr_Ine_num() {
		return Set_Ktr_Ine_num;
	}


	/**
	 * @param set_Ktr_Ine_num the set_Ktr_Ine_num to set
	 */
	public void setSet_Ktr_Ine_num(double set_Ktr_Ine_num) {
		Set_Ktr_Ine_num = set_Ktr_Ine_num;
	}


	/**
	 * @return the tal_Nbr_num
	 */
	public double getTal_Nbr_num() {
		return Tal_Nbr_num;
	}


	/**
	 * @param tal_Nbr_num the tal_Nbr_num to set
	 */
	public void setTal_Nbr_num(double tal_Nbr_num) {
		Tal_Nbr_num = tal_Nbr_num;
	}


	/**
	 * @return the kzl
	 */
	public double getKzl() {
		return kzl;
	}


	/**
	 * @param kzl the kzl to set
	 */
	public void setKzl(double kzl) {
		this.kzl = kzl;
	}


	/**
	 * @return the tal_Nbr_Set_num
	 */
	public double getTal_Nbr_Set_num() {
		return Tal_Nbr_Set_num;
	}


	/**
	 * @param tal_Nbr_Set_num the tal_Nbr_Set_num to set
	 */
	public void setTal_Nbr_Set_num(double tal_Nbr_Set_num) {
		Tal_Nbr_Set_num = tal_Nbr_Set_num;
	}


	/**
	 * @return the pgs_Per_num
	 */
	public double getPgs_Per_num() {
		return Pgs_Per_num;
	}


	/**
	 * @param pgs_Per_num the pgs_Per_num to set
	 */
	public void setPgs_Per_num(double pgs_Per_num) {
		Pgs_Per_num = pgs_Per_num;
	}


	/**
	 * @return the grp_Nbr_num
	 */
	public double getGrp_Nbr_num() {
		return Grp_Nbr_num;
	}


	/**
	 * @param grp_Nbr_num the grp_Nbr_num to set
	 */
	public void setGrp_Nbr_num(double grp_Nbr_num) {
		Grp_Nbr_num = grp_Nbr_num;
	}


	/**
	 * @return the jb
	 */
	public double getJb() {
		return jb;
	}


	/**
	 * @param jb the jb to set
	 */
	public void setJb(double jb) {
		this.jb = jb;
	}


	/**
	 * @return the jbzw
	 */
	public double getJbzw() {
		return jbzw;
	}


	/**
	 * @param jbzw the jbzw to set
	 */
	public void setJbzw(double jbzw) {
		this.jbzw = jbzw;
	}


	/**
	 * @return the jblk
	 */
	public double getJblk() {
		return jblk;
	}


	/**
	 * @param jblk the jblk to set
	 */
	public void setJblk(double jblk) {
		this.jblk = jblk;
	}


	/**
	 * @return the jbtd
	 */
	public double getJbtd() {
		return jbtd;
	}


	/**
	 * @param jbtd the jbtd to set
	 */
	public void setJbtd(double jbtd) {
		this.jbtd = jbtd;
	}


	/**
	 * @return the jbkzl
	 */
	public double getJbkzl() {
		return jbkzl;
	}


	/**
	 * @param jbkzl the jbkzl to set
	 */
	public void setJbkzl(double jbkzl) {
		this.jbkzl = jbkzl;
	}


	/**
	 * @return the jbys
	 */
	public double getJbys() {
		return jbys;
	}


	/**
	 * @param jbys the jbys to set
	 */
	public void setJbys(double jbys) {
		this.jbys = jbys;
	}


	/**
	 * @return the jbzglsr
	 */
	public double getJbzglsr() {
		return jbzglsr;
	}


	/**
	 * @param jbzglsr the jbzglsr to set
	 */
	public void setJbzglsr(double jbzglsr) {
		this.jbzglsr = jbzglsr;
	}


	/**
	 * @return the jbkl
	 */
	public double getJbkl() {
		return jbkl;
	}


	/**
	 * @param jbkl the jbkl to set
	 */
	public void setJbkl(double jbkl) {
		this.jbkl = jbkl;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}


	/**
	 * @return the yBFare
	 */
	public double getyBFare() {
		return yBFare;
	}


	/**
	 * @param yBFare the yBFare to set
	 */
	public void setyBFare(double yBFare) {
		this.yBFare = yBFare;
	}


	/**
	 * @return the jbtdsr
	 */
	public double getJbtdsr() {
		return jbtdsr;
	}


	/**
	 * @param jbtdsr the jbtdsr to set
	 */
	public void setJbtdsr(double jbtdsr) {
		this.jbtdsr = jbtdsr;
	}


	/**
	 * @return the tdzk
	 */
	public double getTdzk() {
		return tdzk;
	}


	/**
	 * @param tdzk the tdzk to set
	 */
	public void setTdzk(double tdzk) {
		this.tdzk = tdzk;
	}


	/**
	 * @return the skzk
	 */
	public double getSkzk() {
		return skzk;
	}


	/**
	 * @param skzk the skzk to set
	 */
	public void setSkzk(double skzk) {
		this.skzk = skzk;
	}


	/**
	 * @return the pjzk
	 */
	public double getPjzk() {
		return pjzk;
	}


	/**
	 * @param pjzk the pjzk to set
	 */
	public void setPjzk(double pjzk) {
		this.pjzk = pjzk;
	}


	/**
	 * @return the zglsr
	 */
	public double getZglsr() {
		return zglsr;
	}


	/**
	 * @param zglsr the zglsr to set
	 */
	public void setZglsr(double zglsr) {
		this.zglsr = zglsr;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TotalFly [hangduan=" + hangduan + ", startDate=" + startDate
				+ ", EndDate=" + EndDate + ", Flt_Nbr=" + Flt_Nbr
				+ ", Flt_Nbr_num=" + Flt_Nbr_num + ", Pgs_Per_Cls_num="
				+ Pgs_Per_Cls_num + ", Set_Ktr_Ine_num=" + Set_Ktr_Ine_num
				+ ", Tal_Nbr_num=" + Tal_Nbr_num + ", kzl=" + kzl
				+ ", Tal_Nbr_Set_num=" + Tal_Nbr_Set_num + ", Pgs_Per_num="
				+ Pgs_Per_num + ", Grp_Nbr_num=" + Grp_Nbr_num + ", jb=" + jb
				+ ", jbzw=" + jbzw + ", jblk=" + jblk + ", jbtd=" + jbtd
				+ ", jbkzl=" + jbkzl + ", jbys=" + jbys + ", jbzglsr="
				+ jbzglsr + ", jbkl=" + jbkl + ", name=" + name + ", value="
				+ value + ", yBFare=" + yBFare + ", jbtdsr=" + jbtdsr
				+ ", tdzk=" + tdzk + ", skzk=" + skzk + ", pjzk=" + pjzk
				+ ", zglsr=" + zglsr + "]";
	}



}
