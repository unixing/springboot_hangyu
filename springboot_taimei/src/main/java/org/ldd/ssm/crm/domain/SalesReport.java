package org.ldd.ssm.crm.domain;

import java.util.List;

/**
 * 销售报表实体
 * @Title:SalesReport 
 * @Description:
 * @author taimei-gds 
 * @date 2016-4-20 下午5:07:54
 */
public class SalesReport {
     private String date;	//航班日期
     private String airPort;	//航段
     private String yPrice;	//Y舱价格
     private String cutPriceTeam;	//切位团队价格
     private List<Airdiscount> airdiscounts;//折扣舱位和人数
     private String two_Tak_Ppt;	//200%
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
     private String one_Fve_Dnt_Ppt;	//15%
     private String one_Dnt_Ppt;	//10%
     private String sal_Tak_Ppt;	//特殊舱位（小于20%）
     private String grp_Nbr;	//团队人数
     private String pgs_Per_Cls;	//散人和团队人数总和
     private String wak_tol_Nbr;	//散客总人数
     private String wak_tol_Ine;	//散客总收入
     private String grp_Ine;	//团队总收入
     private String set_Ktr_Ine; //座公里收入
     private String avg_Dctd1;	//平均折扣
     private String avg_Dctd2;	//平均折扣
     private String avg_Dct;	//平均折扣
     
     private String stzsr;//散团总收入
     private String xssr;//小时收入
     private String pjkzl;//平均客座率
     private String mbxssr;//目标小时收入
     private String bbbt;//本班补贴
     private String byljbt;//本月累计补贴
     
