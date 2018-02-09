package org.ldd.ssm.crm.query;

public class SortQuery {
	private String itia;
	private String date;
	private String field;
	private String sortWay;
	private String year;
	private String month;
	public String getItia() {
		return itia;
	}
	public void setItia(String itia) {
		this.itia = itia;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getSortWay() {
		return sortWay;
	}
	public void setSortWay(String sortWay) {
		this.sortWay = sortWay;
	}
	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SortQuery [itia=" + itia + ", date=" + date + ", field="
				+ field + ", sortWay=" + sortWay + ", year=" + year
				+ ", month=" + month + "]";
	}
	
}
