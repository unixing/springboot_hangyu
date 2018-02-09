package org.ldd.ssm.hangyu.query;

import java.io.Serializable;

public class DemandDto implements Serializable {

		private String dpt;
		private String cityCoordinateJ;
		private String cityCoordinateW;
		private int num;
		private String demandType;
		private String obj;
		private String newInfo;
		private int myNum;
		
		public int getMyNum() {
			return myNum;
		}
		public void setMyNum(int myNum) {
			this.myNum = myNum;
		}
		public String getDpt() {
			return dpt;
		}
		public void setDpt(String dpt) {
			this.dpt = dpt;
		}
		public String getCityCoordinateJ() {
			return cityCoordinateJ;
		}
		public void setCityCoordinateJ(String cityCoordinateJ) {
			this.cityCoordinateJ = cityCoordinateJ;
		}
		public String getCityCoordinateW() {
			return cityCoordinateW;
		}
		public void setCityCoordinateW(String cityCoordinateW) {
			this.cityCoordinateW = cityCoordinateW;
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public String getDemandType() {
			return demandType;
		}
		public void setDemandType(String demandType) {
			this.demandType = demandType;
		}
		public String getObj() {
			return obj;
		}
		public void setObj(String obj) {
			this.obj = obj;
		}
		public String getNewInfo() {
			return newInfo;
		}
		public void setNewInfo(String newInfo) {
			this.newInfo = newInfo;
		}
		
		
		
}
