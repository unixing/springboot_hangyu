package org.ldd.ssm.crm.domain;

public class EmployeeRole {
    private Long employeeId;

    private String itia;

    private Long roleId;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getItia() {
        return itia;
    }

    public void setItia(String itia) {
        this.itia = itia == null ? null : itia.trim();
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}