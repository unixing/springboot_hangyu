package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 中航数据domain类
 */
public class Z_Airdata {
	private int id;
	private Date lcl_Dpt_Day; // 航班执行日期
	private String lcl_Dpt_Day_Str;
	private String method;
	private String dpt_AirPt_Cd; // 始发地
	private String arrv_Airpt_Cd; // 到达地
	private String flt_Nbr; // 航班号
	private String cpy_Nm; // 公司
	private String lcl_Dpt_Tm; //   出发时刻
	private String lcl_Arrv_Tm; // 到达时刻
	private String flt_Rte_Cd; // 航线
	private int tal_Nbr_Set; // 座位总数
	private int count_Set;//布局座位数
	private int pgs_Per_Cls; // 每班旅客
	private int grp_Nbr; // 团队人数
	private int ech_Cls_Grp; // 每班团队
	private int grp_Fng_Per;//散团总人数
	private BigDecimal grp_Fng_Rte; // 团队成行率
	private BigDecimal grp_Ine;//团队收入
	private BigDecimal grp_Ppt; // 团队比例
	private BigDecimal egs_Lod_Fts; // 收益客座率
	private int totalNumber;//每班收入
	private BigDecimal Set_Ktr_Ine;//座公里收入
	private BigDecimal avg_Dct; //平均折扣
	private BigDecimal idd_Dct; // 散客折扣
	private BigDecimal grp_Dct; // 团队折扣
	private int two_Tak_Ppt; // 两舱比例
	private int ful_Pce_Ppt; // 全价比例
	private int nne_Dnt_Ppt; // 9折比例
	private int eht_Five_Dnt_Ppt; // 8.5折比例
	private int eht_Dnt_Ppt; // 8折比例
	private int sen_Five_Dnt_Ppt; // 7.5折比例
	private int sen_Dnt_Ppt; // 7折比例
	private int six_Five_Dnt_Ppt; // 6.5折比例
	private int six_Dnt_Ppt; // 6折比例
	private int fve_Fve_Dnt_Ppt; // 5.5折比例
	private int fve_Dnt_Ppt; // 5折比例
	private int fur_Fve_Dnt_Ppt; // 4.5折比例
	private int fur_Dnt_Ppt; // 4折比例
	private int thr_Fve_Dnt_Ppt;	//35%
    private int thr_Dnt_Ppt;	//30%
    private int two_Fve_Dnt_Ppt;	//25%
    private int two_Dnt_Ppt;	//20%
	private int sal_Tak_Ppt; // 特殊舱比例
	private int r_Tak_Ppt; // R舱比例
	private int u_Tak_Ppt; // U舱比例
	private int i_Tak_Ppt; // I舱比例
	private int z_Tak_Ppt; // Z舱比例
	private int e_Tak_Ppt; // E舱比例
	private int a_Tak_Ppt; // A舱比例
	private int o_Tak_Ppt; // O舱比例
	private int s_Tak_Ppt; // S舱比例
	private int h_Tak_Ppt; // H舱比例
	private int x_Tak_Ppt; // X舱比例
	private int children; // 儿童
	private int yBFare;//yb运价
	private int sailingDistance;//航距
	private String dta_Sce;//数据源获得源
	private Date dta_Dte; // 数据导入日期
	private String data_Date;
	private long company;//数据属于哪家公司
	private String itia;
	private String month;//月份
	private String eterm_account_id;//采集账户
	private String dpt_Cha_Nm;
	private String arrv_Cha_Nm;
	private String allData;//这条航线的数据是否齐全 (1表示齐全,及A-B-C 等于三条数据)
	private String comeAndGo;//往返数据是否齐全 (1表示齐全,及A-B-C  C-B-A等于六条数据)
	public int getCount_Set() {
		return count_Set;
	}

	public void setCount_Set(int count_Set) {
		this.count_Set = count_Set;
	}

	public String getData_Date() {
		return data_Date;
	}

	public void setData_Date(String data_Date) {
		this.data_Date = data_Date;
	}

	public String getLcl_Dpt_Day_Str() {
		return lcl_Dpt_Day_Str;
	}

	public void setLcl_Dpt_Day_Str(String lcl_Dpt_Day_Str) {
		this.lcl_Dpt_Day_Str = lcl_Dpt_Day_Str;
	}

	public String getItia() {
		return itia;
	}

	public void setItia(String itia) {
		this.itia = itia;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}

