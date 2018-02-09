package org.ldd.ssm.crm.domain;

import java.util.ArrayList;

public class LinkTypeData
{
	private int id;
	/**
	 * 链接的地址
	 */
	private String linkHref;
	/**
	 * 链接的标题
	 */
	private String airText;
	/**
	 * 时间
	 */
	private String summary;
	/**
	 * 机场
	 */
	private String content;
	/**
	 * 班期
	 * @return
	 */
	private ArrayList<String> arrayList;
	/**
	 * 航班号
	 * @return
	 */
	private String flyNub=null;
	/**
	 * 机型
	 */
	private String AirCrft_Typ;
	
	public String getAirCrft_Typ() {
		return AirCrft_Typ;
	}
	public void setAirCrft_Typ(String airCrft_Typ) {
		AirCrft_Typ = airCrft_Typ;
	}
	public ArrayList<String> getArrayList() {
		return arrayList;
	}
	public void setArrayList(ArrayList<String> arrayList) {
		this.arrayList = arrayList;
	}
	public String getFlyNub() {
		return flyNub;
	}
	public void setFlyNub(String flyNub) {
		this.flyNub = flyNub;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getLinkHref()
	{
		return linkHref;
	}
	public void setLinkHref(String linkHref)
	{
		this.linkHref = linkHref;
	}
	
	public String getAirText() {
		return airText;
	}
	public void setAirText(String airText) {
		this.airText = airText;
	}
	public String getSummary()
	{
		return summary;
	}
	public void setSummary(String summary)
	{
		this.summary = summary;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent(String content)
	{
		this.content = content;
	}
	@Override
	public String toString() {
		return "LinkTypeData [id=" + id + ", linkHref=" + linkHref
				+ ", airText=" + airText + ", summary=" + summary
				+ ", content=" + content + ", arrayList=" + arrayList
				+ ", flyNub=" + flyNub + ", AirCrft_Typ=" + AirCrft_Typ + "]";
	}

}
