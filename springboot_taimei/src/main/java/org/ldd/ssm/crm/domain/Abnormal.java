package org.ldd.ssm.crm.domain;


public class Abnormal implements Comparable<Abnormal>{
	private String name;
	private int val;
	public Abnormal(String name,int val){
		this.name = name;
		this.val = val;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getVal() {
		return val;
	}
	public void setVal(int val) {
		this.val = val;
	}
	public int compareTo(Abnormal o) {
		if(o.val < val)
		{
			return 1;
		}
		else if(o.val > val)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
