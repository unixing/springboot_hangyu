package org.ldd.ssm.crm.domain;


public class SourceDistriData implements Comparable<SourceDistriData>{
	private String city;
	private int number;
	private String city_x;
	private String city_y;
	
	public String getCity_x() {
		return city_x;
	}
	public void setCity_x(String city_x) {
		this.city_x = city_x;
	}
	public String getCity_y() {
		return city_y;
	}
	public void setCity_y(String city_y) {
		this.city_y = city_y;
	}
	public SourceDistriData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SourceDistriData(String city, int number) {
		super();
		this.city = city;
		this.number = number;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	@Override
	public String toString() {
		return "SourceDistriData [city=" + city + ", number=" + number + "]";
	}
	@Override
	public int compareTo(SourceDistriData o) {
		if (this.getNumber() > o.getNumber()){
			return -1;// 由高到底排序
		}else if(this.getNumber() == o.getNumber()){
			return 0;
		}else{
			return 1;
		}
	}
	
}
