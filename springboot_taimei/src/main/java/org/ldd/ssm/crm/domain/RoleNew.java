package org.ldd.ssm.crm.domain;

import java.util.List;


public class RoleNew implements Cloneable{
	
	private Long id;
	
	private String sn;//编号
	
	private String name;//角色名称
	
	private String itia;
	
	private Long createId;
	
	private Employee creator;
	
	private int type;//是否具有该角色：0.否 1.是
	
	private List<MenuNew> menus;
	
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }
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
	public RoleNew(Long id) {
		super();
		this.id = id;
	}

	public RoleNew() {
		super();
	}
	
	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public String getItia() {
		return itia;
	}

	public void setItia(String itia) {
		this.itia = itia;
	}

	public Long getCreateId() {
		return createId;
	}

	public void setCreateId(Long createId) {
		this.createId = createId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<MenuNew> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuNew> menus) {
		this.menus = menus;
	}

	@Override
	public String toString() {
		return "RoleNew [id=" + id + ", sn=" + sn + ", name=" + name
				+ ", itia=" + itia + ", createId=" + createId + ", creator="
				+ creator + ", type=" + type + ", menus=" + menus + "]";
	}
}
