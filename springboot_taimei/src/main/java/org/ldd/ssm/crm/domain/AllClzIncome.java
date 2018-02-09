package org.ldd.ssm.crm.domain;
/**
 * 均班收益的前台数据接收对象
 * @author Taimei
 *
 */
public class AllClzIncome {
     private String date;	//航班日期
     private String airPort;	//航段
     private String yPrice;	//Y舱价格
     private String cutPriceTeam;	//切位团队价格
     private String two_Tak_Ppt;	//150%
     private String ful_Pce_Ppt;	//100%
     private String nne_Dnt_Ppt;	//90%
     private String eht_Five_Dnt_Ppt;	//85%
     private String eht_Dnt_Ppt;	//80%
     private String sen_Five_Dnt_Ppt;	//75%
     private String sen_Dnt_Ppt;	//70%
     private String six_Five_Dnt_Ppt;	//65%
     private String six_Dnt_Ppt;	//60%
     private String fve_Fve_Dnt_Ppt;	//55%
     private String fve_Dnt_Ppt;	//50%
     private String fur_Fve_Dnt_Ppt;	//45%
     private String fur_Dnt_Ppt;	//40%
     private String thr_Fve_Dnt_Ppt;	//35%
     private String thr_Dnt_Ppt;	//30%
     private String two_Fve_Dnt_Ppt;	//25%
     private String two_Dnt_Ppt;	//20%
     private String sal_Tak_Ppt;	//特殊舱位
     private String grp_Nbr;	//团队人数
     private String pgs_Per_Cls;	//散人和团队人数总和
     private String wak_tol_Nbr;	//散客总人数
     private String wak_tol_Ine;	//散客总收入
     private String grp_Ine;	//团队总收入
     private String avg_Dct;	//平均折扣
     private String tal_Nbr_Set;//座位数
     private String pjTime;//平均飞行小时
	public String getTal_Nbr_Set() {
		return tal_Nbr_Set;
	}
	public void setTal_Nbr_Set(String tal_Nbr_Set) {
		this.tal_Nbr_Set = tal_Nbr_Set;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAirPort() {
		return airPort;
	}
	public void setAirPort(String airPort) {
		this.airPort = airPort;
	}
	public String getyPrice() {
		return yPrice;
	}
	public void setyPrice(String yPrice) {
		this.yPrice = yPrice;
	}
	public String getCutPriceTeam() {
		return cutPriceTeam;
	}
	public void setCutPriceTeam(String cutPriceTeam) {
		this.cutPriceTeam = cutPriceTeam;
	}
	public String getTwo_Tak_Ppt() {
		return two_Tak_Ppt;
	}
	public void setTwo_Tak_Ppt(String two_Tak_Ppt) {
		this.two_Tak_Ppt = two_Tak_Ppt;
	}
	public String getFul_Pce_Ppt() {
		return ful_Pce_Ppt;
	}
	public void setFul_Pce_Ppt(String ful_Pce_Ppt) {
		this.ful_Pce_Ppt = ful_Pce_Ppt;
	}
	public String getNne_Dnt_Ppt() {
		return nne_Dnt_Ppt;
	}
	public void setNne_Dnt_Ppt(String nne_Dnt_Ppt) {
		this.nne_Dnt_Ppt = nne_Dnt_Ppt;
	}
	public String getEht_Five_Dnt_Ppt() {
		return eht_Five_Dnt_Ppt;
	}
	public void setEht_Five_Dnt_Ppt(String eht_Five_Dnt_Ppt) {
		this.eht_Five_Dnt_Ppt = eht_Five_Dnt_Ppt;
	}
	public String getEht_Dnt_Ppt() {
		return eht_Dnt_Ppt;
	}
	public void setEht_Dnt_Ppt(String eht_Dnt_Ppt) {
		this.eht_Dnt_Ppt = eht_Dnt_Ppt;
	}
	public String getSen_Five_Dnt_Ppt() {
		return sen_Five_Dnt_Ppt;
	}
	public void setSen_Five_Dnt_Ppt(String sen_Five_Dnt_Ppt) {
		this.sen_Five_Dnt_Ppt = sen_Five_Dnt_Ppt;
	}
	public String getSen_Dnt_Ppt() {
		return sen_Dnt_Ppt;
	}
	public void setSen_Dnt_Ppt(String sen_Dnt_Ppt) {
		this.sen_Dnt_Ppt = sen_Dnt_Ppt;
	}
	public String getSix_Five_Dnt_Ppt() {
		return six_Five_Dnt_Ppt;
	}
	public void setSix_Five_Dnt_Ppt(String six_Five_Dnt_Ppt) {
		this.six_Five_Dnt_Ppt = six_Five_Dnt_Ppt;
	}
	public String getSix_Dnt_Ppt() {
		return six_Dnt_Ppt;
	}
	public void setSix_Dnt_Ppt(String six_Dnt_Ppt) {
		this.six_Dnt_Ppt = six_Dnt_Ppt;
	}
	public String getFve_Fve_Dnt_Ppt() {
		return fve_Fve_Dnt_Ppt;
	}
	public void setFve_Fve_Dnt_Ppt(String fve_Fve_Dnt_Ppt) {
		this.fve_Fve_Dnt_Ppt = fve_Fve_Dnt_Ppt;
	}
	public String getFve_Dnt_Ppt() {
		return fve_Dnt_Ppt;
	}
	public void setFve_Dnt_Ppt(String fve_Dnt_Ppt) {
		this.fve_Dnt_Ppt = fve_Dnt_Ppt;
	}
	public String getFur_Fve_Dnt_Ppt() {
		return fur_Fve_Dnt_Ppt;
	}
	public void setFur_Fve_Dnt_Ppt(String fur_Fve_Dnt_Ppt) {
		this.fur_Fve_Dnt_Ppt = fur_Fve_Dnt_Ppt;
	}
	public String getFur_Dnt_Ppt() {
		return fur_Dnt_Ppt;
	}
	public void setFur_Dnt_Ppt(String fur_Dnt_Ppt) {
		this.fur_Dnt_Ppt = fur_Dnt_Ppt;
	}
	public String getThr_Fve_Dnt_Ppt() {
		return thr_Fve_Dnt_Ppt;
	}
	public void setThr_Fve_Dnt_Ppt(String thr_Fve_Dnt_Ppt) {
		this.thr_Fve_Dnt_Ppt = thr_Fve_Dnt_Ppt;
	}
	public String getThr_Dnt_Ppt() {
		return thr_Dnt_Ppt;
	}
	public void setThr_Dnt_Ppt(String thr_Dnt_Ppt) {
		this.thr_Dnt_Ppt = thr_Dnt_Ppt;
	}
	public String getTwo_Fve_Dnt_Ppt() {
		return two_Fve_Dnt_Ppt;
	}
	public void setTwo_Fve_Dnt_Ppt(String two_Fve_Dnt_Ppt) {
		this.two_Fve_Dnt_Ppt = two_Fve_Dnt_Ppt;
	}
	public String getTwo_Dnt_Ppt() {
		return two_Dnt_Ppt;
	}
	public void setTwo_Dnt_Ppt(String two_Dnt_Ppt) {
		this.two_Dnt_Ppt = two_Dnt_Ppt;
	}
	public String getSal_Tak_Ppt() {
		return sal_Tak_Ppt;
	}
	public void setSal_Tak_Ppt(String sal_Tak_Ppt) {
		this.sal_Tak_Ppt = sal_Tak_Ppt;
	}
	public String getGrp_Nbr() {
		return grp_Nbr;
	}
	public void setGrp_Nbr(String grp_Nbr) {
		this.grp_Nbr = grp_Nbr;
	}
	
	public String getPgs_Per_Cls() {
		return pgs_Per_Cls;
	}
	public void setPgs_Per_Cls(String pgs_Per_Cls) {
		this.pgs_Per_Cls = pgs_Per_Cls;
	}
	public String getWak_tol_Nbr() {
		return wak_tol_Nbr;
	}
	public void setWak_tol_Nbr(String wak_tol_Nbr) {
		this.wak_tol_Nbr = wak_tol_Nbr;
	}
	public String getWak_tol_Ine() {
		return wak_tol_Ine;
	}
	public void setWak_tol_Ine(String wak_tol_Ine) {
		this.wak_tol_Ine = wak_tol_Ine;
	}
	public String getGrp_Ine() {
		return grp_Ine;
	}
	public void setGrp_Ine(String grp_Ine) {
		this.grp_Ine = grp_Ine;
	}
	public String getAvg_Dct() {
		return avg_Dct;
	}
	public void setAvg_Dct(String avg_Dct) {
		this.avg_Dct = avg_Dct;
	}
	/**
	 * @return the pjTime
	 */
	public String getPjTime() {
		return pjTime;
	}
	/**
	 * @param pjTime the pjTime to set
	 */
	public void setPjTime(String pjTime) {
		this.pjTime = pjTime;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AllClzIncome [date=" + date + ", airPort=" + airPort
				+ ", yPrice=" + yPrice + ", cutPriceTeam=" + cutPriceTeam
				+ ", two_Tak_Ppt=" + two_Tak_Ppt + ", ful_Pce_Ppt="
				+ ful_Pce_Ppt + ", nne_Dnt_Ppt=" + nne_Dnt_Ppt
				+ ", eht_Five_Dnt_Ppt=" + eht_Five_Dnt_Ppt + ", eht_Dnt_Ppt="
				+ eht_Dnt_Ppt + ", sen_Five_Dnt_Ppt=" + sen_Five_Dnt_Ppt
				+ ", sen_Dnt_Ppt=" + sen_Dnt_Ppt + ", six_Five_Dnt_Ppt="
				+ six_Five_Dnt_Ppt + ", six_Dnt_Ppt=" + six_Dnt_Ppt
				+ ", fve_Fve_Dnt_Ppt=" + fve_Fve_Dnt_Ppt + ", fve_Dnt_Ppt="
				+ fve_Dnt_Ppt + ", fur_Fve_Dnt_Ppt=" + fur_Fve_Dnt_Ppt
				+ ", fur_Dnt_Ppt=" + fur_Dnt_Ppt + ", thr_Fve_Dnt_Ppt="
				+ thr_Fve_Dnt_Ppt + ", thr_Dnt_Ppt=" + thr_Dnt_Ppt
				+ ", two_Fve_Dnt_Ppt=" + two_Fve_Dnt_Ppt + ", two_Dnt_Ppt="
				+ two_Dnt_Ppt + ", sal_Tak_Ppt=" + sal_Tak_Ppt + ", grp_Nbr="
				+ grp_Nbr + ", pgs_Per_Cls=" + pgs_Per_Cls + ", wak_tol_Nbr="
				+ wak_tol_Nbr + ", wak_tol_Ine=" + wak_tol_Ine + ", grp_Ine="
				+ grp_Ine + ", avg_Dct=" + avg_Dct + ", tal_Nbr_Set="
				+ tal_Nbr_Set + ", pjTime=" + pjTime + "]";
	}
	
	
	
     
}
