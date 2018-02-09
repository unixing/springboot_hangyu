package org.ldd.ssm.crm.query;
/**
 * 高级查询加分页的封装对象
 */
public class OperatorLogQuery {
	
	private String opresult;//操作结果
	private String logtype;//日志类型
	private String logrank;//日志级别
	private String opName;//操作人
	private String opIp;//操作IP
	private String startDate;//开始时间
	private String endDate;//结束时间
	//分页用的字段
	private Integer offset;//当前页数
	private Integer limit;//当前页数量
	/**
	 * @return the opresult
	 */
	public String getOpresult() {
		return opresult;
	}
	/**
	 * @param opresult the opresult to set
	 */
	public void setOpresult(String opresult) {
		this.opresult = opresult;
	}
	/**
	 * @return the logtype
	 */
	public String getLogtype() {
		return logtype;
	}
	/**
	 * @param logtype the logtype to set
	 */
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	/**
	 * @return the logrank
	 */
	public String getLogrank() {
		return logrank;
	}
	/**
	 * @param logrank the logrank to set
	 */
	public void setLogrank(String logrank) {
		this.logrank = logrank;
	}
	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/**
	 * @return the opName
	 */
	public String getOpName() {
		return opName;
	}
	/**
	 * @param opName the opName to set
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}
	/**
	 * @return the opIp
	 */
	public String getOpIp() {
		return opIp;
	}
	/**
	 * @param opIp the opIp to set
	 */
	public void setOpIp(String opIp) {
		this.opIp = opIp;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperatorLogQuery [opresult=" + opresult + ", logtype="
				+ logtype + ", logrank=" + logrank + ", opName=" + opName
				+ ", opIp=" + opIp + ", startDate=" + startDate + ", endDate="
				+ endDate + ", offset=" + offset + ", limit=" + limit + "]";
	}
	
	
}