package org.ldd.ssm.crm.domain;

import java.util.Date;

public class UserCommitText {
    private Long id;

    private Long userCommitInfoId;

    private String phone;

    private String userText;

    private Date userUpdateDate;

    private String adminText;

    private Date adminUpdateDate;

    private String userUuid;

    private String adminUuid;

    private Long employeeAdminId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCommitInfoId() {
        return userCommitInfoId;
    }

    public void setUserCommitInfoId(Long userCommitInfoId) {
        this.userCommitInfoId = userCommitInfoId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUserText() {
        return userText;
    }

    public void setUserText(String userText) {
        this.userText = userText == null ? null : userText.trim();
    }

    public Date getUserUpdateDate() {
        return userUpdateDate;
    }

    public void setUserUpdateDate(Date userUpdateDate) {
        this.userUpdateDate = userUpdateDate;
    }

    public String getAdminText() {
        return adminText;
    }

    public void setAdminText(String adminText) {
        this.adminText = adminText == null ? null : adminText.trim();
    }

    public Date getAdminUpdateDate() {
        return adminUpdateDate;
    }

    public void setAdminUpdateDate(Date adminUpdateDate) {
        this.adminUpdateDate = adminUpdateDate;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid == null ? null : userUuid.trim();
    }

    public String getAdminUuid() {
        return adminUuid;
    }

    public void setAdminUuid(String adminUuid) {
        this.adminUuid = adminUuid == null ? null : adminUuid.trim();
    }

    public Long getEmployeeAdminId() {
        return employeeAdminId;
    }

    public void setEmployeeAdminId(Long employeeAdminId) {
        this.employeeAdminId = employeeAdminId;
    }
}