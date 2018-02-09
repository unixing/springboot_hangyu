package org.ldd.ssm.crm.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Strategy {
	private Long id;
	private String etermName;
	private String etermPassword;
	private String etermIP;
	private String etermProt;
	private String etermFlight;
	private Date etermDate;
	private Long user_id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEtermName() {
		return etermName;
	}
	public void setEtermName(String etermName) {
		this.etermName = etermName;
	}
	public String getEtermPassword() {
		return etermPassword;
	}
	public void setEtermPassword(String etermPassword) {
		this.etermPassword = etermPassword;
	}
	public String getEtermIP() {
		return etermIP;
	}
	public void setEtermIP(String etermIP) {
		this.etermIP = etermIP;
	}
	public String getEtermProt() {
		return etermProt;
	}
	public void setEtermProt(String etermProt) {
		this.etermProt = etermProt;
	}
	public String getEtermFlight() {
		return etermFlight;
	}
	public void setEtermFlight(String etermFlight) {
		this.etermFlight = etermFlight;
	}
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Date getEtermDate() {
		return etermDate;
	}
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public void setEtermDate(Date etermDate) {
		this.etermDate = etermDate;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	@Override
	public String toString() {
		return "Strategy [id=" + id + ", etermName=" + etermName
				+ ", etermPassword=" + etermPassword + ", etermIP=" + etermIP
				+ ", etermProt=" + etermProt + ", etermFlight=" + etermFlight
				+ ", etermDate=" + etermDate + ", user_id=" + user_id + "]";
	}

}
