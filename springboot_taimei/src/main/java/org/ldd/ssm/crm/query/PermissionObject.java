package org.ldd.ssm.crm.query;

import java.util.List;

/**
 * 高级查询加分页的封装对象
 */
public class PermissionObject<T> {
	private List<T> rows;
	private Integer total ;
	
	public PermissionObject() {
	}
	
	public PermissionObject(List<T> rows, Integer total) {
		this.rows = rows;
		this.total = total;
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
	@Override
	public String toString() {
		return "SystemDictionaryObject [rows=" + rows + ", total=" + total
				+ "]";
	}
	
}