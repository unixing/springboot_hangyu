package org.ldd.ssm.crm.query;

import java.math.BigDecimal;

//吞吐量
public class ThroughputOrGdp {
	
	private int year;
	private String data;
	private String growthRate;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getGrowthRate() {
		return growthRate;
	}
	public void setGrowthRate(String growthRate) {
		this.growthRate = growthRate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ThroughputOrGdp [year=" + year + ", data=" + data
				+ ", growthRate=" + growthRate + "]";
	}
	
	
}
