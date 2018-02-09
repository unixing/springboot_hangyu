package org.ldd.ssm.crm.domain;
/**
 * 数字字典名字
 */
public class SystemDictionaryItem {
	private Long id; // ID
	private Long parent_id; // 字典目录      多对一
	private String name; // 字典明细名称
	private String sn; //  字典明细序号
	private String intro; //     字典明细简介
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	

	public Long getParent_id() {
		return parent_id;
	}
	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	@Override
	public String toString() {
		return "SystemDictionaryItem [id=" + id + ", parent_id=" + parent_id
				+ ", name=" + name + ", sn=" + sn + ", intro=" + intro + "]";
	}
	
}
