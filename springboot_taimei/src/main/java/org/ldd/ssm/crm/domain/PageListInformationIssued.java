package org.ldd.ssm.crm.domain;

import java.util.List;

public class PageListInformationIssued {
	private int totalPage;
	private List<InformationIssued> list;
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<InformationIssued> getList() {
		return list;
	}
	public void setList(List<InformationIssued> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "PageListInformationIssued [totalPage=" + totalPage + ", list="
				+ list ;
	}
	
}
