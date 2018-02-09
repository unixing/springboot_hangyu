package org.ldd.ssm.crm.domain;
/**
 * 经停航线包含的每一段的详细信息实体
 * @Title:AirLineAllInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2017-9-4 下午4:00:30
 */
public class AirLineAllInfo {
	private double go_sr_cd;//去长段收入
	private double go_sr_dd1;//去短1收入
	private double go_sr_dd2;//去短2收入
	private double go_tdsr_cd;//去长段团队收入
	private double go_tdsr_dd1;//去短1团队收入
	private double go_tdsr_dd2;//去短2团队收入
	private double go_ftzws_cd;//去长段座位数
	private double go_ftzws_dd1;//去短1座位数
	private double go_ftzws_dd2;//去短2座位数
	private double go_fxsj_cd;//去长段时间
	private double go_fxsj_dd1;//去短1时间
	private double go_fxsj_dd2;//去短2时间
	private double go_hj_cd;//去长段行距
	private double go_hj_dd1;//去短1行距
	private double go_hj_dd2;//去短2行距
	private double go_zk_cd;//去长段折扣
	private double go_zk_dd1;//去短1折扣
	private double go_zk_dd2;//去短2折扣
	private double go_tdzk_cd;//去团队长段折扣
	private double go_tdzk_dd1;//去团队短1折扣
	private double go_tdzk_dd2;//去团队短2折扣
	private double go_rs_cd;//去长段人数
	private double go_rs_dd1;//去短1人数
	private double go_rs_dd2;//去短2人数
	private double go_tdrs_cd;//去长段团队人数
	private double go_tdrs_dd1;//去短1团队人数
	private double go_tdrs_dd2;//去短2团队人数
	private double go_cap;//去的布局座位数
	private double go_index;//去的段数
	private double go_zgl_cd;//去的长段座公里
	private double go_zgl_dd1;//去的短1段座公里
	private double go_zgl_dd2;//去的短2段座公里
	private double go_xssr_cd;//去的长段小时收入
	private double go_xssr_dd1;//去的短1段小时收入
	private double go_xssr_dd2;//去的短2段小时收入
	private double go_kzl_cd;//去的长段客座率
	private double go_kzl_dd1;//去的短1段客座率
	private double go_kzl_dd2;//去的短2段客座率
	
	
	private double back_sr_cd;//回长段收入
	private double back_sr_dd1;//回短1收入
	private double back_sr_dd2;//回短2收入
	private double back_tdsr_cd;//回长段团队收入
	private double back_tdsr_dd1;//回短1团队收入
	private double back_tdsr_dd2;//回短2团队收入
	private double back_ftzws_cd;//回长段座位数
	private double back_ftzws_dd1;//回短1座位数
	private double back_ftzws_dd2;//回短2座位数
	private double back_fxsj_cd;//回长段时间
	private double back_fxsj_dd1;//回短1时间
	private double back_fxsj_dd2;//回短2时间
	private double back_hj_cd;//回长段行距
	private double back_hj_dd1;//回短1行距
	private double back_hj_dd2;//回短2行距
	private double back_zk_cd;//回长段折扣
	private double back_zk_dd1;//回短1折扣
	private double back_zk_dd2;//回短2折扣
	private double back_tdzk_cd;//回团队长段折扣
	private double back_tdzk_dd1;//回团队短1折扣
	private double back_tdzk_dd2;//回团队短2折扣
	private double back_rs_cd;//回长段人数
	private double back_rs_dd1;//回短1人数
	private double back_rs_dd2;//回短2人数
	private double back_tdrs_cd;//回长段团队人数
	private double back_tdrs_dd1;//回短1团队人数
	private double back_tdrs_dd2;//回短2团队人数
	private double back_cap;//回的布局座位数
	private double back_index;//回的段数
	private double back_zgl_cd;//回的长段座公里
	private double back_zgl_dd1;//回的短1段座公里
	private double back_zgl_dd2;//回的短2段座公里
	private double back_xssr_cd;//回的长段小时收入
	private double back_xssr_dd1;//回的短1段小时收入
	private double back_xssr_dd2;//回的短2段小时收入
	private double back_kzl_cd;//回的长段客座率
	private double back_kzl_dd1;//回的短1段客座率
	private double back_kzl_dd2;//回的短2段客座率
	/**
	 * @return the go_sr_cd
	 */
	public double getGo_sr_cd() {
		return go_sr_cd;
	}
	/**
	 * @param go_sr_cd the go_sr_cd to set
	 */
	public void setGo_sr_cd(double go_sr_cd) {
		this.go_sr_cd = go_sr_cd;
	}
	/**
	 * @return the go_sr_dd1
	 */
	public double getGo_sr_dd1() {
		return go_sr_dd1;
	}
	/**
	 * @param go_sr_dd1 the go_sr_dd1 to set
	 */
	public void setGo_sr_dd1(double go_sr_dd1) {
		this.go_sr_dd1 = go_sr_dd1;
	}
	/**
	 * @return the go_sr_dd2
	 */
	public double getGo_sr_dd2() {
		return go_sr_dd2;
	}
	/**
	 * @param go_sr_dd2 the go_sr_dd2 to set
	 */
	public void setGo_sr_dd2(double go_sr_dd2) {
		this.go_sr_dd2 = go_sr_dd2;
	}
	/**
	 * @return the go_ftzws_cd
	 */
	public double getGo_ftzws_cd() {
		return go_ftzws_cd;
	}
	/**
	 * @param go_ftzws_cd the go_ftzws_cd to set
	 */
	public void setGo_ftzws_cd(double go_ftzws_cd) {
		this.go_ftzws_cd = go_ftzws_cd;
	}
	/**
	 * @return the go_ftzws_dd1
	 */
	public double getGo_ftzws_dd1() {
		return go_ftzws_dd1;
	}
	/**
	 * @param go_ftzws_dd1 the go_ftzws_dd1 to set
	 */
	public void setGo_ftzws_dd1(double go_ftzws_dd1) {
		this.go_ftzws_dd1 = go_ftzws_dd1;
	}
	/**
	 * @return the go_ftzws_dd2
	 */
	public double getGo_ftzws_dd2() {
		return go_ftzws_dd2;
	}
	/**
	 * @param go_ftzws_dd2 the go_ftzws_dd2 to set
	 */
	public void setGo_ftzws_dd2(double go_ftzws_dd2) {
		this.go_ftzws_dd2 = go_ftzws_dd2;
	}
	/**
	 * @return the go_fxsj_cd
	 */
	public double getGo_fxsj_cd() {
		return go_fxsj_cd;
	}
	/**
	 * @param go_fxsj_cd the go_fxsj_cd to set
	 */
	public void setGo_fxsj_cd(double go_fxsj_cd) {
		this.go_fxsj_cd = go_fxsj_cd;
	}
	/**
	 * @return the go_fxsj_dd1
	 */
	public double getGo_fxsj_dd1() {
		return go_fxsj_dd1;
	}
	/**
	 * @param go_fxsj_dd1 the go_fxsj_dd1 to set
	 */
	public void setGo_fxsj_dd1(double go_fxsj_dd1) {
		this.go_fxsj_dd1 = go_fxsj_dd1;
	}
	/**
	 * @return the go_fxsj_dd2
	 */
	public double getGo_fxsj_dd2() {
		return go_fxsj_dd2;
	}
	/**
	 * @param go_fxsj_dd2 the go_fxsj_dd2 to set
	 */
	public void setGo_fxsj_dd2(double go_fxsj_dd2) {
		this.go_fxsj_dd2 = go_fxsj_dd2;
	}
	/**
	 * @return the go_hj_cd
	 */
	public double getGo_hj_cd() {
		return go_hj_cd;
	}
	/**
	 * @param go_hj_cd the go_hj_cd to set
	 */
	public void setGo_hj_cd(double go_hj_cd) {
		this.go_hj_cd = go_hj_cd;
	}
	/**
	 * @return the go_hj_dd1
	 */
	public double getGo_hj_dd1() {
		return go_hj_dd1;
	}
	/**
	 * @param go_hj_dd1 the go_hj_dd1 to set
	 */
	public void setGo_hj_dd1(double go_hj_dd1) {
		this.go_hj_dd1 = go_hj_dd1;
	}
	/**
	 * @return the go_hj_dd2
	 */
	public double getGo_hj_dd2() {
		return go_hj_dd2;
	}
	/**
	 * @param go_hj_dd2 the go_hj_dd2 to set
	 */
	public void setGo_hj_dd2(double go_hj_dd2) {
		this.go_hj_dd2 = go_hj_dd2;
	}
	/**
	 * @return the go_zk_cd
	 */
	public double getGo_zk_cd() {
		return go_zk_cd;
	}
	/**
	 * @param go_zk_cd the go_zk_cd to set
	 */
	public void setGo_zk_cd(double go_zk_cd) {
		this.go_zk_cd = go_zk_cd;
	}
	/**
	 * @return the go_zk_dd1
	 */
	public double getGo_zk_dd1() {
		return go_zk_dd1;
	}
	/**
	 * @param go_zk_dd1 the go_zk_dd1 to set
	 */
	public void setGo_zk_dd1(double go_zk_dd1) {
		this.go_zk_dd1 = go_zk_dd1;
	}
	/**
	 * @return the go_zk_dd2
	 */
	public double getGo_zk_dd2() {
		return go_zk_dd2;
	}
	/**
	 * @param go_zk_dd2 the go_zk_dd2 to set
	 */
	public void setGo_zk_dd2(double go_zk_dd2) {
		this.go_zk_dd2 = go_zk_dd2;
	}
	/**
	 * @return the go_rs_cd
	 */
	public double getGo_rs_cd() {
		return go_rs_cd;
	}
	/**
	 * @param go_rs_cd the go_rs_cd to set
	 */
	public void setGo_rs_cd(double go_rs_cd) {
		this.go_rs_cd = go_rs_cd;
	}
	/**
	 * @return the go_rs_dd1
	 */
	public double getGo_rs_dd1() {
		return go_rs_dd1;
	}
	/**
	 * @param go_rs_dd1 the go_rs_dd1 to set
	 */
	public void setGo_rs_dd1(double go_rs_dd1) {
		this.go_rs_dd1 = go_rs_dd1;
	}
	/**
	 * @return the go_rs_dd2
	 */
	public double getGo_rs_dd2() {
		return go_rs_dd2;
	}
	/**
	 * @param go_rs_dd2 the go_rs_dd2 to set
	 */
	public void setGo_rs_dd2(double go_rs_dd2) {
		this.go_rs_dd2 = go_rs_dd2;
	}
	/**
	 * @return the go_cap
	 */
	public double getGo_cap() {
		return go_cap;
	}
	/**
	 * @param go_cap the go_cap to set
	 */
	public void setGo_cap(double go_cap) {
		this.go_cap = go_cap;
	}
	/**
	 * @return the go_index
	 */
	public double getGo_index() {
		return go_index;
	}
	/**
	 * @param go_index the go_index to set
	 */
	public void setGo_index(double go_index) {
		this.go_index = go_index;
	}
	/**
	 * @return the back_sr_cd
	 */
	public double getBack_sr_cd() {
		return back_sr_cd;
	}
	/**
	 * @param back_sr_cd the back_sr_cd to set
	 */
	public void setBack_sr_cd(double back_sr_cd) {
		this.back_sr_cd = back_sr_cd;
	}
	/**
	 * @return the back_sr_dd1
	 */
	public double getBack_sr_dd1() {
		return back_sr_dd1;
	}
	/**
	 * @param back_sr_dd1 the back_sr_dd1 to set
	 */
	public void setBack_sr_dd1(double back_sr_dd1) {
		this.back_sr_dd1 = back_sr_dd1;
	}
	/**
	 * @return the back_sr_dd2
	 */
	public double getBack_sr_dd2() {
		return back_sr_dd2;
	}
	/**
	 * @param back_sr_dd2 the back_sr_dd2 to set
	 */
	public void setBack_sr_dd2(double back_sr_dd2) {
		this.back_sr_dd2 = back_sr_dd2;
	}
	/**
	 * @return the back_ftzws_cd
	 */
	public double getBack_ftzws_cd() {
		return back_ftzws_cd;
	}
	/**
	 * @param back_ftzws_cd the back_ftzws_cd to set
	 */
	public void setBack_ftzws_cd(double back_ftzws_cd) {
		this.back_ftzws_cd = back_ftzws_cd;
	}
	/**
	 * @return the back_ftzws_dd1
	 */
	public double getBack_ftzws_dd1() {
		return back_ftzws_dd1;
	}
	/**
	 * @param back_ftzws_dd1 the back_ftzws_dd1 to set
	 */
	public void setBack_ftzws_dd1(double back_ftzws_dd1) {
		this.back_ftzws_dd1 = back_ftzws_dd1;
	}
	/**
	 * @return the back_ftzws_dd2
	 */
	public double getBack_ftzws_dd2() {
		return back_ftzws_dd2;
	}
	/**
	 * @param back_ftzws_dd2 the back_ftzws_dd2 to set
	 */
	public void setBack_ftzws_dd2(double back_ftzws_dd2) {
		this.back_ftzws_dd2 = back_ftzws_dd2;
	}
	/**
	 * @return the back_fxsj_cd
	 */
	public double getBack_fxsj_cd() {
		return back_fxsj_cd;
	}
	/**
	 * @param back_fxsj_cd the back_fxsj_cd to set
	 */
	public void setBack_fxsj_cd(double back_fxsj_cd) {
		this.back_fxsj_cd = back_fxsj_cd;
	}
	/**
	 * @return the back_fxsj_dd1
	 */
	public double getBack_fxsj_dd1() {
		return back_fxsj_dd1;
	}
	/**
	 * @param back_fxsj_dd1 the back_fxsj_dd1 to set
	 */
	public void setBack_fxsj_dd1(double back_fxsj_dd1) {
		this.back_fxsj_dd1 = back_fxsj_dd1;
	}
	/**
	 * @return the back_fxsj_dd2
	 */
	public double getBack_fxsj_dd2() {
		return back_fxsj_dd2;
	}
	/**
	 * @param back_fxsj_dd2 the back_fxsj_dd2 to set
	 */
	public void setBack_fxsj_dd2(double back_fxsj_dd2) {
		this.back_fxsj_dd2 = back_fxsj_dd2;
	}
	/**
	 * @return the back_hj_cd
	 */
	public double getBack_hj_cd() {
		return back_hj_cd;
	}
	/**
	 * @param back_hj_cd the back_hj_cd to set
	 */
	public void setBack_hj_cd(double back_hj_cd) {
		this.back_hj_cd = back_hj_cd;
	}
	/**
	 * @return the back_hj_dd1
	 */
	public double getBack_hj_dd1() {
		return back_hj_dd1;
	}
	/**
	 * @param back_hj_dd1 the back_hj_dd1 to set
	 */
	public void setBack_hj_dd1(double back_hj_dd1) {
		this.back_hj_dd1 = back_hj_dd1;
	}
	/**
	 * @return the back_hj_dd2
	 */
	public double getBack_hj_dd2() {
		return back_hj_dd2;
	}
	/**
	 * @param back_hj_dd2 the back_hj_dd2 to set
	 */
	public void setBack_hj_dd2(double back_hj_dd2) {
		this.back_hj_dd2 = back_hj_dd2;
	}
	/**
	 * @return the back_zk_cd
	 */
	public double getBack_zk_cd() {
		return back_zk_cd;
	}
	/**
	 * @param back_zk_cd the back_zk_cd to set
	 */
	public void setBack_zk_cd(double back_zk_cd) {
		this.back_zk_cd = back_zk_cd;
	}
	/**
	 * @return the back_zk_dd1
	 */
	public double getBack_zk_dd1() {
		return back_zk_dd1;
	}
	/**
	 * @param back_zk_dd1 the back_zk_dd1 to set
	 */
	public void setBack_zk_dd1(double back_zk_dd1) {
		this.back_zk_dd1 = back_zk_dd1;
	}
	/**
	 * @return the back_zk_dd2
	 */
	public double getBack_zk_dd2() {
		return back_zk_dd2;
	}
	/**
	 * @param back_zk_dd2 the back_zk_dd2 to set
	 */
	public void setBack_zk_dd2(double back_zk_dd2) {
		this.back_zk_dd2 = back_zk_dd2;
	}
	/**
	 * @return the back_rs_cd
	 */
	public double getBack_rs_cd() {
		return back_rs_cd;
	}
	/**
	 * @param back_rs_cd the back_rs_cd to set
	 */
	public void setBack_rs_cd(double back_rs_cd) {
		this.back_rs_cd = back_rs_cd;
	}
	/**
	 * @return the back_rs_dd1
	 */
	public double getBack_rs_dd1() {
		return back_rs_dd1;
	}
	/**
	 * @param back_rs_dd1 the back_rs_dd1 to set
	 */
	public void setBack_rs_dd1(double back_rs_dd1) {
		this.back_rs_dd1 = back_rs_dd1;
	}
	/**
	 * @return the back_rs_dd2
	 */
	public double getBack_rs_dd2() {
		return back_rs_dd2;
	}
	/**
	 * @param back_rs_dd2 the back_rs_dd2 to set
	 */
	public void setBack_rs_dd2(double back_rs_dd2) {
		this.back_rs_dd2 = back_rs_dd2;
	}
	/**
	 * @return the back_cap
	 */
	public double getBack_cap() {
		return back_cap;
	}
	/**
	 * @param back_cap the back_cap to set
	 */
	public void setBack_cap(double back_cap) {
		this.back_cap = back_cap;
	}
	/**
	 * @return the back_index
	 */
	public double getBack_index() {
		return back_index;
	}
	/**
	 * @param back_index the back_index to set
	 */
	public void setBack_index(double back_index) {
		this.back_index = back_index;
	}
	/**
	 * @return the go_tdrs_cd
	 */
	public double getGo_tdrs_cd() {
		return go_tdrs_cd;
	}
	/**
	 * @param go_tdrs_cd the go_tdrs_cd to set
	 */
	public void setGo_tdrs_cd(double go_tdrs_cd) {
		this.go_tdrs_cd = go_tdrs_cd;
	}
	/**
	 * @return the go_tdrs_dd1
	 */
	public double getGo_tdrs_dd1() {
		return go_tdrs_dd1;
	}
	/**
	 * @param go_tdrs_dd1 the go_tdrs_dd1 to set
	 */
	public void setGo_tdrs_dd1(double go_tdrs_dd1) {
		this.go_tdrs_dd1 = go_tdrs_dd1;
	}
	/**
	 * @return the go_tdrs_dd2
	 */
	public double getGo_tdrs_dd2() {
		return go_tdrs_dd2;
	}
	/**
	 * @param go_tdrs_dd2 the go_tdrs_dd2 to set
	 */
	public void setGo_tdrs_dd2(double go_tdrs_dd2) {
		this.go_tdrs_dd2 = go_tdrs_dd2;
	}
	/**
	 * @return the back_tdrs_cd
	 */
	public double getBack_tdrs_cd() {
		return back_tdrs_cd;
	}
	/**
	 * @param back_tdrs_cd the back_tdrs_cd to set
	 */
	public void setBack_tdrs_cd(double back_tdrs_cd) {
		this.back_tdrs_cd = back_tdrs_cd;
	}
	/**
	 * @return the back_tdrs_dd1
	 */
	public double getBack_tdrs_dd1() {
		return back_tdrs_dd1;
	}
	/**
	 * @param back_tdrs_dd1 the back_tdrs_dd1 to set
	 */
	public void setBack_tdrs_dd1(double back_tdrs_dd1) {
		this.back_tdrs_dd1 = back_tdrs_dd1;
	}
	/**
	 * @return the back_tdrs_dd2
	 */
	public double getBack_tdrs_dd2() {
		return back_tdrs_dd2;
	}
	/**
	 * @param back_tdrs_dd2 the back_tdrs_dd2 to set
	 */
	public void setBack_tdrs_dd2(double back_tdrs_dd2) {
		this.back_tdrs_dd2 = back_tdrs_dd2;
	}
	/**
	 * @return the go_zgl_cd
	 */
	public double getGo_zgl_cd() {
		return go_zgl_cd;
	}
	/**
	 * @param go_zgl_cd the go_zgl_cd to set
	 */
	public void setGo_zgl_cd(double go_zgl_cd) {
		this.go_zgl_cd = go_zgl_cd;
	}
	/**
	 * @return the go_zgl_dd1
	 */
	public double getGo_zgl_dd1() {
		return go_zgl_dd1;
	}
	/**
	 * @param go_zgl_dd1 the go_zgl_dd1 to set
	 */
	public void setGo_zgl_dd1(double go_zgl_dd1) {
		this.go_zgl_dd1 = go_zgl_dd1;
	}
	/**
	 * @return the go_zgl_dd2
	 */
	public double getGo_zgl_dd2() {
		return go_zgl_dd2;
	}
	/**
	 * @param go_zgl_dd2 the go_zgl_dd2 to set
	 */
	public void setGo_zgl_dd2(double go_zgl_dd2) {
		this.go_zgl_dd2 = go_zgl_dd2;
	}
	/**
	 * @return the go_xssr_cd
	 */
	public double getGo_xssr_cd() {
		return go_xssr_cd;
	}
	/**
	 * @param go_xssr_cd the go_xssr_cd to set
	 */
	public void setGo_xssr_cd(double go_xssr_cd) {
		this.go_xssr_cd = go_xssr_cd;
	}
	/**
	 * @return the go_xssr_dd1
	 */
	public double getGo_xssr_dd1() {
		return go_xssr_dd1;
	}
	/**
	 * @param go_xssr_dd1 the go_xssr_dd1 to set
	 */
	public void setGo_xssr_dd1(double go_xssr_dd1) {
		this.go_xssr_dd1 = go_xssr_dd1;
	}
	/**
	 * @return the go_xssr_dd2
	 */
	public double getGo_xssr_dd2() {
		return go_xssr_dd2;
	}
	/**
	 * @param go_xssr_dd2 the go_xssr_dd2 to set
	 */
	public void setGo_xssr_dd2(double go_xssr_dd2) {
		this.go_xssr_dd2 = go_xssr_dd2;
	}
	/**
	 * @return the go_kzl_cd
	 */
	public double getGo_kzl_cd() {
		return go_kzl_cd;
	}
	/**
	 * @param go_kzl_cd the go_kzl_cd to set
	 */
	public void setGo_kzl_cd(double go_kzl_cd) {
		this.go_kzl_cd = go_kzl_cd;
	}
	/**
	 * @return the go_kzl_dd1
	 */
	public double getGo_kzl_dd1() {
		return go_kzl_dd1;
	}
	/**
	 * @param go_kzl_dd1 the go_kzl_dd1 to set
	 */
	public void setGo_kzl_dd1(double go_kzl_dd1) {
		this.go_kzl_dd1 = go_kzl_dd1;
	}
	/**
	 * @return the go_kzl_dd2
	 */
	public double getGo_kzl_dd2() {
		return go_kzl_dd2;
	}
	/**
	 * @param go_kzl_dd2 the go_kzl_dd2 to set
	 */
	public void setGo_kzl_dd2(double go_kzl_dd2) {
		this.go_kzl_dd2 = go_kzl_dd2;
	}
	/**
	 * @return the back_zgl_cd
	 */
	public double getBack_zgl_cd() {
		return back_zgl_cd;
	}
	/**
	 * @param back_zgl_cd the back_zgl_cd to set
	 */
	public void setBack_zgl_cd(double back_zgl_cd) {
		this.back_zgl_cd = back_zgl_cd;
	}
	/**
	 * @return the back_zgl_dd1
	 */
	public double getBack_zgl_dd1() {
		return back_zgl_dd1;
	}
	/**
	 * @param back_zgl_dd1 the back_zgl_dd1 to set
	 */
	public void setBack_zgl_dd1(double back_zgl_dd1) {
		this.back_zgl_dd1 = back_zgl_dd1;
	}
	/**
	 * @return the back_zgl_dd2
	 */
	public double getBack_zgl_dd2() {
		return back_zgl_dd2;
	}
	/**
	 * @param back_zgl_dd2 the back_zgl_dd2 to set
	 */
	public void setBack_zgl_dd2(double back_zgl_dd2) {
		this.back_zgl_dd2 = back_zgl_dd2;
	}
	/**
	 * @return the back_xssr_cd
	 */
	public double getBack_xssr_cd() {
		return back_xssr_cd;
	}
	/**
	 * @param back_xssr_cd the back_xssr_cd to set
	 */
	public void setBack_xssr_cd(double back_xssr_cd) {
		this.back_xssr_cd = back_xssr_cd;
	}
	/**
	 * @return the back_xssr_dd1
	 */
	public double getBack_xssr_dd1() {
		return back_xssr_dd1;
	}
	/**
	 * @param back_xssr_dd1 the back_xssr_dd1 to set
	 */
	public void setBack_xssr_dd1(double back_xssr_dd1) {
		this.back_xssr_dd1 = back_xssr_dd1;
	}
	/**
	 * @return the back_xssr_dd2
	 */
	public double getBack_xssr_dd2() {
		return back_xssr_dd2;
	}
	/**
	 * @param back_xssr_dd2 the back_xssr_dd2 to set
	 */
	public void setBack_xssr_dd2(double back_xssr_dd2) {
		this.back_xssr_dd2 = back_xssr_dd2;
	}
	/**
	 * @return the back_kzl_cd
	 */
	public double getBack_kzl_cd() {
		return back_kzl_cd;
	}
	/**
	 * @param back_kzl_cd the back_kzl_cd to set
	 */
	public void setBack_kzl_cd(double back_kzl_cd) {
		this.back_kzl_cd = back_kzl_cd;
	}
	/**
	 * @return the back_kzl_dd1
	 */
	public double getBack_kzl_dd1() {
		return back_kzl_dd1;
	}
	/**
	 * @param back_kzl_dd1 the back_kzl_dd1 to set
	 */
	public void setBack_kzl_dd1(double back_kzl_dd1) {
		this.back_kzl_dd1 = back_kzl_dd1;
	}
	/**
	 * @return the back_kzl_dd2
	 */
	public double getBack_kzl_dd2() {
		return back_kzl_dd2;
	}
	/**
	 * @param back_kzl_dd2 the back_kzl_dd2 to set
	 */
	public void setBack_kzl_dd2(double back_kzl_dd2) {
		this.back_kzl_dd2 = back_kzl_dd2;
	}
	/**
	 * @return the go_tdsr_cd
	 */
	public double getGo_tdsr_cd() {
		return go_tdsr_cd;
	}
	/**
	 * @param go_tdsr_cd the go_tdsr_cd to set
	 */
	public void setGo_tdsr_cd(double go_tdsr_cd) {
		this.go_tdsr_cd = go_tdsr_cd;
	}
	/**
	 * @return the go_tdsr_dd1
	 */
	public double getGo_tdsr_dd1() {
		return go_tdsr_dd1;
	}
	/**
	 * @param go_tdsr_dd1 the go_tdsr_dd1 to set
	 */
	public void setGo_tdsr_dd1(double go_tdsr_dd1) {
		this.go_tdsr_dd1 = go_tdsr_dd1;
	}
	/**
	 * @return the go_tdsr_dd2
	 */
	public double getGo_tdsr_dd2() {
		return go_tdsr_dd2;
	}
	/**
	 * @param go_tdsr_dd2 the go_tdsr_dd2 to set
	 */
	public void setGo_tdsr_dd2(double go_tdsr_dd2) {
		this.go_tdsr_dd2 = go_tdsr_dd2;
	}
	/**
	 * @return the back_tdsr_cd
	 */
	public double getBack_tdsr_cd() {
		return back_tdsr_cd;
	}
	/**
	 * @param back_tdsr_cd the back_tdsr_cd to set
	 */
	public void setBack_tdsr_cd(double back_tdsr_cd) {
		this.back_tdsr_cd = back_tdsr_cd;
	}
	/**
	 * @return the back_tdsr_dd1
	 */
	public double getBack_tdsr_dd1() {
		return back_tdsr_dd1;
	}
	/**
	 * @param back_tdsr_dd1 the back_tdsr_dd1 to set
	 */
	public void setBack_tdsr_dd1(double back_tdsr_dd1) {
		this.back_tdsr_dd1 = back_tdsr_dd1;
	}
	/**
	 * @return the back_tdsr_dd2
	 */
	public double getBack_tdsr_dd2() {
		return back_tdsr_dd2;
	}
	/**
	 * @param back_tdsr_dd2 the back_tdsr_dd2 to set
	 */
	public void setBack_tdsr_dd2(double back_tdsr_dd2) {
		this.back_tdsr_dd2 = back_tdsr_dd2;
	}
	/**
	 * @return the go_tdzk_cd
	 */
	public double getGo_tdzk_cd() {
		return go_tdzk_cd;
	}
	/**
	 * @param go_tdzk_cd the go_tdzk_cd to set
	 */
	public void setGo_tdzk_cd(double go_tdzk_cd) {
		this.go_tdzk_cd = go_tdzk_cd;
	}
	/**
	 * @return the go_tdzk_dd1
	 */
	public double getGo_tdzk_dd1() {
		return go_tdzk_dd1;
	}
	/**
	 * @param go_tdzk_dd1 the go_tdzk_dd1 to set
	 */
	public void setGo_tdzk_dd1(double go_tdzk_dd1) {
		this.go_tdzk_dd1 = go_tdzk_dd1;
	}
	/**
	 * @return the go_tdzk_dd2
	 */
	public double getGo_tdzk_dd2() {
		return go_tdzk_dd2;
	}
	/**
	 * @param go_tdzk_dd2 the go_tdzk_dd2 to set
	 */
	public void setGo_tdzk_dd2(double go_tdzk_dd2) {
		this.go_tdzk_dd2 = go_tdzk_dd2;
	}
	/**
	 * @return the back_tdzk_cd
	 */
	public double getBack_tdzk_cd() {
		return back_tdzk_cd;
	}
	/**
	 * @param back_tdzk_cd the back_tdzk_cd to set
	 */
	public void setBack_tdzk_cd(double back_tdzk_cd) {
		this.back_tdzk_cd = back_tdzk_cd;
	}
	/**
	 * @return the back_tdzk_dd1
	 */
	public double getBack_tdzk_dd1() {
		return back_tdzk_dd1;
	}
	/**
	 * @param back_tdzk_dd1 the back_tdzk_dd1 to set
	 */
	public void setBack_tdzk_dd1(double back_tdzk_dd1) {
		this.back_tdzk_dd1 = back_tdzk_dd1;
	}
	/**
	 * @return the back_tdzk_dd2
	 */
	public double getBack_tdzk_dd2() {
		return back_tdzk_dd2;
	}
	/**
	 * @param back_tdzk_dd2 the back_tdzk_dd2 to set
	 */
	public void setBack_tdzk_dd2(double back_tdzk_dd2) {
		this.back_tdzk_dd2 = back_tdzk_dd2;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AirLineAllInfo [go_sr_cd=" + go_sr_cd + ", go_sr_dd1="
				+ go_sr_dd1 + ", go_sr_dd2=" + go_sr_dd2 + ", go_tdsr_cd="
				+ go_tdsr_cd + ", go_tdsr_dd1=" + go_tdsr_dd1
				+ ", go_tdsr_dd2=" + go_tdsr_dd2 + ", go_ftzws_cd="
				+ go_ftzws_cd + ", go_ftzws_dd1=" + go_ftzws_dd1
				+ ", go_ftzws_dd2=" + go_ftzws_dd2 + ", go_fxsj_cd="
				+ go_fxsj_cd + ", go_fxsj_dd1=" + go_fxsj_dd1
				+ ", go_fxsj_dd2=" + go_fxsj_dd2 + ", go_hj_cd=" + go_hj_cd
				+ ", go_hj_dd1=" + go_hj_dd1 + ", go_hj_dd2=" + go_hj_dd2
				+ ", go_zk_cd=" + go_zk_cd + ", go_zk_dd1=" + go_zk_dd1
				+ ", go_zk_dd2=" + go_zk_dd2 + ", go_tdzk_cd=" + go_tdzk_cd
				+ ", go_tdzk_dd1=" + go_tdzk_dd1 + ", go_tdzk_dd2="
				+ go_tdzk_dd2 + ", go_rs_cd=" + go_rs_cd + ", go_rs_dd1="
				+ go_rs_dd1 + ", go_rs_dd2=" + go_rs_dd2 + ", go_tdrs_cd="
				+ go_tdrs_cd + ", go_tdrs_dd1=" + go_tdrs_dd1
				+ ", go_tdrs_dd2=" + go_tdrs_dd2 + ", go_cap=" + go_cap
				+ ", go_index=" + go_index + ", go_zgl_cd=" + go_zgl_cd
				+ ", go_zgl_dd1=" + go_zgl_dd1 + ", go_zgl_dd2=" + go_zgl_dd2
				+ ", go_xssr_cd=" + go_xssr_cd + ", go_xssr_dd1=" + go_xssr_dd1
				+ ", go_xssr_dd2=" + go_xssr_dd2 + ", go_kzl_cd=" + go_kzl_cd
				+ ", go_kzl_dd1=" + go_kzl_dd1 + ", go_kzl_dd2=" + go_kzl_dd2
				+ ", back_sr_cd=" + back_sr_cd + ", back_sr_dd1=" + back_sr_dd1
				+ ", back_sr_dd2=" + back_sr_dd2 + ", back_tdsr_cd="
				+ back_tdsr_cd + ", back_tdsr_dd1=" + back_tdsr_dd1
				+ ", back_tdsr_dd2=" + back_tdsr_dd2 + ", back_ftzws_cd="
				+ back_ftzws_cd + ", back_ftzws_dd1=" + back_ftzws_dd1
				+ ", back_ftzws_dd2=" + back_ftzws_dd2 + ", back_fxsj_cd="
				+ back_fxsj_cd + ", back_fxsj_dd1=" + back_fxsj_dd1
				+ ", back_fxsj_dd2=" + back_fxsj_dd2 + ", back_hj_cd="
				+ back_hj_cd + ", back_hj_dd1=" + back_hj_dd1
				+ ", back_hj_dd2=" + back_hj_dd2 + ", back_zk_cd=" + back_zk_cd
				+ ", back_zk_dd1=" + back_zk_dd1 + ", back_zk_dd2="
				+ back_zk_dd2 + ", back_tdzk_cd=" + back_tdzk_cd
				+ ", back_tdzk_dd1=" + back_tdzk_dd1 + ", back_tdzk_dd2="
				+ back_tdzk_dd2 + ", back_rs_cd=" + back_rs_cd
				+ ", back_rs_dd1=" + back_rs_dd1 + ", back_rs_dd2="
				+ back_rs_dd2 + ", back_tdrs_cd=" + back_tdrs_cd
				+ ", back_tdrs_dd1=" + back_tdrs_dd1 + ", back_tdrs_dd2="
				+ back_tdrs_dd2 + ", back_cap=" + back_cap + ", back_index="
				+ back_index + ", back_zgl_cd=" + back_zgl_cd
				+ ", back_zgl_dd1=" + back_zgl_dd1 + ", back_zgl_dd2="
				+ back_zgl_dd2 + ", back_xssr_cd=" + back_xssr_cd
				+ ", back_xssr_dd1=" + back_xssr_dd1 + ", back_xssr_dd2="
				+ back_xssr_dd2 + ", back_kzl_cd=" + back_kzl_cd
				+ ", back_kzl_dd1=" + back_kzl_dd1 + ", back_kzl_dd2="
				+ back_kzl_dd2 + "]";
	}
	
}
