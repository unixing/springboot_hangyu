package org.ldd.ssm.crm.domain;


public class Role {
	
	private Long id;
	
	private String sn;//编号
	
	private String name;//角色名称
	
	private Long companyId;
	
	private Long createId;
	
	private Company company;
	
	private Employee creator;
	
	private int type;//是否具有属于该角色：0.否 1.是

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
	public Role(Long id) {
		super();
		this.id = id;
	}

	public Role() {
		super();
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Employee getCreator() {
		return creator;
	}

	public void setCreator(Employee creator) {
		this.creator = creator;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
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

	@Override
	public String toString() {
		return "Role [id=" + id + ", sn=" + sn + ", name=" + name+"]";
	}
}
