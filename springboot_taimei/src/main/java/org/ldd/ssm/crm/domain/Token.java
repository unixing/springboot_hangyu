package org.ldd.ssm.crm.domain;

public class Token {
	private long id;
	private String uuid;
	private long employee_id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public long getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(long employee_id) {
		this.employee_id = employee_id;
	}
	@Override
	public String toString() {
		return "Token [id=" + id + ", uuid=" + uuid + ", employee_id="
				+ employee_id + "]";
	}
	
}
