package org.ldd.ssm.crm.query;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 *系统日志的高级查询对象
 */
public class SystemLogQuery {

	private String searchKey;//关键字
	private Date inputTimeStart;//开始时间
	private Date inputTimeEnd;//结束时间
	// 分页
	private Integer page = 1;// 当前页码
	private Integer rows = 10;//  每页长度
	public Integer getBeginIndex() {
		return (page - 1)* rows;
	}
	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getInputTimeStart() {
		return inputTimeStart;
	}

	public void setInputTimeStart(Date inputTimeStart) {
		this.inputTimeStart = inputTimeStart;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
	public Date getInputTimeEnd() {
		return inputTimeEnd;
	}

	public void setInputTimeEnd(Date inputTimeEnd) {
		this.inputTimeEnd = inputTimeEnd;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "SystemLogQuery [searchKey=" + searchKey + ", inputTimeStart="
				+ inputTimeStart + ", inputTimeEnd=" + inputTimeEnd + ", page="
				+ page + ", rows=" + rows + "]";
	}

}