	public int getyBFare() {
		return yBFare;
	}

	public void setyBFare(int yBFare) {
		this.yBFare = yBFare;
	}

	public int getSailingDistance() {
		return sailingDistance;
	}

	public void setSailingDistance(int sailingDistance) {
		this.sailingDistance = sailingDistance;
	}

	public int getGrp_Fng_Per() {
		return grp_Fng_Per;
	}

	public void setGrp_Fng_Per(int grp_Fng_Per) {
		this.grp_Fng_Per = grp_Fng_Per;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getLcl_Dpt_Day() {
		return lcl_Dpt_Day;
	}
	
	public int getThr_Fve_Dnt_Ppt() {
		return thr_Fve_Dnt_Ppt;
	}

	public void setThr_Fve_Dnt_Ppt(int thr_Fve_Dnt_Ppt) {
		this.thr_Fve_Dnt_Ppt = thr_Fve_Dnt_Ppt;
	}

	public String getLcl_Dpt_Tm() {
		return lcl_Dpt_Tm;
	}

	public void setLcl_Dpt_Tm(String lcl_Dpt_Tm) {
		this.lcl_Dpt_Tm = lcl_Dpt_Tm;
	}

	public int getThr_Dnt_Ppt() {
		return thr_Dnt_Ppt;
	}

	public void setThr_Dnt_Ppt(int thr_Dnt_Ppt) {
		this.thr_Dnt_Ppt = thr_Dnt_Ppt;
	}

	public int getTwo_Fve_Dnt_Ppt() {
		return two_Fve_Dnt_Ppt;
	}

	public void setTwo_Fve_Dnt_Ppt(int two_Fve_Dnt_Ppt) {
		this.two_Fve_Dnt_Ppt = two_Fve_Dnt_Ppt;
	}

	public int getTwo_Dnt_Ppt() {
		return two_Dnt_Ppt;
	}

	public void setTwo_Dnt_Ppt(int two_Dnt_Ppt) {
		this.two_Dnt_Ppt = two_Dnt_Ppt;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDpt_AirPt_Cd() {
		return dpt_AirPt_Cd;
	}

	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		this.dpt_AirPt_Cd = dpt_AirPt_Cd;
	}

	public String getArrv_Airpt_Cd() {
		return arrv_Airpt_Cd;
	}

	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		this.arrv_Airpt_Cd = arrv_Airpt_Cd;
	}

	public String getFlt_Nbr() {
		return flt_Nbr;
	}

	public void setFlt_Nbr(String flt_Nbr) {
		this.flt_Nbr = flt_Nbr;
	}

	public String getCpy_Nm() {
		return cpy_Nm;
	}

	public void setCpy_Nm(String cpy_Nm) {
		this.cpy_Nm = cpy_Nm;
	}

	public String getFlt_Rte_Cd() {
		return flt_Rte_Cd;
	}

	public void setFlt_Rte_Cd(String flt_Rte_Cd) {
		this.flt_Rte_Cd = flt_Rte_Cd;
	}


	public int getTal_Nbr_Set() {
		return tal_Nbr_Set;
	}

	public void setTal_Nbr_Set(int tal_Nbr_Set) {
		this.tal_Nbr_Set = tal_Nbr_Set;
	}

	public int getPgs_Per_Cls() {
		return pgs_Per_Cls;
	}

	public void setPgs_Per_Cls(int pgs_Per_Cls) {
		this.pgs_Per_Cls = pgs_Per_Cls;
	}

	public int getGrp_Nbr() {
		return grp_Nbr;
	}

	public void setGrp_Nbr(int grp_Nbr) {
		this.grp_Nbr = grp_Nbr;
	}

	public int getEch_Cls_Grp() {
		return ech_Cls_Grp;
	}