     private String count_set;//布局座位数
     private String tal_Nbr_Set;//座位数
     private double lcl_Dpt_Tm;//起飞时刻(毫秒)
     private double lcl_Arrv_Tm;//到达时刻(毫秒)
     private String talTime;//总飞行时间
     private String egs_Lod_Fts;//收益客座率
     private String hbys;//航班营收
     private String hangju;//行距
     private String dpt_AirPt_Cd;
     private String arrv_Airpt_Cd;
     private String flt_Nbr;
     private String section_ket_ined1;//分段客座率
     private String section_ket_ined2;//分段客座率
     private String section_ket_ine;//分段客座率
     private String banci;//班次
     private String jbrs;//均班人数
     private String eterm_account_id;//采集账户
     private String hasData;//是否有数据
     private String pjzk;//平均折扣
     private String grp_Dct;//团队平均折扣
     private List<String> bcDateList;//有数据的日期的list
	public List<Airdiscount> getAirdiscounts() {
		return airdiscounts;
	}
	public void setAirdiscounts(List<Airdiscount> airdiscounts) {
		this.airdiscounts = airdiscounts;
	}
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
	 * @return the airPort
	 */
	public String getAirPort() {
		return airPort;
	}
	/**
	 * @param airPort the airPort to set
	 */
	public void setAirPort(String airPort) {
		this.airPort = airPort;
	}
	/**
	 * @return the yPrice
	 */
	public String getyPrice() {
		return yPrice;
	}
	/**
	 * @param yPrice the yPrice to set
	 */
	public void setyPrice(String yPrice) {
		this.yPrice = yPrice;
	}
	/**
	 * @return the cutPriceTeam
	 */
	public String getCutPriceTeam() {
		return cutPriceTeam;
	}
	/**
	 * @param cutPriceTeam the cutPriceTeam to set
	 */
	public void setCutPriceTeam(String cutPriceTeam) {
		this.cutPriceTeam = cutPriceTeam;
	}
	/**
	 * @return the two_Tak_Ppt
	 */
	public String getTwo_Tak_Ppt() {
		return two_Tak_Ppt;
	}
	/**
	 * @param two_Tak_Ppt the two_Tak_Ppt to set
	 */
	public void setTwo_Tak_Ppt(String two_Tak_Ppt) {
		this.two_Tak_Ppt = two_Tak_Ppt;
	}
	/**
	 * @return the ful_Pce_Ppt
	 */
	public String getFul_Pce_Ppt() {
		return ful_Pce_Ppt;
	}
	/**
	 * @param ful_Pce_Ppt the ful_Pce_Ppt to set
	 */
	public void setFul_Pce_Ppt(String ful_Pce_Ppt) {
		this.ful_Pce_Ppt = ful_Pce_Ppt;
	}
	/**
	 * @return the nne_Dnt_Ppt
	 */
	public String getNne_Dnt_Ppt() {
		return nne_Dnt_Ppt;
	}
	/**
	 * @param nne_Dnt_Ppt the nne_Dnt_Ppt to set
	 */
	public void setNne_Dnt_Ppt(String nne_Dnt_Ppt) {
		this.nne_Dnt_Ppt = nne_Dnt_Ppt;
	}
	/**
	 * @return the eht_Five_Dnt_Ppt
	 */
	public String getEht_Five_Dnt_Ppt() {
		return eht_Five_Dnt_Ppt;
	}
	/**
	 * @param eht_Five_Dnt_Ppt the eht_Five_Dnt_Ppt to set
	 */
	public void setEht_Five_Dnt_Ppt(String eht_Five_Dnt_Ppt) {
		this.eht_Five_Dnt_Ppt = eht_Five_Dnt_Ppt;
	}
	/**
	 * @return the eht_Dnt_Ppt
	 */
	public String getEht_Dnt_Ppt() {
		return eht_Dnt_Ppt;
	}
	/**
	 * @param eht_Dnt_Ppt the eht_Dnt_Ppt to set
	 */
	public void setEht_Dnt_Ppt(String eht_Dnt_Ppt) {
		this.eht_Dnt_Ppt = eht_Dnt_Ppt;
	}
	/**
	 * @return the sen_Five_Dnt_Ppt
	 */
	public String getSen_Five_Dnt_Ppt() {
		return sen_Five_Dnt_Ppt;
	}
	/**
	 * @param sen_Five_Dnt_Ppt the sen_Five_Dnt_Ppt to set
	 */
	public void setSen_Five_Dnt_Ppt(String sen_Five_Dnt_Ppt) {
		this.sen_Five_Dnt_Ppt = sen_Five_Dnt_Ppt;
	}
	/**
	 * @return the sen_Dnt_Ppt
	 */
	public String getSen_Dnt_Ppt() {
		return sen_Dnt_Ppt;
	}
	/**
	 * @param sen_Dnt_Ppt the sen_Dnt_Ppt to set
	 */
	public void setSen_Dnt_Ppt(String sen_Dnt_Ppt) {
		this.sen_Dnt_Ppt = sen_Dnt_Ppt;
	}
	/**
	 * @return the six_Five_Dnt_Ppt
	 */
	public String getSix_Five_Dnt_Ppt() {
		return six_Five_Dnt_Ppt;
	}
	/**
	 * @param six_Five_Dnt_Ppt the six_Five_Dnt_Ppt to set
	 */
	public void setSix_Five_Dnt_Ppt(String six_Five_Dnt_Ppt) {
		this.six_Five_Dnt_Ppt = six_Five_Dnt_Ppt;
	}
	/**
	 * @return the six_Dnt_Ppt
	 */
	public String getSix_Dnt_Ppt() {
		return six_Dnt_Ppt;
	}
	/**
	 * @param six_Dnt_Ppt the six_Dnt_Ppt to set
	 */
	public void setSix_Dnt_Ppt(String six_Dnt_Ppt) {
		this.six_Dnt_Ppt = six_Dnt_Ppt;
	}
	/**
	 * @return the fve_Fve_Dnt_Ppt
	 */
	public String getFve_Fve_Dnt_Ppt() {
		return fve_Fve_Dnt_Ppt;
	}
	/**
	 * @param fve_Fve_Dnt_Ppt the fve_Fve_Dnt_Ppt to set
	 */
	public void setFve_Fve_Dnt_Ppt(String fve_Fve_Dnt_Ppt) {
		this.fve_Fve_Dnt_Ppt = fve_Fve_Dnt_Ppt;
	}
	/**
	 * @return the fve_Dnt_Ppt
	 */
	public String getFve_Dnt_Ppt() {
		return fve_Dnt_Ppt;
	}
	/**
	 * @param fve_Dnt_Ppt the fve_Dnt_Ppt to set
	 */
	public void setFve_Dnt_Ppt(String fve_Dnt_Ppt) {
		this.fve_Dnt_Ppt = fve_Dnt_Ppt;
	}
	/**
	 * @return the fur_Fve_Dnt_Ppt
	 */
	public String getFur_Fve_Dnt_Ppt() {
		return fur_Fve_Dnt_Ppt;
	}
	/**
	 * @param fur_Fve_Dnt_Ppt the fur_Fve_Dnt_Ppt to set
	 */
	public void setFur_Fve_Dnt_Ppt(String fur_Fve_Dnt_Ppt) {
		this.fur_Fve_Dnt_Ppt = fur_Fve_Dnt_Ppt;
	}
	/**
	 * @return the fur_Dnt_Ppt
	 */
	public String getFur_Dnt_Ppt() {
		return fur_Dnt_Ppt;
	}
	/**
	 * @param fur_Dnt_Ppt the fur_Dnt_Ppt to set
	 */
	public void setFur_Dnt_Ppt(String fur_Dnt_Ppt) {
		this.fur_Dnt_Ppt = fur_Dnt_Ppt;
	}
	/**
	 * @return the thr_Fve_Dnt_Ppt
	 */
	public String getThr_Fve_Dnt_Ppt() {
		return thr_Fve_Dnt_Ppt;
	}
	/**
	 * @param thr_Fve_Dnt_Ppt the thr_Fve_Dnt_Ppt to set
	 */
	public void setThr_Fve_Dnt_Ppt(String thr_Fve_Dnt_Ppt) {
		this.thr_Fve_Dnt_Ppt = thr_Fve_Dnt_Ppt;
	}
	/**
	 * @return the thr_Dnt_Ppt
	 */
	public String getThr_Dnt_Ppt() {
		return thr_Dnt_Ppt;
	}
	/**
	 * @param thr_Dnt_Ppt the thr_Dnt_Ppt to set
	 */
	public void setThr_Dnt_Ppt(String thr_Dnt_Ppt) {
		this.thr_Dnt_Ppt = thr_Dnt_Ppt;
	}
	/**
	 * @return the two_Fve_Dnt_Ppt
	 */
	public String getTwo_Fve_Dnt_Ppt() {
		return two_Fve_Dnt_Ppt;
	}
	/**
	 * @param two_Fve_Dnt_Ppt the two_Fve_Dnt_Ppt to set
	 */
	public void setTwo_Fve_Dnt_Ppt(String two_Fve_Dnt_Ppt) {
		this.two_Fve_Dnt_Ppt = two_Fve_Dnt_Ppt;
	}
	/**
	 * @return the two_Dnt_Ppt
	 */
	public String getTwo_Dnt_Ppt() {
		return two_Dnt_Ppt;
	}
	/**
	 * @param two_Dnt_Ppt the two_Dnt_Ppt to set
	 */
	public void setTwo_Dnt_Ppt(String two_Dnt_Ppt) {
		this.two_Dnt_Ppt = two_Dnt_Ppt;
	}
	/**
	 * @return the one_Fve_Dnt_Ppt
	 */
	public String getOne_Fve_Dnt_Ppt() {
		return one_Fve_Dnt_Ppt;
	}
	/**
	 * @param one_Fve_Dnt_Ppt the one_Fve_Dnt_Ppt to set
	 */
	public void setOne_Fve_Dnt_Ppt(String one_Fve_Dnt_Ppt) {
		this.one_Fve_Dnt_Ppt = one_Fve_Dnt_Ppt;
	}
	/**
	 * @return the one_Dnt_Ppt
	 */
	public String getOne_Dnt_Ppt() {
		return one_Dnt_Ppt;
	}
	/**
	 * @param one_Dnt_Ppt the one_Dnt_Ppt to set
	 */
	public void setOne_Dnt_Ppt(String one_Dnt_Ppt) {
		this.one_Dnt_Ppt = one_Dnt_Ppt;
	}
	
