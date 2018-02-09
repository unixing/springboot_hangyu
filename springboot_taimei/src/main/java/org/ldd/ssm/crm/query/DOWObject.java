package org.ldd.ssm.crm.query;

import java.util.List;

/**
 * 高级查询加分页的封装对象
 */
public class DOWObject<T> {
	private List<T> rows;
	private Integer total ;
	private List<T> fitFooter;
	
	public DOWObject() {
	}
	
	public DOWObject(List<T> rows, Integer total) {
		this.rows = rows;
		this.total = total;
	}

	public DOWObject(List<T> rows, Integer total, List<T> fitFooter) {
		super();
		this.rows = rows;
		this.total = total;
		this.fitFooter = fitFooter;
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

	public List<T> getFitFooter() {
		return fitFooter;
	}

	public void setFitFooter(List<T> fitFooter) {
		this.fitFooter = fitFooter;
	}

	@Override
	public String toString() {
		return "DOWObject [rows=" + rows + ", total=" + total + ", fitFooter="
				+ fitFooter + "]";
	}
	
}