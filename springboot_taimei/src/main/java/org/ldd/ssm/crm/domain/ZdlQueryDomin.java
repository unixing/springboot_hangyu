package org.ldd.ssm.crm.domain;

import java.util.List;

/**
 * 月准点率统计实体
 * @Title:ZdlQueryDomin 
 * @Description:
 * @author taimei-gds 
 * @date 2017-12-18 上午11:08:28
 */
public class ZdlQueryDomin {
	private String cancel_count;//取消的数量
	private String ontime_count;//准点数量
	private String sum_count;//总数量
	private String zd_rate;//准点率
	private List<ZdlQueryDominSon> remark_list;//异常列表
	/**
	 * @return the cancel_count
	 */
	public String getCancel_count() {
		return cancel_count;
	}
	/**
	 * @param cancel_count the cancel_count to set
	 */
	public void setCancel_count(String cancel_count) {
		this.cancel_count = cancel_count;
	}
	/**
	 * @return the ontime_count
	 */
	public String getOntime_count() {
		return ontime_count;
	}
	/**
	 * @param ontime_count the ontime_count to set
	 */
	public void setOntime_count(String ontime_count) {
		this.ontime_count = ontime_count;
	}
	/**
	 * @return the sum_count
	 */
	public String getSum_count() {
		return sum_count;
	}
	/**
	 * @param sum_count the sum_count to set
	 */
	public void setSum_count(String sum_count) {
		this.sum_count = sum_count;
	}
	/**
	 * @return the zd_rate
	 */
	public String getZd_rate() {
		return zd_rate;
	}
	/**
	 * @param zd_rate the zd_rate to set
	 */
	public void setZd_rate(String zd_rate) {
		this.zd_rate = zd_rate;
	}
	/**
	 * @return the remark_list
	 */
	public List<ZdlQueryDominSon> getRemark_list() {
		return remark_list;
	}
	/**
	 * @param remark_list the remark_list to set
	 */
	public void setRemark_list(List<ZdlQueryDominSon> remark_list) {
		this.remark_list = remark_list;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZdlQueryDomin [cancel_count=" + cancel_count
				+ ", ontime_count=" + ontime_count + ", sum_count=" + sum_count
				+ ", zd_rate=" + zd_rate + "]";
	}
}