	/**
	 * @return the sal_Tak_Ppt
	 */
	public String getSal_Tak_Ppt() {
		return sal_Tak_Ppt;
	}
	/**
	 * @param sal_Tak_Ppt the sal_Tak_Ppt to set
	 */
	public void setSal_Tak_Ppt(String sal_Tak_Ppt) {
		this.sal_Tak_Ppt = sal_Tak_Ppt;
	}
	/**
	 * @return the grp_Nbr
	 */
	public String getGrp_Nbr() {
		return grp_Nbr;
	}
	/**
	 * @param grp_Nbr the grp_Nbr to set
	 */
	public void setGrp_Nbr(String grp_Nbr) {
		this.grp_Nbr = grp_Nbr;
	}
	/**
	 * @return the pgs_Per_Cls
	 */
	public String getPgs_Per_Cls() {
		return pgs_Per_Cls;
	}
	/**
	 * @param pgs_Per_Cls the pgs_Per_Cls to set
	 */
	public void setPgs_Per_Cls(String pgs_Per_Cls) {
		this.pgs_Per_Cls = pgs_Per_Cls;
	}
	/**
	 * @return the wak_tol_Nbr
	 */
	public String getWak_tol_Nbr() {
		return wak_tol_Nbr;
	}
	/**
	 * @param wak_tol_Nbr the wak_tol_Nbr to set
	 */
	public void setWak_tol_Nbr(String wak_tol_Nbr) {
		this.wak_tol_Nbr = wak_tol_Nbr;
	}
	/**
	 * @return the wak_tol_Ine
	 */
	public String getWak_tol_Ine() {
		return wak_tol_Ine;
	}
	/**
	 * @param wak_tol_Ine the wak_tol_Ine to set
	 */
	public void setWak_tol_Ine(String wak_tol_Ine) {
		this.wak_tol_Ine = wak_tol_Ine;
	}
	/**
	 * @return the grp_Ine
	 */
	public String getGrp_Ine() {
		return grp_Ine;
	}
	/**
	 * @param grp_Ine the grp_Ine to set
	 */
	public void setGrp_Ine(String grp_Ine) {
		this.grp_Ine = grp_Ine;
	}
	
