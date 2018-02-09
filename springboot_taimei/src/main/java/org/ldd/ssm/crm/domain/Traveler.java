package org.ldd.ssm.crm.domain;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 航班信息详细表
 */
public class Traveler {
	private String Pax_Id_Nbr; // '旅客证件号',
	private BigDecimal Tit_Pre; // '票面价格',
	private String Frt_Spe; // '旅客座舱位',
	private String Grp_Ind; // '代表团队编码',
	private String Bk_Ofc_Cd; // '订票单位编码(office号)',
	private String Rsp_Ofc_Cd; // '出票单位编码(office)',
	private int Pen_Tye; // '旅客类型(成人,儿童,婴儿)',
	private String TKT_No; // '票号',
	private int Fel_Che; // '燃油费',
	private int Apt_Fee; // '机建费',
	private int Ptn_Fee; // '促销费',
	private int PNR_Cde; // 'PNR编码',
	private Date To_Drw_Dte; // '出票日期',
	private String uuid; // '连接航班信息表',
	public String getPax_Id_Nbr() {
		return Pax_Id_Nbr;
	}
	public void setPax_Id_Nbr(String pax_Id_Nbr) {
		Pax_Id_Nbr = pax_Id_Nbr;
	}
	public BigDecimal getTit_Pre() {
		return Tit_Pre;
	}
	public void setTit_Pre(BigDecimal tit_Pre) {
		Tit_Pre = tit_Pre;
	}
	public String getFrt_Spe() {
		return Frt_Spe;
	}
	public void setFrt_Spe(String frt_Spe) {
		Frt_Spe = frt_Spe;
	}
	public String getGrp_Ind() {
		return Grp_Ind;
	}
	public void setGrp_Ind(String grp_Ind) {
		Grp_Ind = grp_Ind;
	}
	public String getBk_Ofc_Cd() {
		return Bk_Ofc_Cd;
	}
	public void setBk_Ofc_Cd(String bk_Ofc_Cd) {
		Bk_Ofc_Cd = bk_Ofc_Cd;
	}
	public String getRsp_Ofc_Cd() {
		return Rsp_Ofc_Cd;
	}
	public void setRsp_Ofc_Cd(String rsp_Ofc_Cd) {
		Rsp_Ofc_Cd = rsp_Ofc_Cd;
	}
	public int getPen_Tye() {
		return Pen_Tye;
	}
	public void setPen_Tye(int pen_Tye) {
		Pen_Tye = pen_Tye;
	}
	public String getTKT_No() {
		return TKT_No;
	}
	public void setTKT_No(String tKT_No) {
		TKT_No = tKT_No;
	}
	public int getFel_Che() {
		return Fel_Che;
	}
	public void setFel_Che(int fel_Che) {
		Fel_Che = fel_Che;
	}
	public int getApt_Fee() {
		return Apt_Fee;
	}
	public void setApt_Fee(int apt_Fee) {
		Apt_Fee = apt_Fee;
	}
	public int getPtn_Fee() {
		return Ptn_Fee;
	}
	public void setPtn_Fee(int ptn_Fee) {
		Ptn_Fee = ptn_Fee;
	}
	public int getPNR_Cde() {
		return PNR_Cde;
	}
	public void setPNR_Cde(int pNR_Cde) {
		PNR_Cde = pNR_Cde;
	}
	public Date getTo_Drw_Dte() {
		return To_Drw_Dte;
	}
	public void setTo_Drw_Dte(Date to_Drw_Dte) {
		To_Drw_Dte = to_Drw_Dte;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String toString() {
		return "Traveler [Pax_Id_Nbr=" + Pax_Id_Nbr + ", Tit_Pre=" + Tit_Pre
				+ ", Frt_Spe=" + Frt_Spe + ", Grp_Ind=" + Grp_Ind
				+ ", Bk_Ofc_Cd=" + Bk_Ofc_Cd + ", Rsp_Ofc_Cd=" + Rsp_Ofc_Cd
				+ ", Pen_Tye=" + Pen_Tye + ", TKT_No=" + TKT_No + ", Fel_Che="
				+ Fel_Che + ", Apt_Fee=" + Apt_Fee + ", Ptn_Fee=" + Ptn_Fee
				+ ", PNR_Cde=" + PNR_Cde + ", To_Drw_Dte=" + To_Drw_Dte
				+ ", uuid=" + uuid + "]";
	}
}
