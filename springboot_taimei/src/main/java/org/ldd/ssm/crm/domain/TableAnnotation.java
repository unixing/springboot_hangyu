package org.ldd.ssm.crm.domain;

public class TableAnnotation {
	private String table_name;
	private String column_name;
	private String column_comment;
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_comment() {
		return column_comment;
	}
	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}
	@Override
	public String toString() {
		return "TableAnnotation [table_name=" + table_name + ", column_name="
				+ column_name + ", column_comment=" + column_comment + "]";
	}
	
}
