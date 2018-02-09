package org.ldd.ssm.hangyu.domain;

import java.io.Serializable;

public class EachflightDemo implements Serializable {
	private String time;
	private int monCnt;
	private int tuesCnt;
	private int wedCnt;
	private int thursCnt;
	private int friCnt;
	private int staCnt;
	private int sunCnt;
	public EachflightDemo() {}
	public EachflightDemo(String time) {
		this.time = time;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMonCnt() {
		return monCnt;
	}
	public void setMonCnt(int monCnt) {
		this.monCnt = monCnt;
	}
	public int getTuesCnt() {
		return tuesCnt;
	}
	public void setTuesCnt(int tuesCnt) {
		this.tuesCnt = tuesCnt;
	}
	public int getWedCnt() {
		return wedCnt;
	}
	public void setWedCnt(int wedCnt) {
		this.wedCnt = wedCnt;
	}
	public int getThursCnt() {
		return thursCnt;
	}
	public void setThursCnt(int thursCnt) {
		this.thursCnt = thursCnt;
	}
	public int getFriCnt() {
		return friCnt;
	}
	public void setFriCnt(int friCnt) {
		this.friCnt = friCnt;
	}
	public int getStaCnt() {
		return staCnt;
	}
	public void setStaCnt(int staCnt) {
		this.staCnt = staCnt;
	}
	public int getSunCnt() {
		return sunCnt;
	}
	public void setSunCnt(int sunCnt) {
		this.sunCnt = sunCnt;
	}
	@Override
	public String toString() {
		return "EachflightDemo [time=" + time + ", monCnt=" + monCnt
				+ ", tuesCnt=" + tuesCnt + ", wedCnt=" + wedCnt + ", thursCnt="
				+ thursCnt + ", friCnt=" + friCnt + ", staCnt=" + staCnt
				+ ", sunCnt=" + sunCnt + "]";
	}
}
