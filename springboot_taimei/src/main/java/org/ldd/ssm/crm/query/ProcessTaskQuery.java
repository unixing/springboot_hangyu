package org.ldd.ssm.crm.query;

import java.util.Arrays;


/**
 * 高级查询加分页的封装对象
 */
public class ProcessTaskQuery {
	private String dpt_AirPt_Cd;//机场名字
	private String arrv_Airpt_Cd;//机场名字
	private String dpt_AirPt_Cd_itia;//机场三字码
	private String arrv_Airpt_Cd_itia;//机场三字码
	private String goOrReturn;
	private String get_tim;
	private String mu;
	private String fltNbrSort;
	private String dptTmSort;
	private String arrvTmSort;
	private String dptTmStart;
	private String dptTmEnd;
	private String arrvTmStart;
	private String arrvTmEnd;
	private String[] fieldArray;
	private int sorttype;
	//分页用的字段
	private Integer offset;//当前页数
	private Integer limit;//当前页数量
	
	
	public Integer getBeginIndex() {
		return offset * limit;
	}
	
	public String getGet_tim() {
		return get_tim;
	}

	public String getMu() {
		return mu;
	}

	public void setMu(String mu) {
		this.mu = mu;
	}

	public void setGet_tim(String get_tim) {
		this.get_tim = get_tim;
	}

	public String getGoOrReturn() {
		return goOrReturn;
	}

	public void setGoOrReturn(String goOrReturn) {
		this.goOrReturn = goOrReturn;
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
	public void setRows(Integer rows){
		this.limit = rows;
	}
	public void setPage(Integer page){
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

	public String getDpt_AirPt_Cd_itia() {
		return dpt_AirPt_Cd_itia;
	}

	public void setDpt_AirPt_Cd_itia(String dpt_AirPt_Cd_itia) {
		this.dpt_AirPt_Cd_itia = dpt_AirPt_Cd_itia;
	}

	public String getArrv_Airpt_Cd_itia() {
		return arrv_Airpt_Cd_itia;
	}

	public void setArrv_Airpt_Cd_itia(String arrv_Airpt_Cd_itia) {
		this.arrv_Airpt_Cd_itia = arrv_Airpt_Cd_itia;
	}

	public String getFltNbrSort() {
		return fltNbrSort;
	}

	public void setFltNbrSort(String fltNbrSort) {
		this.fltNbrSort = fltNbrSort;
	}

	public String getDptTmSort() {
		return dptTmSort;
	}

	public void setDptTmSort(String dptTmSort) {
		this.dptTmSort = dptTmSort;
	}

	public String getArrvTmSort() {
		return arrvTmSort;
	}

	public void setArrvTmSort(String arrvTmSort) {
		this.arrvTmSort = arrvTmSort;
	}

	public String getDptTmStart() {
		return dptTmStart;
	}

	public void setDptTmStart(String dptTmStart) {
		this.dptTmStart = dptTmStart;
	}

	public String getDptTmEnd() {
		return dptTmEnd;
	}

	public void setDptTmEnd(String dptTmEnd) {
		this.dptTmEnd = dptTmEnd;
	}

	public String getArrvTmStart() {
		return arrvTmStart;
	}

	public void setArrvTmStart(String arrvTmStart) {
		this.arrvTmStart = arrvTmStart;
	}

	public String getArrvTmEnd() {
		return arrvTmEnd;
	}

	public void setArrvTmEnd(String arrvTmEnd) {
		this.arrvTmEnd = arrvTmEnd;
	}

	public String[] getFieldArray() {
		return fieldArray;
	}

	public void setFieldArray(String[] fieldArray) {
		this.fieldArray = fieldArray;
	}

	public int getSorttype() {
		return sorttype;
	}

	public void setSorttype(int sorttype) {
		this.sorttype = sorttype;
	}

	@Override
	public String toString() {
		return "ProcessTaskQuery [dpt_AirPt_Cd=" + dpt_AirPt_Cd
				+ ", arrv_Airpt_Cd=" + arrv_Airpt_Cd + ", dpt_AirPt_Cd_itia="
				+ dpt_AirPt_Cd_itia + ", arrv_Airpt_Cd_itia="
				+ arrv_Airpt_Cd_itia + ", goOrReturn=" + goOrReturn
				+ ", get_tim=" + get_tim + ", mu=" + mu + ", fltNbrSort="
				+ fltNbrSort + ", dptTmSort=" + dptTmSort + ", arrvTmSort="
				+ arrvTmSort + ", dptTmStart=" + dptTmStart + ", dptTmEnd="
				+ dptTmEnd + ", arrvTmStart=" + arrvTmStart + ", arrvTmEnd="
				+ arrvTmEnd + ", fieldArray=" + Arrays.toString(fieldArray)
				+ ", sorttype=" + sorttype + ", offset=" + offset + ", limit="
				+ limit + "]";
	}

}