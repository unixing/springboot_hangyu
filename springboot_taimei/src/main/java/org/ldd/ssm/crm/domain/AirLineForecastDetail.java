package org.ldd.ssm.crm.domain;

import java.util.Date;

/**
 * 航线收益预估航段详细信息
 * @Title:AirLineForecastDetail 
 * @Description:
 * @author taimei-gds 
 * @date 2016-3-28 下午5:30:18
 */
public class AirLineForecastDetail {
	//关联航段基本信息
	private String airLineForecastId;
	//航段
	private String hangduan;
	//航距
	private Float hangju;
	//Y舱价格
	private Float y_price;
	//切位团队价
	private Float qie_team_price;
	//150%
	private Float f_flag;
	//100%
	private Float y_flag;
	//90%
	private Float b_flag;
	//85%
	private Float h_flag;
	//80%
	private Float k_flag;
	//75%
	private Float l_flag;
	//70%
	private Float m_flag;
	//65%
	private Float q_flag;
	//60%
	private Float x_flag;
	//55%
	private Float u_flag;
	//50%
	private Float e_flag;
	//45%
	private Float z_flag;
	//40%
	private Float t_flag;
	//35%
	private Float v_flag;
	//30%
	private Float g_flag;
	//25%
	private Float s_flag;
	//0%
	private Float o_flag;
	//切位团队折扣
	private Float qp_flag;
	//其他
	private Float qt_flag;
	//主键
	private String id;
	//数据源
	private String dta_Sce;
	//增加时间
	private Date Dta_Dte;
	
	
	/**
	 * @return the hangju
	 */
	public Float getHangju() {
		return hangju;
	}
	/**
	 * @param hangju the hangju to set
	 */
	public void setHangju(Float hangju) {
		this.hangju = hangju;
	}
	/**
	 * @return the y_price
	 */
	public Float getY_price() {
		return y_price;
	}
	/**
	 * @param y_price the y_price to set
	 */
	public void setY_price(Float y_price) {
		this.y_price = y_price;
	}
	/**
	 * @return the qie_team_price
	 */
	public Float getQie_team_price() {
		return qie_team_price;
	}
	/**
	 * @param qie_team_price the qie_team_price to set
	 */
	public void setQie_team_price(Float qie_team_price) {
		this.qie_team_price = qie_team_price;
	}
	/**
	 * @return the f_flag
	 */
	public Float getF_flag() {
		return f_flag;
	}
	/**
	 * @param f_flag the f_flag to set
	 */
	public void setF_flag(Float f_flag) {
		this.f_flag = f_flag;
	}
	/**
	 * @return the y_flag
	 */
	public Float getY_flag() {
		return y_flag;
	}
	/**
	 * @param y_flag the y_flag to set
	 */
	public void setY_flag(Float y_flag) {
		this.y_flag = y_flag;
	}
	/**
	 * @return the b_flag
	 */
	public Float getB_flag() {
		return b_flag;
	}
	/**
	 * @param b_flag the b_flag to set
	 */
	public void setB_flag(Float b_flag) {
		this.b_flag = b_flag;
	}
	/**
	 * @return the h_flag
	 */
	public Float getH_flag() {
		return h_flag;
	}
	/**
	 * @param h_flag the h_flag to set
	 */
	public void setH_flag(Float h_flag) {
		this.h_flag = h_flag;
	}
	/**
	 * @return the k_flag
	 */
	public Float getK_flag() {
		return k_flag;
	}
	/**
	 * @param k_flag the k_flag to set
	 */
	public void setK_flag(Float k_flag) {
		this.k_flag = k_flag;
	}
	/**
	 * @return the l_flag
	 */
	public Float getL_flag() {
		return l_flag;
	}
	/**
	 * @param l_flag the l_flag to set
	 */
	public void setL_flag(Float l_flag) {
		this.l_flag = l_flag;
	}
	/**
	 * @return the m_flag
	 */
	public Float getM_flag() {
		return m_flag;
	}
	/**
	 * @param m_flag the m_flag to set
	 */
	public void setM_flag(Float m_flag) {
		this.m_flag = m_flag;
	}
	/**
	 * @return the q_flag
	 */
	public Float getQ_flag() {
		return q_flag;
	}
	/**
	 * @param q_flag the q_flag to set
	 */
	public void setQ_flag(Float q_flag) {
		this.q_flag = q_flag;
	}
	/**
	 * @return the x_flag
	 */
	public Float getX_flag() {
		return x_flag;
	}
	/**
	 * @param x_flag the x_flag to set
	 */
	public void setX_flag(Float x_flag) {
		this.x_flag = x_flag;
	}
	/**
	 * @return the u_flag
	 */
	public Float getU_flag() {
		return u_flag;
	}
	/**
	 * @param u_flag the u_flag to set
	 */
	public void setU_flag(Float u_flag) {
		this.u_flag = u_flag;
	}
	/**
	 * @return the e_flag
	 */
	public Float getE_flag() {
		return e_flag;
	}
	/**
	 * @param e_flag the e_flag to set
	 */
	public void setE_flag(Float e_flag) {
		this.e_flag = e_flag;
	}
	/**
	 * @return the z_flag
	 */
	public Float getZ_flag() {
		return z_flag;
	}
	/**
	 * @param z_flag the z_flag to set
	 */
	public void setZ_flag(Float z_flag) {
		this.z_flag = z_flag;
	}
	/**
	 * @return the t_flag
	 */
	public Float getT_flag() {
		return t_flag;
	}
	/**
	 * @param t_flag the t_flag to set
	 */
	public void setT_flag(Float t_flag) {
		this.t_flag = t_flag;
	}
	/**
	 * @return the v_flag
	 */
	public Float getV_flag() {
		return v_flag;
	}
	/**
	 * @param v_flag the v_flag to set
	 */
	public void setV_flag(Float v_flag) {
		this.v_flag = v_flag;
	}
	/**
	 * @return the g_flag
	 */
	public Float getG_flag() {
		return g_flag;
	}
	/**
	 * @param g_flag the g_flag to set
	 */
	public void setG_flag(Float g_flag) {
		this.g_flag = g_flag;
	}
	/**
	 * @return the o_flag
	 */
	public Float getO_flag() {
		return o_flag;
	}
	/**
	 * @param o_flag the o_flag to set
	 */
	public void setO_flag(Float o_flag) {
		this.o_flag = o_flag;
	}
	/**
	 * @return the s_flag
	 */
	public Float getS_flag() {
		return s_flag;
	}
	/**
	 * @param s_flag the s_flag to set
	 */
	public void setS_flag(Float s_flag) {
		this.s_flag = s_flag;
	}
	/**
	 * @return the qt_flag
	 */
	public Float getQt_flag() {
		return qt_flag;
	}
	/**
	 * @param qt_flag the qt_flag to set
	 */
	public void setQt_flag(Float qt_flag) {
		this.qt_flag = qt_flag;
	}
	
	
	/**
	 * @return the dta_Sce
	 */
	public String getDta_Sce() {
		return dta_Sce;
	}
	/**
	 * @param dta_Sce the dta_Sce to set
	 */
	public void setDta_Sce(String dta_Sce) {
		this.dta_Sce = dta_Sce;
	}
	/**
	 * @return the dta_Dte
	 */
	public Date getDta_Dte() {
		return Dta_Dte;
	}
	/**
	 * @param dta_Dte the dta_Dte to set
	 */
	public void setDta_Dte(Date dta_Dte) {
		Dta_Dte = dta_Dte;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the qp_flag
	 */
	public Float getQp_flag() {
		return qp_flag;
	}
	/**
	 * @param qp_flag the qp_flag to set
	 */
	public void setQp_flag(Float qp_flag) {
		this.qp_flag = qp_flag;
	}
	
	/**
	 * @return the airLineForecastId
	 */
	public String getAirLineForecastId() {
		return airLineForecastId;
	}
	/**
	 * @param airLineForecastId the airLineForecastId to set
	 */
	public void setAirLineForecastId(String airLineForecastId) {
		this.airLineForecastId = airLineForecastId;
	}
	
	/**
	 * @return the hangduan
	 */
	public String getHangduan() {
		return hangduan;
	}
	/**
	 * @param hangduan the hangduan to set
	 */
	public void setHangduan(String hangduan) {
		this.hangduan = hangduan;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineForecastDetail [airLineForecastId=" + airLineForecastId
				+ ", hangduan=" + hangduan + ", hangju=" + hangju
				+ ", y_price=" + y_price + ", qie_team_price=" + qie_team_price
				+ ", f_flag=" + f_flag + ", y_flag=" + y_flag + ", b_flag="
				+ b_flag + ", h_flag=" + h_flag + ", k_flag=" + k_flag
				+ ", l_flag=" + l_flag + ", m_flag=" + m_flag + ", q_flag="
				+ q_flag + ", x_flag=" + x_flag + ", u_flag=" + u_flag
				+ ", e_flag=" + e_flag + ", z_flag=" + z_flag + ", t_flag="
				+ t_flag + ", v_flag=" + v_flag + ", g_flag=" + g_flag
				+ ", s_flag=" + s_flag + ", o_flag=" + o_flag + ", qp_flag="
				+ qp_flag + ", qt_flag=" + qt_flag + ", id=" + id
				+ ", dta_Sce=" + dta_Sce + ", Dta_Dte=" + Dta_Dte + "]";
	}
	
}
