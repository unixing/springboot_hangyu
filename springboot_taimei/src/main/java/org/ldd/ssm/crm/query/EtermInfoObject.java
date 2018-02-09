package org.ldd.ssm.crm.query;

import org.ldd.ssm.crm.domain.ETermInfo;

public class EtermInfoObject {
	private String eTermUserName;
	private ETermInfo info;
	public String geteTermUserName() {
		return eTermUserName;
	}
	public void seteTermUserName(String eTermUserName) {
		this.eTermUserName = eTermUserName;
	}
	public ETermInfo getInfo() {
		return info;
	}
	public void setInfo(ETermInfo info) {
		this.info = info;
	}
}
