package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class SimpleDemand implements Serializable {
	
	private String key;
	
	private String val;
	
	public SimpleDemand() {
		super();
	}
	
	public SimpleDemand(String key, String val) {
		super();
		this.key = key;
		this.val = val;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "SimpleDemand [key=" + key + ", val=" + val + "]";
	}
	
}
