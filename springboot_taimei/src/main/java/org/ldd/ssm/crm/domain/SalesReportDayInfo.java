package org.ldd.ssm.crm.domain;

import java.util.Map;

/**
 * 销售报表日报返回的组装对象
 * @Title:SalesReportDayInfo 
 * @Description:
 * @author taimei-gds 
 * @date 2017-5-3 上午10:32:53
 */
public class SalesReportDayInfo {
	private String flyName;//航段名字以-分隔
	private String flyCode;//航段代码以-分隔
	private Map<String,Object> dataMap;//数据MAP
	/**
	 * @return the flyName
	 */
	public String getFlyName() {
		return flyName;
	}
	/**
	 * @param flyName the flyName to set
	 */
	public void setFlyName(String flyName) {
		this.flyName = flyName;
	}
	/**
	 * @return the flyCode
	 */
	public String getFlyCode() {
		return flyCode;
	}
	/**
	 * @param flyCode the flyCode to set
	 */
	public void setFlyCode(String flyCode) {
		this.flyCode = flyCode;
	}
	/**
	 * @return the dataMap
	 */
	public Map<String, Object> getDataMap() {
		return dataMap;
	}
	/**
	 * @param dataMap the dataMap to set
	 */
	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}
	@Override
	public String toString() {
		return "SalesReportDayInfo [flyName=" + flyName + ", flyCode="
				+ flyCode + ", dataMap=" + dataMap + "]";
	}
	
	
}