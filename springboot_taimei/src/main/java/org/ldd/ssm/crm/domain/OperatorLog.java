package org.ldd.ssm.crm.domain;

import java.util.Date;

/**
 * 操作日志实体类
 * @Title:OperatorLog 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-8 上午10:41:58
 */
public class OperatorLog{	
	
	private int id;//主键
	private int userId;//登陆人员ID
	private String userName;//登陆用户名
	private String opTime;//操作时间
	private String opIp;//操作IP 
	private String methodCode;//操作的方法的代码
	private String methodName;//操作的方法的注释
	private String opResult;//操作结果
	private String logType;//操作类型(1、登陆，2、功能访问，3、数据操作，4、编辑修改，5、系统日志)
	private String logRank;//操作级别（1、信息，2、警告，3、错误，4、调试）
	private String params;//穿入参数
	private String startTime;//查询用的开始时间
	private String endTime;//查询用的结束时间
	private String serverIP;//服务器IP;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * @return the opTime
	 */
	public String getOpTime() {
		return opTime;
	}
	/**
	 * @param opTime the opTime to set
	 */
	public void setOpTime(String opTime) {
		this.opTime = opTime;
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
	 * @return the methodCode
	 */
	public String getMethodCode() {
		return methodCode;
	}
	/**
	 * @param methodCode the methodCode to set
	 */
	public void setMethodCode(String methodCode) {
		this.methodCode = methodCode;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the opResult
	 */
	public String getOpResult() {
		return opResult;
	}
	/**
	 * @param opResult the opResult to set
	 */
	public void setOpResult(String opResult) {
		this.opResult = opResult;
	}
	/**
	 * @return the logType
	 */
	public String getLogType() {
		return logType;
	}
	/**
	 * @param logType the logType to set
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}
	/**
	 * @return the logRank
	 */
	public String getLogRank() {
		return logRank;
	}
	/**
	 * @param logRank the logRank to set
	 */
	public void setLogRank(String logRank) {
		this.logRank = logRank;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the params
	 */
	public String getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(String params) {
		this.params = params;
	}
	/**
	 * @return the serverIP
	 */
	public String getServerIP() {
		return serverIP;
	}
	/**
	 * @param serverIP the serverIP to set
	 */
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OperatorLog [id=" + id + ", userId=" + userId + ", userName="
				+ userName + ", opTime=" + opTime + ", opIp=" + opIp
				+ ", methodCode=" + methodCode + ", methodName=" + methodName
				+ ", opResult=" + opResult + ", logType=" + logType
				+ ", logRank=" + logRank + ", params=" + params
				+ ", startTime=" + startTime + ", endTime=" + endTime
				+ ", serverIP=" + serverIP + "]";
	}
	
	
}
