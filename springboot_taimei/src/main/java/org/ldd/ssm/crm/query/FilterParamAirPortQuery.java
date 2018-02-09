package org.ldd.ssm.crm.query;

import java.util.Arrays;
import java.util.List;

/**
 * 机场筛选条件查询类
 * @author Taimei
 *
 */
public class FilterParamAirPortQuery {
	private String[] fly_area_grade;//飞行区等级, 多选
	private String[] lastyear_person;//吞吐量,范围值
	private String[] airport_type;//机场类别
	private String[] subsidy_type;//补贴政策
	public String[] getFly_area_grade() {
		return fly_area_grade;
	}
	public void setFly_area_grade(String[] fly_area_grade) {
		this.fly_area_grade = fly_area_grade;
	}
	public String[] getLastyear_person() {
		return lastyear_person;
	}
	public void setLastyear_person(String[] lastyear_person) {
		this.lastyear_person = lastyear_person;
	}
	public String[] getAirport_type() {
		return airport_type;
	}
	public void setAirport_type(String[] airport_type) {
		this.airport_type = airport_type;
	}
	public String[] getSubsidy_type() {
		return subsidy_type;
	}
	public void setSubsidy_type(String[] subsidy_type) {
		this.subsidy_type = subsidy_type;
	}
	@Override
	public String toString() {
		return "FilterParamAirPortQuery [fly_area_grade="
				+ Arrays.toString(fly_area_grade) + ", lastyear_person="
				+ Arrays.toString(lastyear_person) + ", airport_type="
				+ Arrays.toString(airport_type) + ", subsidy_type="
				+ Arrays.toString(subsidy_type) + "]";
	}
	
}
