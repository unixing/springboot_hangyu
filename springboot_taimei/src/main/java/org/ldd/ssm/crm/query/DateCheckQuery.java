package org.ldd.ssm.crm.query;

public class DateCheckQuery {
	private String dpt_AirPt_Cd;// 机场名字
	private String arrv_Airpt_Cd;// 机场名字
	private String startDate;
	private String endDate;
	private String flt_Nbr;
	private String cpy_Nm;
	private String flt_Rte_Cd;
	private String tal_Nbr_Set;
	private String pgs_Per_Cls;
	private String grp_Nbr;
	private String ech_Cls_Grp;
	private String tal_Nbr;
	private String set_Ktr_Ine;
	private String two_Tak_Ppt;
	private String ful_Pce_Ppt;
	private String nne_Dnt_Ppt;
	private String eht_Five_Dnt_Ppt;
	private String eht_Dnt_Ppt;
	private String sen_Five_Dnt_Ppt;
	private String sen_Dnt_Ppt;
	private String six_Five_Dnt_Ppt;
	private String six_Dnt_Ppt;
	private String fve_Fve_Dnt_Ppt;
	private String fve_Dnt_Ppt;
	private String fur_Fve_Dnt_Ppt;
	private String fur_Dnt_Ppt;
	private String sal_Tak_Ppt;
	private String r_Tak_Ppt;
	private String u_Tak_Ppt;
	private String i_Tak_Ppt;
	private String z_Tak_Ppt;
	private String e_Tak_Ppt;
	private String a_Tak_Ppt;
	private String o_Tak_Ppt;
	private String s_Tak_Ppt;
	private String h_Tak_Ppt;
	private String x_Tak_Ppt;
	private String children;
	private String dta_Sce;
	// 分页用的字段
	private Integer offset;// 当前页数
	private Integer limit;// 当前页数量

	public Integer getBeginIndex() {
		return offset * limit;
	}

	public String getFlt_Nbr() {
		return flt_Nbr;
	}

	public String getSen_Dnt_Ppt() {
		return sen_Dnt_Ppt;
	}

	public String getSix_Dnt_Ppt() {
		return six_Dnt_Ppt;
	}

	public String getChildren() {
		return children;
	}

	public void setChildren(String children) {
		this.children = children;
	}

