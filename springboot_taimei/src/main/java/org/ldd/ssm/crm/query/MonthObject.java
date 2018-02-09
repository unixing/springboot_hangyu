package org.ldd.ssm.crm.query;

import java.util.List;

/**
 * 高级查询加分页的封装对象
 */
public class MonthObject<T> {
	private List<T> rows;
	private Integer total ;
	private List<T> footer;
	
	public MonthObject() {
	}
	
	public MonthObject(List<T> rows, Integer total) {
		this.rows = rows;
		this.total = total;
	}

	public MonthObject(List<T> rows, Integer total, List<T> footer) {
		super();
		this.rows = rows;
		this.total = total;
		this.footer = footer;
	}


	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}
	
	@Override
	public String toString() {
		return "DOWObject [rows=" + rows + ", total=" + total + ", footer="
				+ footer + "]";
	}
	
}