	/**
	 * @return the tal_Nbr_Set
	 */
	public String getTal_Nbr_Set() {
		return tal_Nbr_Set;
	}
	/**
	 * @param tal_Nbr_Set the tal_Nbr_Set to set
	 */
	public void setTal_Nbr_Set(String tal_Nbr_Set) {
		this.tal_Nbr_Set = tal_Nbr_Set;
	}
	
	/**
	 * @return the set_Ktr_Ine
	 */
	public String getSet_Ktr_Ine() {
		return set_Ktr_Ine;
	}
	/**
	 * @param set_Ktr_Ine the set_Ktr_Ine to set
	 */
	public void setSet_Ktr_Ine(String set_Ktr_Ine) {
		this.set_Ktr_Ine = set_Ktr_Ine;
	}
	/**
	 * @return the stzsr
	 */
	public String getStzsr() {
		return stzsr;
	}
	/**
	 * @param stzsr the stzsr to set
	 */
	public void setStzsr(String stzsr) {
		this.stzsr = stzsr;
	}
	/**
	 * @return the xssr
	 */
	public String getXssr() {
		return xssr;
	}
	/**
	 * @param xssr the xssr to set
	 */
	public void setXssr(String xssr) {
		this.xssr = xssr;
	}
	/**
	 * @return the pjkzl
	 */
	public String getPjkzl() {
		return pjkzl;
	}
	/**
	 * @param pjkzl the pjkzl to set
	 */
	public void setPjkzl(String pjkzl) {
		this.pjkzl = pjkzl;
	}
	/**
	 * @return the mbxssr
	 */
	public String getMbxssr() {
		return mbxssr;
	}
	/**
	 * @param mbxssr the mbxssr to set
	 */
	public void setMbxssr(String mbxssr) {
		this.mbxssr = mbxssr;
	}
	/**
	 * @return the bbbt
	 */
	public String getBbbt() {
		return bbbt;
	}
	/**
	 * @param bbbt the bbbt to set
	 */
	public void setBbbt(String bbbt) {
		this.bbbt = bbbt;
	}
	/**
	 * @return the byljbt
	 */
	public String getByljbt() {
		return byljbt;
	}
	/**
	 * @param byljbt the byljbt to set
	 */
	public void setByljbt(String byljbt) {
		this.byljbt = byljbt;
	}
	