	public String getDta_Sce() {
		return dta_Sce;
	}

	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}

	public void setSix_Dnt_Ppt(String six_Dnt_Ppt) {
		this.six_Dnt_Ppt = six_Dnt_Ppt;
	}

	public void setSen_Dnt_Ppt(String sen_Dnt_Ppt) {
		this.sen_Dnt_Ppt = sen_Dnt_Ppt;
	}

	public void setFlt_Nbr(String flt_Nbr) {
		this.flt_Nbr = flt_Nbr;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setRows(Integer rows) {
		this.limit = rows;
	}

	public void setPage(Integer page) {
		this.offset = page;
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

	public String getTal_Nbr_Set() {
		return tal_Nbr_Set;
	}

	public void setTal_Nbr_Set(String tal_Nbr_Set) {
		this.tal_Nbr_Set = tal_Nbr_Set;
	}

	public String getPgs_Per_Cls() {
		return pgs_Per_Cls;
	}

	public void setPgs_Per_Cls(String pgs_Per_Cls) {
		this.pgs_Per_Cls = pgs_Per_Cls;
	}

	public String getGrp_Nbr() {
		return grp_Nbr;
	}

	public void setGrp_Nbr(String grp_Nbr) {
		this.grp_Nbr = grp_Nbr;
	}

	public String getEch_Cls_Grp() {
		return ech_Cls_Grp;
	}

	public void setEch_Cls_Grp(String ech_Cls_Grp) {
		this.ech_Cls_Grp = ech_Cls_Grp;
	}

	public String getTal_Nbr() {
		return tal_Nbr;
	}

	public void setTal_Nbr(String tal_Nbr) {
		this.tal_Nbr = tal_Nbr;
	}

	public String getSet_Ktr_Ine() {
		return set_Ktr_Ine;
	}

	public void setSet_Ktr_Ine(String set_Ktr_Ine) {
		this.set_Ktr_Ine = set_Ktr_Ine;
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

	public String getSix_Five_Dnt_Ppt() {
		return six_Five_Dnt_Ppt;
	}

	public void setSix_Five_Dnt_Ppt(String six_Five_Dnt_Ppt) {
		this.six_Five_Dnt_Ppt = six_Five_Dnt_Ppt;
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

	public String getSal_Tak_Ppt() {
		return sal_Tak_Ppt;
	}

	public void setSal_Tak_Ppt(String sal_Tak_Ppt) {
		this.sal_Tak_Ppt = sal_Tak_Ppt;
	}

	public String getR_Tak_Ppt() {
		return r_Tak_Ppt;
	}

	public void setR_Tak_Ppt(String r_Tak_Ppt) {
		this.r_Tak_Ppt = r_Tak_Ppt;
	}

	public String getU_Tak_Ppt() {
		return u_Tak_Ppt;
	}

	public void setU_Tak_Ppt(String u_Tak_Ppt) {
		this.u_Tak_Ppt = u_Tak_Ppt;
	}

	public String getI_Tak_Ppt() {
		return i_Tak_Ppt;
	}

	public void setI_Tak_Ppt(String i_Tak_Ppt) {
		this.i_Tak_Ppt = i_Tak_Ppt;
	}

	public String getZ_Tak_Ppt() {
		return z_Tak_Ppt;
	}

	public void setZ_Tak_Ppt(String z_Tak_Ppt) {
		this.z_Tak_Ppt = z_Tak_Ppt;
	}

	public String getE_Tak_Ppt() {
		return e_Tak_Ppt;
	}

	public void setE_Tak_Ppt(String e_Tak_Ppt) {
		this.e_Tak_Ppt = e_Tak_Ppt;
	}

	public String getA_Tak_Ppt() {
		return a_Tak_Ppt;
	}

	public void setA_Tak_Ppt(String a_Tak_Ppt) {
		this.a_Tak_Ppt = a_Tak_Ppt;
	}

	public String getO_Tak_Ppt() {
		return o_Tak_Ppt;
	}

	public void setO_Tak_Ppt(String o_Tak_Ppt) {
		this.o_Tak_Ppt = o_Tak_Ppt;
	}

	public String getS_Tak_Ppt() {
		return s_Tak_Ppt;
	}

	public void setS_Tak_Ppt(String s_Tak_Ppt) {
		this.s_Tak_Ppt = s_Tak_Ppt;
	}

	public String getH_Tak_Ppt() {
		return h_Tak_Ppt;
	}

	public void setH_Tak_Ppt(String h_Tak_Ppt) {
		this.h_Tak_Ppt = h_Tak_Ppt;
	}

	public String getX_Tak_Ppt() {
		return x_Tak_Ppt;
	}

	public void setX_Tak_Ppt(String x_Tak_Ppt) {
		this.x_Tak_Ppt = x_Tak_Ppt;
	}

	@Override
	public String toString() {
		return "DateCheckQuery [dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", startDate="
				+ startDate + ", endDate=" + endDate + ", flt_Nbr=" + flt_Nbr
				+ ", cpy_Nm=" + cpy_Nm + ", flt_Rte_Cd=" + flt_Rte_Cd
				+ ", tal_Nbr_Set=" + tal_Nbr_Set + ", pgs_Per_Cls="
				+ pgs_Per_Cls + ", grp_Nbr=" + grp_Nbr + ", ech_Cls_Grp="
				+ ech_Cls_Grp + ", tal_Nbr=" + tal_Nbr + ", set_Ktr_Ine="
				+ set_Ktr_Ine + ", two_Tak_Ppt=" + two_Tak_Ppt
				+ ", ful_Pce_Ppt=" + ful_Pce_Ppt + ", nne_Dnt_Ppt="
				+ nne_Dnt_Ppt + ", eht_Five_Dnt_Ppt=" + eht_Five_Dnt_Ppt
				+ ", eht_Dnt_Ppt=" + eht_Dnt_Ppt + ", sen_Five_Dnt_Ppt="
				+ sen_Five_Dnt_Ppt + ", sen_Dnt_Ppt=" + sen_Dnt_Ppt
				+ ", six_Five_Dnt_Ppt=" + six_Five_Dnt_Ppt + ", six_Dnt_Ppt="
				+ six_Dnt_Ppt + ", fve_Fve_Dnt_Ppt=" + fve_Fve_Dnt_Ppt
				+ ", fve_Dnt_Ppt=" + fve_Dnt_Ppt + ", fur_Fve_Dnt_Ppt="
				+ fur_Fve_Dnt_Ppt + ", fur_Dnt_Ppt=" + fur_Dnt_Ppt
				+ ", sal_Tak_Ppt=" + sal_Tak_Ppt + ", r_Tak_Ppt=" + r_Tak_Ppt
				+ ", u_Tak_Ppt=" + u_Tak_Ppt + ", i_Tak_Ppt=" + i_Tak_Ppt
				+ ", z_Tak_Ppt=" + z_Tak_Ppt + ", e_Tak_Ppt=" + e_Tak_Ppt
				+ ", a_Tak_Ppt=" + a_Tak_Ppt + ", o_Tak_Ppt=" + o_Tak_Ppt
				+ ", s_Tak_Ppt=" + s_Tak_Ppt + ", h_Tak_Ppt=" + h_Tak_Ppt
				+ ", x_Tak_Ppt=" + x_Tak_Ppt + ", children=" + children
				+ ", dta_Sce=" + dta_Sce + ", offset=" + offset + ", limit="
				+ limit + "]";
	}

}
