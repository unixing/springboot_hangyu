package org.ldd.ssm.crm.domain;
/**
 * 上客进度表对应数据库实体
 * @Title:GustStrate 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-19 上午11:56:59
 */
public class GustStrate {

    private String On_Gut_Dte;//上客时间

    private int Gut_Rae;//上客率
    private int persons; //上客人数
    private String Dpt_AirPt_Cd;//始发地

    private String Arrv_Airpt_Cd;//到达地

    private String Flt_Nbr;//航班号
    private String Dta_Sce;//采集时间
    private String count_Set;
	/**
	 * @return the on_Gut_Dte
	 */
	public String getOn_Gut_Dte() {
		return On_Gut_Dte;
	}

	/**
	 * @param on_Gut_Dte the on_Gut_Dte to set
	 */
	public void setOn_Gut_Dte(String on_Gut_Dte) {
		On_Gut_Dte = on_Gut_Dte;
	}

	/**
	 * @return the gut_Rae
	 */
	public int getGut_Rae() {
		return Gut_Rae;
	}

	/**
	 * @param gut_Rae the gut_Rae to set
	 */
	public void setGut_Rae(int gut_Rae) {
		Gut_Rae = gut_Rae;
	}

	/**
	 * @return the dpt_AirPt_Cd
	 */
	public String getDpt_AirPt_Cd() {
		return Dpt_AirPt_Cd;
	}

	/**
	 * @param dpt_AirPt_Cd the dpt_AirPt_Cd to set
	 */
	public void setDpt_AirPt_Cd(String dpt_AirPt_Cd) {
		Dpt_AirPt_Cd = dpt_AirPt_Cd;
	}

	/**
	 * @return the arrv_Airpt_Cd
	 */
	public String getArrv_Airpt_Cd() {
		return Arrv_Airpt_Cd;
	}

	/**
	 * @param arrv_Airpt_Cd the arrv_Airpt_Cd to set
	 */
	public void setArrv_Airpt_Cd(String arrv_Airpt_Cd) {
		Arrv_Airpt_Cd = arrv_Airpt_Cd;
	}

	/**
	 * @return the flt_Nbr
	 */
	public String getFlt_Nbr() {
		return Flt_Nbr;
	}

	/**
	 * @param flt_Nbr the flt_Nbr to set
	 */
	public void setFlt_Nbr(String flt_Nbr) {
		Flt_Nbr = flt_Nbr;
	}

	
	/**
	 * @return the dta_Sce
	 */
	public String getDta_Sce() {
		return Dta_Sce;
	}

	/**
	 * @param dta_Sce the dta_Sce to set
	 */
	public void setDta_Sce(String dta_Sce) {
		Dta_Sce = dta_Sce;
	}

	/**
	 * @return the persons
	 */
	public int getPersons() {
		return persons;
	}

	/**
	 * @param persons the persons to set
	 */
	public void setPersons(int persons) {
		this.persons = persons;
	}

	/**
	 * @return the count_Set
	 */
	public String getCount_Set() {
		return count_Set;
	}

	/**
	 * @param count_Set the count_Set to set
	 */
	public void setCount_Set(String count_Set) {
		this.count_Set = count_Set;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GustStrate [On_Gut_Dte=" + On_Gut_Dte + ", Gut_Rae=" + Gut_Rae
				+ ", persons=" + persons + ", Dpt_AirPt_Cd=" + Dpt_AirPt_Cd
				+ ", Arrv_Airpt_Cd=" + Arrv_Airpt_Cd + ", Flt_Nbr=" + Flt_Nbr
				+ ", Dta_Sce=" + Dta_Sce + ", count_Set=" + count_Set + "]";
	}


   
}