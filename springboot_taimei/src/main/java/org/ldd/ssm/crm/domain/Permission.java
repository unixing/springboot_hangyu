package org.ldd.ssm.crm.domain;
/**
 * 系统权限管理
 */
public class Permission {
	private Long id;
	private String name;//权限名称
	private String resource;//资源的值
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	@Override
	public String toString() {
		return "Permission [id=" + id + ", name=" + name + ", resource="
				+ resource + "]";
	}
}
