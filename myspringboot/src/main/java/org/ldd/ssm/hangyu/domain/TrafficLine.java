package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class TrafficLine implements Serializable {
	private String roadType;//道路类型
	private String roadCode;//道路编号
	private String roadPoint;//道路途径点
	public TrafficLine() {}
	public TrafficLine(String roadType, String roadCode, String roadPoint) {
		this.roadType = roadType;
		this.roadCode = roadCode;
		this.roadPoint = roadPoint;
	}

	public String getRoadType() {
		return roadType;
	}
	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}
	public String getRoadCode() {
		return roadCode;
	}
	public void setRoadCode(String roadCode) {
		this.roadCode = roadCode;
	}
	public String getRoadPoint() {
		return roadPoint;
	}
	public void setRoadPoint(String roadPoint) {
		this.roadPoint = roadPoint;
	}
	@Override
	public String toString() {
		return "TrafficLine [roadType=" + roadType + ", roadCode=" + roadCode
				+ ", roadPoint=" + roadPoint + "]";
	}
}
