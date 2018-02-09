package org.ldd.ssm.crm.domain;

import java.util.List;

/**
 * 数字字典目录
 */
public class SystemDictionary {
	private Long id;//     ID
	private String sn;//     字典目录编号
	private String name;//     字典目录名称
	private String intro;//     字典目录简介
	private Integer state = 1;//     状态               1正常 -1停用
	private List<SystemDictionaryItem> details;//     字典明细           一对多
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	public List<SystemDictionaryItem> getDetails() {
		return details;
	}
	public void setDetails(List<SystemDictionaryItem> details) {
		this.details = details;
	}
	@Override
	public String toString() {
		return "SystemDictionary [id=" + id + ", sn=" + sn + ", name=" + name
				+ ", intro=" + intro + ", state=" + state + ", details="
				+ details + "]";
	}
}
