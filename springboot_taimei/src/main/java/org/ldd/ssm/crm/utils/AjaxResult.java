package org.ldd.ssm.crm.utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.ldd.ssm.crm.domain.ETermInfo;
import org.ldd.ssm.crm.domain.Yesterday_ZD;

public class AjaxResult {
	// 响应是否成功
	private boolean success = true;
	private List<List<Yesterday_ZD>> list;
	private List<ETermInfo> eTermInfos;
	
	
	public List<ETermInfo> geteTermInfos() {
		return eTermInfos;
	}

	public void seteTermInfos(List<ETermInfo> eTermInfos) {
		this.eTermInfos = eTermInfos;
	}

	// 响应消息
	private String message="操作成功！！";
	private Set<String> set = new HashSet<String>();
	
	// 错误码（前台可以根据错误码，进行特殊处理）
	private Integer errorCode = -99;

	@Override
	public String toString() {
		return "AjaxResult [success=" + success + ", list=" + list
				+ ", message=" + message + ", set=" + set + ", errorCode="
				+ errorCode + "]";
	}

	public AjaxResult(List<List<Yesterday_ZD>> list) {
		super();
		this.list = list;
	}

	public List<List<Yesterday_ZD>> getList() {
		return list;
	}

	public void setList(List<List<Yesterday_ZD>> list) {
		this.list = list;
	}

	public Set<String> getSet() {
		return set;
	}

	public void setSet(Set<String> set) {
		this.set = set;
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

	public AjaxResult(Set<String> set) {
		super();
		this.set = set;
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
}