	/**
	 * @return the lcl_Dpt_Tm
	 */
	public double getLcl_Dpt_Tm() {
		return lcl_Dpt_Tm;
	}
	/**
	 * @param lcl_Dpt_Tm the lcl_Dpt_Tm to set
	 */
	public void setLcl_Dpt_Tm(double lcl_Dpt_Tm) {
		this.lcl_Dpt_Tm = lcl_Dpt_Tm;
	}
	/**
	 * @return the lcl_Arrv_Tm
	 */
	public double getLcl_Arrv_Tm() {
		return lcl_Arrv_Tm;
	}
	/**
	 * @param lcl_Arrv_Tm the lcl_Arrv_Tm to set
	 */
	public void setLcl_Arrv_Tm(double lcl_Arrv_Tm) {
		this.lcl_Arrv_Tm = lcl_Arrv_Tm;
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
	 * @return the hangju
	 */
	public String getHangju() {
		return hangju;
	}
	/**
	 * @param hangju the hangju to set
	 */
	public void setHangju(String hangju) {
		this.hangju = hangju;
	}
	/**
	 * @return the dpt_AirPt_Cd
	 */
	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}
	/**
	 * @param dpt_AirPt_Cd the dpt_AirPt_Cd to set
	 */
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	/**
	 * @return the arrv_Airpt_Cd
	 */
	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}
	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	/**
	 * @return the flt_Nbr
	 */
	public String getFlt_Nbr() {
		return flt_Nbr;
	}
	/**
	 * @param flt_Nbr the flt_Nbr to set
	 */
	public void setFlt_Nbr(String flt_Nbr) {
		this.flt_Nbr = flt_Nbr;
	}
	/**
	 * @return the count_set
	 */
	public String getCount_set() {
		return count_set;
	}
	/**
	 * @param count_set the count_set to set
	 */
	public void setCount_set(String count_set) {
		this.count_set = count_set;
	}
	/**
	 * @return the egs_Lod_Fts
	 */
	public String getEgs_Lod_Fts() {
		return egs_Lod_Fts;
	}
	/**
	 * @param egs_Lod_Fts the egs_Lod_Fts to set
	 */
	public void setEgs_Lod_Fts(String egs_Lod_Fts) {
		this.egs_Lod_Fts = egs_Lod_Fts;
	}
	/**
	 * @return the avg_Dctd1
	 */
	public String getAvg_Dctd1() {
		return avg_Dctd1;
	}
	/**
	 * @param avg_Dctd1 the avg_Dctd1 to set
	 */
	public void setAvg_Dctd1(String avg_Dctd1) {
		this.avg_Dctd1 = avg_Dctd1;
	}
	/**
	 * @return the avg_Dctd2
	 */
	public String getAvg_Dctd2() {
		return avg_Dctd2;
	}
	/**
	 * @param avg_Dctd2 the avg_Dctd2 to set
	 */
	public void setAvg_Dctd2(String avg_Dctd2) {
		this.avg_Dctd2 = avg_Dctd2;
	}
	
	/**
	 * @return the section_ket_ined1
	 */
	public String getSection_ket_ined1() {
		return section_ket_ined1;
	}
	/**
	 * @param section_ket_ined1 the section_ket_ined1 to set
	 */
	public void setSection_ket_ined1(String section_ket_ined1) {
		this.section_ket_ined1 = section_ket_ined1;
	}
	/**
	 * @return the section_ket_ined2
	 */
	public String getSection_ket_ined2() {
		return section_ket_ined2;
	}
	/**
	 * @param section_ket_ined2 the section_ket_ined2 to set
	 */
	public void setSection_ket_ined2(String section_ket_ined2) {
		this.section_ket_ined2 = section_ket_ined2;
	}
	/**
	 * @return the avg_Dct
	 */
	public String getAvg_Dct() {
		return avg_Dct;
	}
	/**
	 * @param avg_Dct the avg_Dct to set
	 */
	public void setAvg_Dct(String avg_Dct) {
		this.avg_Dct = avg_Dct;
	}
	/**
	 * @return the section_ket_ine
	 */
	public String getSection_ket_ine() {
		return section_ket_ine;
	}
	/**
	 * @param section_ket_ine the section_ket_ine to set
	 */
	public void setSection_ket_ine(String section_ket_ine) {
		this.section_ket_ine = section_ket_ine;
	}
	/**
	 * @return the banci
	 */
	public String getBanci() {
		return banci;
	}
	/**
	 * @param banci the banci to set
	 */
	public void setBanci(String banci) {
		this.banci = banci;
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
	 * @return the eterm_account_id
	 */
	public String getEterm_account_id() {
		return eterm_account_id;
	}
	/**
	 * @param eterm_account_id the eterm_account_id to set
	 */
	public void setEterm_account_id(String eterm_account_id) {
		this.eterm_account_id = eterm_account_id;
	}
	/**
	 * @return the hasData
	 */
	public String getHasData() {
		return hasData;
	}
	/**
	 * @param hasData the hasData to set
	 */
	public void setHasData(String hasData) {
		this.hasData = hasData;
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
	 * @return the bcDateList
	 */
	public List<String> getBcDateList() {
		return bcDateList;
	}
	/**
	 * @param bcDateList the bcDateList to set
	 */
	public void setBcDateList(List<String> bcDateList) {
		this.bcDateList = bcDateList;
	}
	/**
	 * @return the grp_Dct
	 */
	public String getGrp_Dct() {
		return grp_Dct;
	}
	/**
	 * @param grp_Dct the grp_Dct to set
	 */
	public void setGrp_Dct(String grp_Dct) {
		this.grp_Dct = grp_Dct;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SalesReport [date=" + date + ", airPort=" + airPort
				+ ", yPrice=" + yPrice + ", cutPriceTeam=" + cutPriceTeam
				+ ", airdiscounts=" + airdiscounts + ", two_Tak_Ppt="
				+ two_Tak_Ppt + ", ful_Pce_Ppt=" + ful_Pce_Ppt
				+ ", nne_Dnt_Ppt=" + nne_Dnt_Ppt + ", eht_Five_Dnt_Ppt="
				+ eht_Five_Dnt_Ppt + ", eht_Dnt_Ppt=" + eht_Dnt_Ppt
				+ ", sen_Five_Dnt_Ppt=" + sen_Five_Dnt_Ppt + ", sen_Dnt_Ppt="
				+ sen_Dnt_Ppt + ", six_Five_Dnt_Ppt=" + six_Five_Dnt_Ppt
				+ ", six_Dnt_Ppt=" + six_Dnt_Ppt + ", fve_Fve_Dnt_Ppt="
				+ fve_Fve_Dnt_Ppt + ", fve_Dnt_Ppt=" + fve_Dnt_Ppt
				+ ", fur_Fve_Dnt_Ppt=" + fur_Fve_Dnt_Ppt + ", fur_Dnt_Ppt="
				+ fur_Dnt_Ppt + ", thr_Fve_Dnt_Ppt=" + thr_Fve_Dnt_Ppt
				+ ", thr_Dnt_Ppt=" + thr_Dnt_Ppt + ", two_Fve_Dnt_Ppt="
				+ two_Fve_Dnt_Ppt + ", two_Dnt_Ppt=" + two_Dnt_Ppt
				+ ", one_Fve_Dnt_Ppt=" + one_Fve_Dnt_Ppt + ", one_Dnt_Ppt="
				+ one_Dnt_Ppt + ", sal_Tak_Ppt=" + sal_Tak_Ppt + ", grp_Nbr="
				+ grp_Nbr + ", pgs_Per_Cls=" + pgs_Per_Cls + ", wak_tol_Nbr="
				+ wak_tol_Nbr + ", wak_tol_Ine=" + wak_tol_Ine + ", grp_Ine="
				+ grp_Ine + ", set_Ktr_Ine=" + set_Ktr_Ine + ", avg_Dctd1="
				+ avg_Dctd1 + ", avg_Dctd2=" + avg_Dctd2 + ", avg_Dct="
				+ avg_Dct + ", stzsr=" + stzsr + ", xssr=" + xssr + ", pjkzl="
				+ pjkzl + ", mbxssr=" + mbxssr + ", bbbt=" + bbbt + ", byljbt="
				+ byljbt + ", count_set=" + count_set + ", tal_Nbr_Set="
				+ tal_Nbr_Set + ", lcl_Dpt_Tm=" + lcl_Dpt_Tm + ", lcl_Arrv_Tm="
				+ lcl_Arrv_Tm + ", talTime=" + talTime + ", egs_Lod_Fts="
				+ egs_Lod_Fts + ", hbys=" + hbys + ", hangju=" + hangju
				+ ", dpt_AirPt_Cd=" + dpt_AirPt_Cd + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", flt_Nbr=" + flt_Nbr
				+ ", section_ket_ined1=" + section_ket_ined1
				+ ", section_ket_ined2=" + section_ket_ined2
				+ ", section_ket_ine=" + section_ket_ine + ", banci=" + banci
				+ ", jbrs=" + jbrs + ", eterm_account_id=" + eterm_account_id
				+ ", hasData=" + hasData + ", pjzk=" + pjzk + ", grp_Dct="
				+ grp_Dct + ", bcDateList=" + bcDateList + "]";
	}
	
	
}
