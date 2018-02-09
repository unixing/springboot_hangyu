package org.ldd.ssm.crm.domain;

public class ResourceNew {

	private Long id;
	private String name;
	private String url;
	private Long menuId;
	private int type;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public Long getMenuId() {
		return menuId;
	}
	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}
	@Override
	public String toString() {
		return "Resource [id=" + id + ", name=" + name + ", url=" + url
				+ ", menuId=" + menuId + ", type=" + type + "]";
	}

}
