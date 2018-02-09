package org.ldd.ssm.crm.domain;

import java.io.Serializable;

/**
 * 黑名单Mapper实体类
 * @Title:MapperData 
 * @Description:
 * @author taimei-gds 
 * @date 2016-7-20 上午9:58:10
 */
public class MapperData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String id;//id
	private String functionName;//功能名称
	private String mapCode;//对应的mapper
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
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}
	/**
	 * @param functionName the functionName to set
	 */
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	/**
	 * @return the mapCode
	 */
	public String getMapCode() {
		return mapCode;
	}
	/**
	 * @param mapCode the mapCode to set
	 */
	public void setMapCode(String mapCode) {
		this.mapCode = mapCode;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MapperData [id=" + id + ", functionName=" + functionName
				+ ", mapCode=" + mapCode + "]";
	}

	
}
