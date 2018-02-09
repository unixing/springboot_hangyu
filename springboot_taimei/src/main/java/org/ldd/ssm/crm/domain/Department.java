package org.ldd.ssm.crm.domain;

public class Department {
    private Long id;

    private String dptNm;
    
    private Long parentId;
    
    private Long managerId;
    
    private Long companyId;
    
    private Department parent;
    
    private Employee manager;
    
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDptNm() {
        return dptNm;
    }

    public void setDptNm(String dptNm) {
        this.dptNm = dptNm == null ? null : dptNm.trim();
    }

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}