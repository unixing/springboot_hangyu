package org.ldd.ssm.crm.query;


/**
 * 销售数据查询参数
 * @author wxm
 *
 * @date 2017-3-7
 */
public class SalesDateQuery {
	
	private String startTime;//开始时间（默认时间 航班号 由前台传入）
	private String endTime;//结束时间
	private String flightNum;//航班号 传入参数  得到航班对
	private String FltRteCd;//航线
	private String FltRteCdOther;//航线
	private String leg;//航段号
	private String order="flightDate"; //排序字段
	private String sort="desc";//升序降序 asc desc
	private int page=1;//默认
	private int begin;//分页开始页
	private int rows=35;//每页条数
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getBegin() {
		return (page-1)*this.rows;
	}
	public void setBegin(int begin) {
		this.begin=begin;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public  String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getFlightNum() {
		return flightNum;
	}
	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}
	public String getFltRteCd() {
		return FltRteCd;
	}
	public void setFltRteCd(String fltRteCd) {
		FltRteCd = fltRteCd;
	}
	public String getFltRteCdOther() {
		return FltRteCdOther;
	}
	public void setFltRteCdOther(String fltRteCdOther) {
		FltRteCdOther = fltRteCdOther;
	}
	
	
}
