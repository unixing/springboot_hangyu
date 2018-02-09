package org.ldd.ssm.crm.domain;
/**
 * 某天航班动态的实体类
 * @Title:FltDyDetial 
 * @Description:
 * @author taimei-gds 
 * @date 2017-9-13 下午1:49:43
 */
public class FltDyDetial {
	private String flyDate;//飞行时间
	private String status;//状态，0表示正常，1表示延误，2表示取消，3表示延误又取消
	private String content;//内容
	/**
	 * @return the flyDate
	 */
	public String getFlyDate() {
		return flyDate;
	}
	/**
	 * @param flyDate the flyDate to set
	 */
	public void setFlyDate(String flyDate) {
		this.flyDate = flyDate;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "FltDyDetial [flyDate=" + flyDate + ", status=" + status
				+ ", content=" + content + "]";
	}
	
	
}
