package org.ldd.ssm.crm.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单
 */
public class Menu implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long id;//菜单id
	private String sn;//菜单编号
	private String name;//菜单名称
	private String icon;//菜单图片
	private String url;//链接地址
	private String intro;//菜单简介
	private Long parentId;
	private int type;//是否具有菜单权限：0否，1是
	private List<Menu> chiledren;
	private List<Menu> parent; //上级菜单  多对一
	private List<Resource> resources;
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
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public List<Menu> getParent() {
		return parent;
	}
	public void setParent(List<Menu> parent) {
		this.parent = parent;
	}
	
	public List<Menu> getChiledren() {
		return chiledren;
	}
	public void setChiledren(List<Menu> chiledren) {
		this.chiledren = chiledren;
	}
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public List<Resource> getResources() {
		return resources;
	}
	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}
	@Override
	public String toString() {
		return "Menu [id=" + id + ", sn=" + sn + ", name=" + name + ", icon="
				+ icon + ", url=" + url + ", intro=" + intro + ", parent="
				+ parent + "]";
	}
	
}
