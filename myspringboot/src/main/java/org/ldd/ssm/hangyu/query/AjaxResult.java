package org.ldd.ssm.hangyu.query;


import java.io.Serializable;

public class AjaxResult implements Serializable {
	// 响应是否成功
	private boolean success = true;
	// 响应消息
	private String message="操作成功！！";
	// 错误码（前台可以根据错误码，进行特殊处理）
	private Integer errorCode = -99;
	/**
	 *  成功时
	 */
	public AjaxResult() {
		super();
	}
	/**
	 * 成功时,自定义消息
	 * @param message  成功后的提示消息
	 */
	public AjaxResult(String message) {
		super();
		this.message = message;
	}
	/**
	 *  错误时
	 * @param message  错误消息
	 * @param errorCode 错误码
	 */
	public AjaxResult( String message, Integer errorCode) {
		super();
		this.success = false;
		this.message = message;
		this.errorCode = errorCode;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	@Override
	public String toString() {
		return "AjaxResult [success=" + success + ", message=" + message
				+ ", errorCode=" + errorCode + ", isSuccess()=" + isSuccess()
				+ ", getMessage()=" + getMessage() + ", getErrorCode()="
				+ getErrorCode() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
