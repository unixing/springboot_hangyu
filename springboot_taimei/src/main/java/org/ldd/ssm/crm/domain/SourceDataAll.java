package org.ldd.ssm.crm.domain;

import java.util.List;
import java.util.Map;

public class SourceDataAll {
	private String leg;//航段
	private String manNumber;//男人数
	private String wumanNumber;//女人数
	private SourceData_Age age;//年龄层次
	private List<SourceDistriData> sous;//list<城市 人数>
	private Map<String,Object> dataMap;//每个城市对应人数
	private Map<String,Map<String,Object>> peopleStruct;
	
	public String getLeg() {
		return leg;
	}
	public void setLeg(String leg) {
		this.leg = leg;
	}
	public String getManNumber() {
		return manNumber;
	}
	public void setManNumber(String manNumber) {
		this.manNumber = manNumber;
	}
	public String getWumanNumber() {
		return wumanNumber;
	}
	public void setWumanNumber(String wumanNumber) {
		this.wumanNumber = wumanNumber;
	}
	public SourceData_Age getAge() {
		return age;
	}
	public void setAge(SourceData_Age age) {
		this.age = age;
	}
	public List<SourceDistriData> getSous() {
		return sous;
	}
	public void setSous(List<SourceDistriData> sous) {
		this.sous = sous;
	}
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	public Map<String, Map<String, Object>> getPeopleStruct() {
		return peopleStruct;
	}
	public void setPeopleStruct(Map<String, Map<String, Object>> peopleStruct) {
		this.peopleStruct = peopleStruct;
	}
	@Override
	public String toString() {
		return "SourceDataAll [manNumber=" + manNumber + ", wumanNumber="
				+ wumanNumber + ", age=" + age + ", sous=" + sous
				+ ", dataMap=" + dataMap + ", peopleStruct=" + peopleStruct
				+ "]";
	}
	
}
