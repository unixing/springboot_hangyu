package org.ldd.ssm.crm.query;

public class AirLineObject {
	private String Dpt_Arrv;

	public String getDpt_Arrv() {
		return Dpt_Arrv;
	}

	public void setDpt_Arrv(String dpt_Arrv) {
		Dpt_Arrv = dpt_Arrv;
	}

	@Override
	public String toString() {
		return "AirLineObject [Dpt_Arrv=" + Dpt_Arrv + "]";
	}
}
