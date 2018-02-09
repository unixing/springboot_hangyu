package org.ldd.ssm.crm.query;
/**
 * 飞机数据分页查询对象
 * @Title:FlyBasicDataQuery 
 * @Description:
 * @author taimei-gds 
 * @date 2016-5-24 上午10:28:01
 */
public class FlyBasicDataQuery {
	private String flyType;//机场名字
	//分页用的字段
	private Integer offset;//当前页数
	private Integer limit;//当前页数量
	/**
	 * @return the flyType
	 */
	public String getFlyType() {
		return flyType;
	}
	/**
	 * @param flyType the flyType to set
	 */
	public void setFlyType(String flyType) {
		this.flyType = flyType;
	}
	/**
	 * @return the offset
	 */
	public Integer getOffset() {
		return offset;
	}
	/**
	 * @param offset the offset to set
	 */
	public void setOffset(Integer offset) {
		this.offset = offset;
	}
	/**
	 * @return the limit
	 */
	public Integer getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FlyBasicDataQuery [flyType=" + flyType + ", offset=" + offset
				+ ", limit=" + limit + "]";
	}
	
	
}