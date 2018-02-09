package org.ldd.ssm.crm.domain;

public class Datasupplier {
    private Long id;

    private Company company;

    private String datSreWay;

    private String datSrePhe;

    private Long employeeId;
    
    private Employee employee;
    
    private Long datSreCpyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatSreWay() {
        return datSreWay;
    }

    public void setDatSreWay(String datSreWay) {
        this.datSreWay = datSreWay == null ? null : datSreWay.trim();
    }

    public String getDatSrePhe() {
        return datSrePhe;
    }

    public void setDatSrePhe(String datSrePhe) {
        this.datSrePhe = datSrePhe == null ? null : datSrePhe.trim();
    }

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getDatSreCpyId() {
		return datSreCpyId;
	}

	public void setDatSreCpyId(Long datSreCpyId) {
		this.datSreCpyId = datSreCpyId;
	}

}