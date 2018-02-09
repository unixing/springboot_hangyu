package org.ldd.ssm.crm.query;

public class ETermQuery {
	private String id;
	private String eTermName; //用户名
	private String password; //密码
	private String wordName; //工作号
	private String wordPassword; //工作号密码
	private String lvl;//级别
	private String ip;//ip
	private String port;//端口
	private String security;//是否安全传输
	private String si;//si指令
	private String auto;//自动采集
	private String avoidTime;//回避时间
	private String che;//是否深度采集
	private String flb_nbr1;//去程航班号1
	private String flt_rte_cd1;//去程航线1
	private String flb_nbr2;//回程航班号1
	private String flt_rte_cd2;//回程航班号1
	private String flb_nbr3;
	private String flt_rte_cd3;
	private String flb_nbr4;
	private String flt_rte_cd4;
	private String flb_nbr5;
	private String flt_rte_cd5;
	private String flb_nbr6;
	private String flt_rte_cd6;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String geteTermName() {
		return eTermName;
	}
	public void seteTermName(String eTermName) {
		this.eTermName = eTermName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getWordName() {
		return wordName;
	}
	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	public String getWordPassword() {
		return wordPassword;
	}
	public void setWordPassword(String wordPassword) {
		this.wordPassword = wordPassword;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSecurity() {
		return security;
	}
	public void setSecurity(String security) {
		this.security = security;
	}
	public String getSi() {
		return si;
	}
	public void setSi(String si) {
		this.si = si;
	}
	public String getAuto() {
		return auto;
	}
	public void setAuto(String auto) {
		this.auto = auto;
	}
	public String getAvoidTime() {
		return avoidTime;
	}
	public void setAvoidTime(String avoidTime) {
		this.avoidTime = avoidTime;
	}
	public String getChe() {
		return che;
	}
	public void setChe(String che) {
		this.che = che;
	}
	public String getFlb_nbr1() {
		return flb_nbr1;
	}
	public void setFlb_nbr1(String flb_nbr1) {
		this.flb_nbr1 = flb_nbr1;
	}
	public String getFlt_rte_cd1() {
		return flt_rte_cd1;
	}
	public void setFlt_rte_cd1(String flt_rte_cd1) {
		this.flt_rte_cd1 = flt_rte_cd1;
	}
	public String getFlb_nbr2() {
		return flb_nbr2;
	}
	public void setFlb_nbr2(String flb_nbr2) {
		this.flb_nbr2 = flb_nbr2;
	}
	public String getFlt_rte_cd2() {
		return flt_rte_cd2;
	}
	public void setFlt_rte_cd2(String flt_rte_cd2) {
		this.flt_rte_cd2 = flt_rte_cd2;
	}
	public String getFlb_nbr3() {
		return flb_nbr3;
	}
	public void setFlb_nbr3(String flb_nbr3) {
		this.flb_nbr3 = flb_nbr3;
	}
	public String getFlt_rte_cd3() {
		return flt_rte_cd3;
	}
	public void setFlt_rte_cd3(String flt_rte_cd3) {
		this.flt_rte_cd3 = flt_rte_cd3;
	}
	public String getFlb_nbr4() {
		return flb_nbr4;
	}
	public void setFlb_nbr4(String flb_nbr4) {
		this.flb_nbr4 = flb_nbr4;
	}
	public String getFlt_rte_cd4() {
		return flt_rte_cd4;
	}
	public void setFlt_rte_cd4(String flt_rte_cd4) {
		this.flt_rte_cd4 = flt_rte_cd4;
	}
	public String getFlb_nbr5() {
		return flb_nbr5;
	}
	public void setFlb_nbr5(String flb_nbr5) {
		this.flb_nbr5 = flb_nbr5;
	}
	public String getFlt_rte_cd5() {
		return flt_rte_cd5;
	}
	public void setFlt_rte_cd5(String flt_rte_cd5) {
		this.flt_rte_cd5 = flt_rte_cd5;
	}
	public String getFlb_nbr6() {
		return flb_nbr6;
	}
	public void setFlb_nbr6(String flb_nbr6) {
		this.flb_nbr6 = flb_nbr6;
	}
	public String getFlt_rte_cd6() {
		return flt_rte_cd6;
	}
	public void setFlt_rte_cd6(String flt_rte_cd6) {
		this.flt_rte_cd6 = flt_rte_cd6;
	}
	@Override
	public String toString() {
		return "ETermQuery [id=" + id + ", eTermName=" + eTermName
				+ ", password=" + password + ", wordName=" + wordName
				+ ", wordPassword=" + wordPassword + ", lvl=" + lvl + ", ip="
				+ ip + ", port=" + port + ", security=" + security + ", si="
				+ si + ", auto=" + auto + ", avoidTime=" + avoidTime + ", che="
				+ che + ", flb_nbr1=" + flb_nbr1 + ", flt_rte_cd1="
				+ flt_rte_cd1 + ", flb_nbr2=" + flb_nbr2 + ", flt_rte_cd2="
				+ flt_rte_cd2 + ", flb_nbr3=" + flb_nbr3 + ", flt_rte_cd3="
				+ flt_rte_cd3 + ", flb_nbr4=" + flb_nbr4 + ", flt_rte_cd4="
				+ flt_rte_cd4 + ", flb_nbr5=" + flb_nbr5 + ", flt_rte_cd5="
				+ flt_rte_cd5 + ", flb_nbr6=" + flb_nbr6 + ", flt_rte_cd6="
				+ flt_rte_cd6 + "]";
	}                    
}
