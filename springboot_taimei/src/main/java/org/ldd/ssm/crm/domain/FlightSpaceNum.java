package org.ldd.ssm.crm.domain;
/**
 * 个航司的折扣对应舱位号
 * @author wxm
 *
 * @date 2017-3-9
 */
public class FlightSpaceNum {
	
	private String DctChr;//折扣字符
	private double DctPpt;//折扣比例
	public String getDctChr() {
		return DctChr;
	}
	public void setDctChr(String dctChr) {
		DctChr = dctChr;
	}
	public double getDctPpt() {
		return DctPpt;
	}
	public void setDctPpt(double dctPpt) {
		DctPpt = dctPpt;
	}
	
}
