package org.ldd.ssm.hangyu.utils;

import java.io.Serializable;
import java.util.List;

//分页类
public class PageBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3124072764496321736L;

	private int pageNo = 1;//页码
	
	public static int pageSize = 10;//每页显示记录（默认，静态）
	
	private int totalCount = 0;//总记录数
	
	private int pageCount = 0;//总页数
	
	private int numPrePage = 0;//每页显示记录数（动态）
	
	private List<T> list;//分页数据
	
	private int unReadMessageTotalCount;
	public PageBean(){}
	public PageBean(int totalCount, List<T> list) {
		this.totalCount = totalCount;
		this.list = list;
	}
	
	public PageBean(int totalCount, List<T> list, int numPrePage) {
		this.pageNo = pageNo;
		this.totalCount = totalCount;
		this.list = list;
		this.numPrePage = numPrePage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPageSize(int pageSize) {
		PageBean.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		if(numPrePage>0){
			return totalCount%numPrePage==0?totalCount/numPrePage:(totalCount/numPrePage+1);
		}
		return totalCount%pageSize==0?totalCount/pageSize:(totalCount/pageSize+1);
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	public int getNumPrePage() {
		return numPrePage;
	}
	public void setNumPrePage(int numPrePage) {
		this.numPrePage = numPrePage;
	}
	public int getUnReadMessageTotalCount() {
		return unReadMessageTotalCount;
	}
	public void setUnReadMessageTotalCount(int unReadMessageTotalCount) {
		this.unReadMessageTotalCount = unReadMessageTotalCount;
	}
}
