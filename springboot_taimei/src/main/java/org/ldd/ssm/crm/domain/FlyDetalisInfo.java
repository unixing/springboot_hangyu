package org.ldd.ssm.crm.domain;
/**
 * 飞机详细数据实体类
 * @Title:FlyDetalisInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-24 上午10:47:08
 */
public class FlyDetalisInfo{	
	private String id ;//主键
	private String fly_Tye_Nm;//飞机型号
	private String fly_Mfr;	//飞机的生产厂家
	private String fly_SAt;	//座位总数
	private String fly_Bdy_Lgt;	//飞机长度
	private String fly_Bdy_Wth;	//飞机宽度
	private String fly_Bdy_Hgt;	//飞机高度
	private String fly_Bdy_Wsn;	//机翼展开宽度
	private String fly_Cgo_Hdl;	//货舱容积
	private String fly_Spd;	//飞行速度(马赫为单位)
	private String fly_Ept_Mce;	//空机重
	private String fly_Max_Pld;	//最大业载（吨）
	private String fly_Max_Tkf;	//最大起飞总重（吨）
	private String fly_Tnk_Cpy;	//最大油箱容量（升）
	private String fly_Vyg;	//航程 10,400 公里
	private String fly_Hos_Cot;	//每小时成本(参考)
	private String fly_Img;	//飞机图片地址
	private String employee_id;//维护用户id
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
	 * @return the fly_Tye_Nm
	 */
	public String getFly_Tye_Nm() {
		return fly_Tye_Nm;
	}
	/**
	 * @param fly_Tye_Nm the fly_Tye_Nm to set
	 */
	public void setFly_Tye_Nm(String fly_Tye_Nm) {
		this.fly_Tye_Nm = fly_Tye_Nm;
	}
	/**
	 * @return the fly_Mfr
	 */
	public String getFly_Mfr() {
		return fly_Mfr;
	}
	/**
	 * @param fly_Mfr the fly_Mfr to set
	 */
	public void setFly_Mfr(String fly_Mfr) {
		this.fly_Mfr = fly_Mfr;
	}
	/**
	 * @return the fly_SAt
	 */
	public String getFly_SAt() {
		return fly_SAt;
	}
	/**
	 * @param fly_SAt the fly_SAt to set
	 */
	public void setFly_SAt(String fly_SAt) {
		this.fly_SAt = fly_SAt;
	}
	/**
	 * @return the fly_Bdy_Lgt
	 */
	public String getFly_Bdy_Lgt() {
		return fly_Bdy_Lgt;
	}
	/**
	 * @param fly_Bdy_Lgt the fly_Bdy_Lgt to set
	 */
	public void setFly_Bdy_Lgt(String fly_Bdy_Lgt) {
		this.fly_Bdy_Lgt = fly_Bdy_Lgt;
	}
	/**
	 * @return the fly_Bdy_Wth
	 */
	public String getFly_Bdy_Wth() {
		return fly_Bdy_Wth;
	}
	/**
	 * @param fly_Bdy_Wth the fly_Bdy_Wth to set
	 */
	public void setFly_Bdy_Wth(String fly_Bdy_Wth) {
		this.fly_Bdy_Wth = fly_Bdy_Wth;
	}
	/**
	 * @return the fly_Bdy_Hgt
	 */
	public String getFly_Bdy_Hgt() {
		return fly_Bdy_Hgt;
	}
	/**
	 * @param fly_Bdy_Hgt the fly_Bdy_Hgt to set
	 */
	public void setFly_Bdy_Hgt(String fly_Bdy_Hgt) {
		this.fly_Bdy_Hgt = fly_Bdy_Hgt;
	}
	/**
	 * @return the fly_Bdy_Wsn
	 */
	public String getFly_Bdy_Wsn() {
		return fly_Bdy_Wsn;
	}
	/**
	 * @param fly_Bdy_Wsn the fly_Bdy_Wsn to set
	 */
	public void setFly_Bdy_Wsn(String fly_Bdy_Wsn) {
		this.fly_Bdy_Wsn = fly_Bdy_Wsn;
	}
	/**
	 * @return the fly_Cgo_Hdl
	 */
	public String getFly_Cgo_Hdl() {
		return fly_Cgo_Hdl;
	}
	/**
	 * @param fly_Cgo_Hdl the fly_Cgo_Hdl to set
	 */
	public void setFly_Cgo_Hdl(String fly_Cgo_Hdl) {
		this.fly_Cgo_Hdl = fly_Cgo_Hdl;
	}
	/**
	 * @return the fly_Spd
	 */
	public String getFly_Spd() {
		return fly_Spd;
	}
	/**
	 * @param fly_Spd the fly_Spd to set
	 */
	public void setFly_Spd(String fly_Spd) {
		this.fly_Spd = fly_Spd;
	}
	/**
	 * @return the fly_Ept_Mce
	 */
	public String getFly_Ept_Mce() {
		return fly_Ept_Mce;
	}
	/**
	 * @param fly_Ept_Mce the fly_Ept_Mce to set
	 */
	public void setFly_Ept_Mce(String fly_Ept_Mce) {
		this.fly_Ept_Mce = fly_Ept_Mce;
	}
	/**
	 * @return the fly_Max_Pld
	 */
	public String getFly_Max_Pld() {
		return fly_Max_Pld;
	}
	/**
	 * @param fly_Max_Pld the fly_Max_Pld to set
	 */
	public void setFly_Max_Pld(String fly_Max_Pld) {
		this.fly_Max_Pld = fly_Max_Pld;
	}
	/**
	 * @return the fly_Max_Tkf
	 */
	public String getFly_Max_Tkf() {
		return fly_Max_Tkf;
	}
	/**
	 * @param fly_Max_Tkf the fly_Max_Tkf to set
	 */
	public void setFly_Max_Tkf(String fly_Max_Tkf) {
		this.fly_Max_Tkf = fly_Max_Tkf;
	}
	/**
	 * @return the fly_Tnk_Cpy
	 */
	public String getFly_Tnk_Cpy() {
		return fly_Tnk_Cpy;
	}
	/**
	 * @param fly_Tnk_Cpy the fly_Tnk_Cpy to set
	 */
	public void setFly_Tnk_Cpy(String fly_Tnk_Cpy) {
		this.fly_Tnk_Cpy = fly_Tnk_Cpy;
	}
	/**
	 * @return the fly_Vyg
	 */
	public String getFly_Vyg() {
		return fly_Vyg;
	}
	/**
	 * @param fly_Vyg the fly_Vyg to set
	 */
	public void setFly_Vyg(String fly_Vyg) {
		this.fly_Vyg = fly_Vyg;
	}
	/**
	 * @return the fly_Hos_Cot
	 */
	public String getFly_Hos_Cot() {
		return fly_Hos_Cot;
	}
	/**
	 * @param fly_Hos_Cot the fly_Hos_Cot to set
	 */
	public void setFly_Hos_Cot(String fly_Hos_Cot) {
		this.fly_Hos_Cot = fly_Hos_Cot;
	}
	/**
	 * @return the fly_Img
	 */
	public String getFly_Img() {
		return fly_Img;
	}
	/**
	 * @param fly_Img the fly_Img to set
	 */
	public void setFly_Img(String fly_Img) {
		this.fly_Img = fly_Img;
	}
	/**
	 * @return the employee_id
	 */
	public String getEmployee_id() {
		return employee_id;
	}
	/**
	 * @param employee_id the employee_id to set
	 */
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlyDetalisInfo [id=" + id + ", fly_Tye_Nm=" + fly_Tye_Nm
				+ ", fly_Mfr=" + fly_Mfr + ", fly_SAt=" + fly_SAt
				+ ", fly_Bdy_Lgt=" + fly_Bdy_Lgt + ", fly_Bdy_Wth="
				+ fly_Bdy_Wth + ", fly_Bdy_Hgt=" + fly_Bdy_Hgt
				+ ", fly_Bdy_Wsn=" + fly_Bdy_Wsn + ", fly_Cgo_Hdl="
				+ fly_Cgo_Hdl + ", fly_Spd=" + fly_Spd + ", fly_Ept_Mce="
				+ fly_Ept_Mce + ", fly_Max_Pld=" + fly_Max_Pld
				+ ", fly_Max_Tkf=" + fly_Max_Tkf + ", fly_Tnk_Cpy="
				+ fly_Tnk_Cpy + ", fly_Vyg=" + fly_Vyg + ", fly_Hos_Cot="
				+ fly_Hos_Cot + ", fly_Img=" + fly_Img + ", employee_id="
				+ employee_id + "]";
	}	
	
}
