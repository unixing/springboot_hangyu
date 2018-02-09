package org.ldd.ssm.crm.domain;
/**
 * 冲突解决方案模型
 */
public class Conflictresolution {
	private long id;//
	private String Cct_Ron;// 冲突解决方案'
	private int Dte_Aic_Rrt;//指定自动采集时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCct_Ron() {
		return Cct_Ron;
	}
	public void setCct_Ron(String cct_Ron) {
		Cct_Ron = cct_Ron;
	}
	public int getDte_Aic_Rrt() {
		return Dte_Aic_Rrt;
	}
	public void setDte_Aic_Rrt(int dte_Aic_Rrt) {
		Dte_Aic_Rrt = dte_Aic_Rrt;
	}
	@Override
	public String toString() {
		return "Conflictresolution [id=" + id + ", Cct_Ron=" + Cct_Ron
				+ ", Dte_Aic_Rrt=" + Dte_Aic_Rrt + "]";
	}
	
}
