package org.ldd.ssm.crm.query;

import java.util.Date;
import java.util.List;

import org.ldd.ssm.crm.domain.UserCommitBlob;

public class UserCommit {
    private Long id;

    private Long userCommitInfoId;

    private String phone;

    private String userText;

    private Date userUpdateDate;
    
    private String userUpdateDateFormat;
    
    private List<byte[]> userImgs;
    
    private List<byte[]> adminImgs;
    
    private List<UserCommitBlob> userImgsList;
    
    private List<UserCommitBlob> adminImgsList;
    
    //usercommitInfo
    private Date textDate;
    
    private String textDateFormat;

    private String title;

    private String state;
    
    private String stateCode;//状态码

    private Long employeeId;
    //usercommitInfo
    
    private String adminText;

    private Date adminUpdateDate;
    
    private String adminUpdateDateFormat;

    private String userUuid;

    private String adminUuid;

    private Long employeeAdminId;

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public Date getTextDate() {
		return textDate;
	}

	public void setTextDate(Date textDate) {
		this.textDate = textDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTextDateFormat() {
		return textDateFormat;
	}

	public void setTextDateFormat(String textDateFormat) {
		this.textDateFormat = textDateFormat;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public List<byte[]> getUserImgs() {
		return userImgs;
	}

	public void setUserImgs(List<byte[]> userImgs) {
		this.userImgs = userImgs;
	}

	public List<byte[]> getAdminImgs() {
		return adminImgs;
	}

	public void setAdminImgs(List<byte[]> adminImgs) {
		this.adminImgs = adminImgs;
	}

	public List<UserCommitBlob> getUserImgsList() {
		return userImgsList;
	}

	public void setUserImgsList(List<UserCommitBlob> userImgsList) {
		this.userImgsList = userImgsList;
	}

	public List<UserCommitBlob> getAdminImgsList() {
		return adminImgsList;
	}

	public void setAdminImgsList(List<UserCommitBlob> adminImgsList) {
		this.adminImgsList = adminImgsList;
	}

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
		this.phone = phone;
	}

	public String getUserText() {
		return userText;
	}

	public void setUserText(String userText) {
		this.userText = userText;
	}

	public String getAdminText() {
		return adminText;
	}

	public void setAdminText(String adminText) {
		this.adminText = adminText;
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
		this.userUuid = userUuid;
	}

	public String getAdminUuid() {
		return adminUuid;
	}

	public void setAdminUuid(String adminUuid) {
		this.adminUuid = adminUuid;
	}

	public Long getEmployeeAdminId() {
		return employeeAdminId;
	}

	public void setEmployeeAdminId(Long employeeAdminId) {
		this.employeeAdminId = employeeAdminId;
	}

	public Date getUserUpdateDate() {
		return userUpdateDate;
	}

	public void setUserUpdateDate(Date userUpdateDate) {
		this.userUpdateDate = userUpdateDate;
	}

	public String getUserUpdateDateFormat() {
		return userUpdateDateFormat;
	}

	public void setUserUpdateDateFormat(String userUpdateDateFormat) {
		this.userUpdateDateFormat = userUpdateDateFormat;
	}

	public String getAdminUpdateDateFormat() {
		return adminUpdateDateFormat;
	}

	public void setAdminUpdateDateFormat(String adminUpdateDateFormat) {
		this.adminUpdateDateFormat = adminUpdateDateFormat;
	}

}