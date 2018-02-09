package org.ldd.ssm.crm.domain;
/**
 * 航班号及航段模型
 * @author Taimei
 */
public class AddFltNbr {
	private long id;
	private String flt_Nbr;//航班号
	private String air_line;//航线
	private String flt_one;//航段一
	private String flt_two;//航段二
	private String flt_three;//航段三
	private long eterm_account_id;
	
	public long getEterm_account_id() {
		return eterm_account_id;
	}
	public void setEterm_account_id(long eterm_account_id) {
		this.eterm_account_id = eterm_account_id;
	}
	public String getAir_line() {
		return air_line;
	}
	public void setAir_line(String air_line) {
		this.air_line = air_line;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFlt_Nbr() {
		return flt_Nbr;
	}
	public void setFlt_Nbr(String flt_Nbr) {
		this.flt_Nbr = flt_Nbr;
	}
	public String getFlt_one() {
		return flt_one;
	}
	public void setFlt_one(String flt_one) {
		this.flt_one = flt_one;
	}
	public String getFlt_two() {
		return flt_two;
	}
	public void setFlt_two(String flt_two) {
		this.flt_two = flt_two;
	}
	public String getFlt_three() {
		return flt_three;
	}
	public void setFlt_three(String flt_three) {
		this.flt_three = flt_three;
	}
	@Override
	public String toString() {
		return "AddFltNbr [id=" + id + ", flt_Nbr=" + flt_Nbr + ", air_line="
				+ air_line + ", flt_one=" + flt_one + ", flt_two=" + flt_two
				+ ", flt_three=" + flt_three + ", eterm_account_id="
				+ eterm_account_id + "]";
	}
	
}
