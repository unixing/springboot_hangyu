package org.ldd.ssm.crm.domain;
/**
 * 月准点率统计实体子类
 * ZdlQueryDominSon 
 * @Description:
 * @author taimei-gds 
 * @date 2017-12-18 上午11:08:28
 */
public class ZdlQueryDominSon {
	private String remark;//备注（区分天气等条件）
	private String sum_count;//各种原因的条数
	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ZdlQueryDominSon [remark=" + remark + ", sum_count="
				+ sum_count + "]";
	}
	
	
	
}