	public void setEch_Cls_Grp(int ech_Cls_Grp) {
		this.ech_Cls_Grp = ech_Cls_Grp;
	}


	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}

	public BigDecimal getAvg_Dct() {
		if(avg_Dct==null){
			avg_Dct = new BigDecimal(0);
		}
		return avg_Dct;
	}

	public void setAvg_Dct(BigDecimal avg_Dct) {
		this.avg_Dct = avg_Dct;
	}

	public BigDecimal getIdd_Dct() {
		if(idd_Dct==null){
			idd_Dct = new BigDecimal(0);
		}
		return idd_Dct;
	}

	public void setIdd_Dct(BigDecimal idd_Dct) {
		this.idd_Dct = idd_Dct;
	}

	public BigDecimal getGrp_Fng_Rte() {
		if(grp_Fng_Rte==null){
			grp_Fng_Rte = new BigDecimal(0);
		}
		return grp_Fng_Rte;
	}

	public void setGrp_Fng_Rte(BigDecimal grp_Fng_Rte) {
		this.grp_Fng_Rte = grp_Fng_Rte;
	}

	public BigDecimal getGrp_Ine() {
		if(grp_Ine==null){
			grp_Ine = new BigDecimal(0);
		}
		return grp_Ine;
	}

	public void setGrp_Ine(BigDecimal grp_Ine) {
		this.grp_Ine = grp_Ine;
	}

	public BigDecimal getGrp_Ppt() {
		if(grp_Ppt==null){
			grp_Ppt = new BigDecimal(0);
		}
		return grp_Ppt;
	}

	public void setGrp_Ppt(BigDecimal grp_Ppt) {
		this.grp_Ppt = grp_Ppt;
	}

	public BigDecimal getEgs_Lod_Fts() {
		if(egs_Lod_Fts==null){
			egs_Lod_Fts = new BigDecimal(0);
		}
		return egs_Lod_Fts;
	}

	public void setEgs_Lod_Fts(BigDecimal egs_Lod_Fts) {
		this.egs_Lod_Fts = egs_Lod_Fts;
	}

	public BigDecimal getSet_Ktr_Ine() {
		if(Set_Ktr_Ine==null){
			Set_Ktr_Ine = new BigDecimal(0);
		}
		return Set_Ktr_Ine;
	}

	public void setSet_Ktr_Ine(BigDecimal set_Ktr_Ine) {
		Set_Ktr_Ine = set_Ktr_Ine;
	}

	public BigDecimal getGrp_Dct() {
		if(grp_Dct==null){
			grp_Dct = new BigDecimal(0);
		}
		return grp_Dct;
	}

	public void setGrp_Dct(BigDecimal grp_Dct) {
		this.grp_Dct = grp_Dct;
	}

	public int getTwo_Tak_Ppt() {
		return two_Tak_Ppt;
	}

	public void setTwo_Tak_Ppt(int two_Tak_Ppt) {
		this.two_Tak_Ppt = two_Tak_Ppt;
	}

	public int getFul_Pce_Ppt() {
		return ful_Pce_Ppt;
	}

	public void setFul_Pce_Ppt(int ful_Pce_Ppt) {
		this.ful_Pce_Ppt = ful_Pce_Ppt;
	}

	public int getNne_Dnt_Ppt() {
		return nne_Dnt_Ppt;
	}

	public void setNne_Dnt_Ppt(int nne_Dnt_Ppt) {
		this.nne_Dnt_Ppt = nne_Dnt_Ppt;
	}

	public int getEht_Five_Dnt_Ppt() {
		return eht_Five_Dnt_Ppt;
	}

	public void setEht_Five_Dnt_Ppt(int eht_Five_Dnt_Ppt) {
		this.eht_Five_Dnt_Ppt = eht_Five_Dnt_Ppt;
	}

	public int getEht_Dnt_Ppt() {
		return eht_Dnt_Ppt;
	}

	public void setEht_Dnt_Ppt(int eht_Dnt_Ppt) {
		this.eht_Dnt_Ppt = eht_Dnt_Ppt;
	}

	public int getSen_Five_Dnt_Ppt() {
		return sen_Five_Dnt_Ppt;
	}

	public void setSen_Five_Dnt_Ppt(int sen_Five_Dnt_Ppt) {
		this.sen_Five_Dnt_Ppt = sen_Five_Dnt_Ppt;
	}

	public int getSen_Dnt_Ppt() {
		return sen_Dnt_Ppt;
	}

	public void setSen_Dnt_Ppt(int sen_Dnt_Ppt) {
		this.sen_Dnt_Ppt = sen_Dnt_Ppt;
	}

	public int getSix_Five_Dnt_Ppt() {
		return six_Five_Dnt_Ppt;
	}

	public void setSix_Five_Dnt_Ppt(int six_Five_Dnt_Ppt) {
		this.six_Five_Dnt_Ppt = six_Five_Dnt_Ppt;
	}

	public int getSix_Dnt_Ppt() {
		return six_Dnt_Ppt;
	}

	public void setSix_Dnt_Ppt(int six_Dnt_Ppt) {
		this.six_Dnt_Ppt = six_Dnt_Ppt;
	}

	public int getFve_Fve_Dnt_Ppt() {
		return fve_Fve_Dnt_Ppt;
	}

	public void setFve_Fve_Dnt_Ppt(int fve_Fve_Dnt_Ppt) {
		this.fve_Fve_Dnt_Ppt = fve_Fve_Dnt_Ppt;
	}

	public int getFve_Dnt_Ppt() {
		return fve_Dnt_Ppt;
	}

	public void setFve_Dnt_Ppt(int fve_Dnt_Ppt) {
		this.fve_Dnt_Ppt = fve_Dnt_Ppt;
	}

	public int getFur_Fve_Dnt_Ppt() {
		return fur_Fve_Dnt_Ppt;
	}

	public void setFur_Fve_Dnt_Ppt(int fur_Fve_Dnt_Ppt) {
		this.fur_Fve_Dnt_Ppt = fur_Fve_Dnt_Ppt;
	}

	public int getFur_Dnt_Ppt() {
		return fur_Dnt_Ppt;
	}

	public void setFur_Dnt_Ppt(int fur_Dnt_Ppt) {
		this.fur_Dnt_Ppt = fur_Dnt_Ppt;
	}

	public int getSal_Tak_Ppt() {
		return sal_Tak_Ppt;
	}

	public void setSal_Tak_Ppt(int sal_Tak_Ppt) {
		this.sal_Tak_Ppt = sal_Tak_Ppt;
	}

	public int getR_Tak_Ppt() {
		return r_Tak_Ppt;
	}

	public void setR_Tak_Ppt(int r_Tak_Ppt) {
		this.r_Tak_Ppt = r_Tak_Ppt;
	}

	public int getU_Tak_Ppt() {
		return u_Tak_Ppt;
	}

	public void setU_Tak_Ppt(int u_Tak_Ppt) {
		this.u_Tak_Ppt = u_Tak_Ppt;
	}

	public int getI_Tak_Ppt() {
		return i_Tak_Ppt;
	}

	public void setI_Tak_Ppt(int i_Tak_Ppt) {
		this.i_Tak_Ppt = i_Tak_Ppt;
	}

	public int getZ_Tak_Ppt() {
		return z_Tak_Ppt;
	}

	public void setZ_Tak_Ppt(int z_Tak_Ppt) {
		this.z_Tak_Ppt = z_Tak_Ppt;
	}

	public int getE_Tak_Ppt() {
		return e_Tak_Ppt;
	}

	public void setE_Tak_Ppt(int e_Tak_Ppt) {
		this.e_Tak_Ppt = e_Tak_Ppt;
	}

	public int getA_Tak_Ppt() {
		return a_Tak_Ppt;
	}

	public void setA_Tak_Ppt(int a_Tak_Ppt) {
		this.a_Tak_Ppt = a_Tak_Ppt;
	}

	public int getO_Tak_Ppt() {
		return o_Tak_Ppt;
	}

	public void setO_Tak_Ppt(int o_Tak_Ppt) {
		this.o_Tak_Ppt = o_Tak_Ppt;
	}

	public int getS_Tak_Ppt() {
		return s_Tak_Ppt;
	}

	public void setS_Tak_Ppt(int s_Tak_Ppt) {
		this.s_Tak_Ppt = s_Tak_Ppt;
	}

	public int getH_Tak_Ppt() {
		return h_Tak_Ppt;
	}

	public void setH_Tak_Ppt(int h_Tak_Ppt) {
		this.h_Tak_Ppt = h_Tak_Ppt;
	}

	public int getX_Tak_Ppt() {
		return x_Tak_Ppt;
	}

	public void setX_Tak_Ppt(int x_Tak_Ppt) {
		this.x_Tak_Ppt = x_Tak_Ppt;
	}

	public int getChildren() {
		return children;
	}

	public void setChildren(int children) {
		this.children = children;
	}

	public String getDta_Sce() {
		return dta_Sce;
	}

	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}

	public void setLcl_Dpt_Day(Date lcl_Dpt_Day) {
		this.lcl_Dpt_Day = lcl_Dpt_Day;
	}

	public void setDta_Dte(Date dta_Dte) {
		this.dta_Dte = dta_Dte;
	}

	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getDta_Dte() {
		return dta_Dte;
	}

	/**
	 * @return the lcl_Arrv_Tm
	 */
	public String getLcl_Arrv_Tm() {
		return lcl_Arrv_Tm;
	}

	/**
	 * @param lcl_Arrv_Tm the lcl_Arrv_Tm to set
	 */
	public void setLcl_Arrv_Tm(String lcl_Arrv_Tm) {
		this.lcl_Arrv_Tm = lcl_Arrv_Tm;
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

	public String getDpt_Cha_Nm() {
		return dpt_Cha_Nm;
	}

	public void setDpt_Cha_Nm(String dpt_Cha_Nm) {
		this.dpt_Cha_Nm = dpt_Cha_Nm;
	}

	public String getArrv_Cha_Nm() {
		return arrv_Cha_Nm;
	}

	public void setArrv_Cha_Nm(String arrv_Cha_Nm) {
		this.arrv_Cha_Nm = arrv_Cha_Nm;
	}

	

	/**
	 * @return the allData
	 */
	public String getAllData() {
		return allData;
	}

	/**
	 * @param allData the allData to set
	 */
	public void setAllData(String allData) {
		this.allData = allData;
	}

	/**
	 * @return the comeAndGo
	 */
	public String getComeAndGo() {
		return comeAndGo;
	}

	/**
	 * @param comeAndGo the comeAndGo to set
	 */
	public void setComeAndGo(String comeAndGo) {
		this.comeAndGo = comeAndGo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Z_Airdata [id=" + id + ", lcl_Dpt_Day=" + lcl_Dpt_Day
				+ ", lcl_Dpt_Day_Str=" + lcl_Dpt_Day_Str + ", method=" + method
				+ ", dpt_AirPt_Cd=" + dpt_AirPt_Cd + ", arrv_Airpt_Cd="
				+ arrv_Airpt_Cd + ", flt_Nbr=" + flt_Nbr + ", cpy_Nm=" + cpy_Nm
				+ ", lcl_Dpt_Tm=" + lcl_Dpt_Tm + ", lcl_Arrv_Tm=" + lcl_Arrv_Tm
				+ ", flt_Rte_Cd=" + flt_Rte_Cd + ", tal_Nbr_Set=" + tal_Nbr_Set
				+ ", count_Set=" + count_Set + ", pgs_Per_Cls=" + pgs_Per_Cls
				+ ", grp_Nbr=" + grp_Nbr + ", ech_Cls_Grp=" + ech_Cls_Grp
				+ ", grp_Fng_Per=" + grp_Fng_Per + ", grp_Fng_Rte="
				+ grp_Fng_Rte + ", grp_Ine=" + grp_Ine + ", grp_Ppt=" + grp_Ppt
				+ ", egs_Lod_Fts=" + egs_Lod_Fts + ", totalNumber="
				+ totalNumber + ", Set_Ktr_Ine=" + Set_Ktr_Ine + ", avg_Dct="
				+ avg_Dct + ", idd_Dct=" + idd_Dct + ", grp_Dct=" + grp_Dct
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
				+ two_Dnt_Ppt + ", sal_Tak_Ppt=" + sal_Tak_Ppt + ", r_Tak_Ppt="
				+ r_Tak_Ppt + ", u_Tak_Ppt=" + u_Tak_Ppt + ", i_Tak_Ppt="
				+ i_Tak_Ppt + ", z_Tak_Ppt=" + z_Tak_Ppt + ", e_Tak_Ppt="
				+ e_Tak_Ppt + ", a_Tak_Ppt=" + a_Tak_Ppt + ", o_Tak_Ppt="
				+ o_Tak_Ppt + ", s_Tak_Ppt=" + s_Tak_Ppt + ", h_Tak_Ppt="
				+ h_Tak_Ppt + ", x_Tak_Ppt=" + x_Tak_Ppt + ", children="
				+ children + ", yBFare=" + yBFare + ", sailingDistance="
				+ sailingDistance + ", dta_Sce=" + dta_Sce + ", dta_Dte="
				+ dta_Dte + ", data_Date=" + data_Date + ", company=" + company
				+ ", itia=" + itia + ", month=" + month + ", eterm_account_id="
				+ eterm_account_id + ", dpt_Cha_Nm=" + dpt_Cha_Nm
				+ ", arrv_Cha_Nm=" + arrv_Cha_Nm + ", allData=" + allData
				+ ", comeAndGo=" + comeAndGo + "]";
	}

	 
	
}
