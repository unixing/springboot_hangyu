package org.ldd.ssm.crm.domain;

public class DepthScope {
	private String gotos;// 去程航班号
	private String backtos;// 去程航线
	private String gogos;
	private String backgos;
	public String getGotos() {
		return gotos;
	}
	public void setGotos(String gotos) {
		this.gotos = gotos;
	}
	public String getBacktos() {
		return backtos;
	}
	public void setBacktos(String backtos) {
		this.backtos = backtos;
	}
	public String getGogos() {
		return gogos;
	}
	public void setGogos(String gogos) {
		this.gogos = gogos;
	}
	public String getBackgos() {
		return backgos;
	}
	public void setBackgos(String backgos) {
		this.backgos = backgos;
	}
	@Override
	public String toString() {
		return "DepthScope [gotos=" + gotos + ", backtos=" + backtos
				+ ", gogos=" + gogos + ", backgos=" + backgos + "]";
	}
}
