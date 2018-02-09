package org.ldd.ssm.crm.domain;
/**
 * 班次排行类和座公里收入排行类
 * @author Taimei
 */
public class ClassRanking {
	private String count;//总数
	private String name;
	private String value;
	private double Set_Ktr_Ine;
	private double guestamount;
	private String Dpt_AirPt_Cd;
	private String Arrv_Airpt_Cd;
	private String title;
	private String Cpy_Nm;
	
	
	public String getCpy_Nm() {
		return Cpy_Nm;
	}
	public void setCpy_Nm(String cpy_Nm) {
		Cpy_Nm = cpy_Nm;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getDpt_AirPt_Cd() {
		return Dpt_AirPt_Cd;
	}
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		Dpt_AirPt_Cd = dpt_AirPt_Cd;
	}
	public String getArrv_Airpt_Cd() {
		return Arrv_Airpt_Cd;
	}
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		Arrv_Airpt_Cd = arrv_Airpt_Cd;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public String toString() {
		return "ClassRanking [count=" + count + ", name=" + name + ", value="
				+ value + ", Set_Ktr_Ine=" + Set_Ktr_Ine + ", guestamount="
				+ guestamount + ", Dpt_AirPt_Cd=" + Dpt_AirPt_Cd
				+ ", Arrv_Airpt_Cd=" + Arrv_Airpt_Cd + ", title=" + title
				+ ", Cpy_Nm=" + Cpy_Nm + "]";
	}
	/**
	 * @return the set_Ktr_Ine
	 */
	public double getSet_Ktr_Ine() {
		return Set_Ktr_Ine;
	}
	/**
	 * @param set_Ktr_Ine the set_Ktr_Ine to set
	 */
	public void setSet_Ktr_Ine(double set_Ktr_Ine) {
		Set_Ktr_Ine = set_Ktr_Ine;
	}
	/**
	 * @return the guestamount
	 */
	public double getGuestamount() {
		return guestamount;
	}
	/**
	 * @param guestamount the guestamount to set
	 */
	public void setGuestamount(double guestamount) {
		this.guestamount = guestamount;
	}
	
}
