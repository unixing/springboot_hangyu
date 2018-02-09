package org.ldd.ssm.crm.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 指令集对象
 */
public class Instructions {
	private long id;
	private String Isc_Ist; // 指令集合
	private String Isc_Tem; // 指令所属公司
	private String Etm_Usr; // eterm账号
	private String Etm_Psw; // eterm密码
	private Integer Etm_Pot; // Eterm端口
	private String Etm_IP; // eterm的ip
	private String Off_ID; //office号
	private String Off_Pwd; //office密码
	private String Acc_Lvl; //office等级
	private int state;
	private String employee_id;
	private boolean Is_Four;
	private String itia;
	private int cct_Ron;//重启方式
	private String dte_Aic_Rrt;//重新开启时间
	private int aic_Mal;//自动或者手动
	private Date aic_Tie;//自动采集时间
	private int scope;
	private String si;
	private String date;
	
	
	
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSi() {
		return si;
	}

	public void setSi(String si) {
		this.si = si;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public int getCct_Ron() {
		return cct_Ron;
	}

	public void setCct_Ron(int cct_Ron) {
		this.cct_Ron = cct_Ron;
	}

	public int getAic_Mal() {
		return aic_Mal;
	}

	public void setAic_Mal(int aic_Mal) {
		this.aic_Mal = aic_Mal;
	}

	public String getDte_Aic_Rrt() {
		return dte_Aic_Rrt;
	}

	public void setDte_Aic_Rrt(String dte_Aic_Rrt) {
		this.dte_Aic_Rrt = dte_Aic_Rrt;
	}
	@DateTimeFormat(pattern="HH:mm:ss")
	@JsonFormat(pattern="HH:mm:ss",timezone="GMT+8")
	public Date getAic_Tie() {
		return aic_Tie;
	}

	public void setAic_Tie(Date aic_Tie) {
		this.aic_Tie = aic_Tie;
	}

	public String getItia() {
		return itia;
	}

	public void setItia(String itia) {
		this.itia = itia;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getOff_ID() {
		return Off_ID;
	}

	public void setOff_ID(String off_ID) {
		Off_ID = off_ID;
	}

	public String getOff_Pwd() {
		return Off_Pwd;
	}

	public void setOff_Pwd(String off_Pwd) {
		Off_Pwd = off_Pwd;
	}

	public String getAcc_Lvl() {
		return Acc_Lvl;
	}

	public void setAcc_Lvl(String acc_Lvl) {
		Acc_Lvl = acc_Lvl;
	}

	public String getIsc_Ist() {
		return Isc_Ist;
	}

	public void setIsc_Ist(String isc_Ist) {
		Isc_Ist = isc_Ist;
	}

	public String getIsc_Tem() {
		return Isc_Tem;
	}

	public void setIsc_Tem(String isc_Tem) {
		Isc_Tem = isc_Tem;
	}

	public String getEtm_Usr() {
		return Etm_Usr;
	}

	public void setEtm_Usr(String etm_Usr) {
		Etm_Usr = etm_Usr;
	}

	public String getEtm_Psw() {
		return Etm_Psw;
	}

	public void setEtm_Psw(String etm_Psw) {
		Etm_Psw = etm_Psw;
	}

	public Integer getEtm_Pot() {
		return Etm_Pot;
	}

	public void setEtm_Pot(Integer etm_Pot) {
		Etm_Pot = etm_Pot;
	}

	public String getEtm_IP() {
		return Etm_IP;
	}

	public void setEtm_IP(String etm_IP) {
		Etm_IP = etm_IP;
	}

	public boolean isIs_Four() {
		return Is_Four;
	}

	public void setIs_Four(boolean is_Four) {
		Is_Four = is_Four;
	}

	@Override
	public String toString() {
		return "Instructions [id=" + id + ", Isc_Ist=" + Isc_Ist + ", Isc_Tem="
				+ Isc_Tem + ", Etm_Usr=" + Etm_Usr + ", Etm_Psw=" + Etm_Psw
				+ ", Etm_Pot=" + Etm_Pot + ", Etm_IP=" + Etm_IP + ", Off_ID="
				+ Off_ID + ", Off_Pwd=" + Off_Pwd + ", Acc_Lvl=" + Acc_Lvl
				+ ", state=" + state + ", employee_id=" + employee_id
				+ ", Is_Four=" + Is_Four + ", itia=" + itia + ", cct_Ron="
				+ cct_Ron + ", dte_Aic_Rrt=" + dte_Aic_Rrt + ", aic_Mal="
				+ aic_Mal + ", aic_Tie=" + aic_Tie + ", scope=" + scope
				+ ", si=" + si + ", date=" + date + "]";
	}

}
