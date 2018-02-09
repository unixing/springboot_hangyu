package org.ldd.ssm.crm.domain;

import java.util.Arrays;
import java.util.List;

import org.ldd.ssm.crm.service.impl.AirlineDynameicServiceImpl;

public class AirlineDynameicGraphics {
	private String month_zd;
	private String delay_cls;
	private String cancel_cls;
	private String normal_cls;
	private Abnormal[] exec;
	private List<String> months;
	public String getMonth_zd() {
		return month_zd;
	}
	public void setMonth_zd(String month_zd) {
		this.month_zd = month_zd;
	}
	public String getDelay_cls() {
		return delay_cls;
	}
	public void setDelay_cls(String delay_cls) {
		this.delay_cls = delay_cls;
	}
	public String getCancel_cls() {
		return cancel_cls;
	}
	public void setCancel_cls(String cancel_cls) {
		this.cancel_cls = cancel_cls;
	}
	public String getNormal_cls() {
		return normal_cls;
	}
	public void setNormal_cls(String normal_cls) {
		this.normal_cls = normal_cls;
	}
	public Abnormal[] getExec() {
		return exec;
	}
	public void setExec(Abnormal[] exec) {
		this.exec = exec;
	}
	public List<String> getMonths() {
		return months;
	}
	public void setMonths(List<String> months) {
		this.months = months;
	}
	@Override
	public String toString() {
		return "AirlineDynameicGraphics [month_zd=" + month_zd + ", delay_cls="
				+ delay_cls + ", cancel_cls=" + cancel_cls + ", normal_cls="
				+ normal_cls + ", exec=" + Arrays.toString(exec) + ", months="
				+ months + "]";
	}
	
}
