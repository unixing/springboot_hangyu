package org.ldd.ssm.crm.domain;

/**
 * 航司JSON实体类
 * @Title:ShippingData 
 * @Description:
 * @author taimei-gds 
 * @date 2017-3-29 上午9:59:09
 */
public class ShippingData {
	private String name;//名字
	private String iT;//代码
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the iT
	 */
	public String getiT() {
		return iT;
	}
	/**
	 * @param iT the iT to set
	 */
	public void setiT(String iT) {
		this.iT = iT;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ShippingData [name=" + name + ", iT=" + iT + "]";
	}
	
	
}
